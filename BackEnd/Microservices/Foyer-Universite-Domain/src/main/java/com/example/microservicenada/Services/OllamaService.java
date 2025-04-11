package com.example.microservicenada.Services;

import com.example.microservicenada.Entities.Foyer;
import com.example.microservicenada.Entities.Universite;
import com.example.microservicenada.Repositories.FoyerRepository;
import com.example.microservicenada.Repositories.UniversiteRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OllamaService {
    private static final Logger log = LoggerFactory.getLogger(OllamaService.class);
    private final RestTemplate restTemplate;
    private final FoyerRepository foyerRepository;
    private final UniversiteRepository universiteRepository;

    // Nearby cities configuration
    private static final Map<String, String> NEARBY_CITIES = Map.of(
            "Sousse", "Monastir (30km), Mahdia (45km)",
            "Mahdia", "Sousse (45km), Monastir (60km)",
            "Tunis", "Ariana (8km), Ben Arous (12km)"
    );

    public OllamaService(RestTemplate restTemplate,
                         FoyerRepository foyerRepository,
                         UniversiteRepository universiteRepository) {
        this.restTemplate = restTemplate;
        this.foyerRepository = foyerRepository;
        this.universiteRepository = universiteRepository;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String getResponse(String prompt) {
        String normalizedPrompt = normalizeInput(prompt);
        Optional<String> dbAnswer = tryAnswerFromDatabase(normalizedPrompt);
        return dbAnswer.orElseGet(() -> askMistral(normalizedPrompt));
    }

    private String normalizeInput(String input) {
        return input.trim().toLowerCase();
    }

    private Optional<String> tryAnswerFromDatabase(String question) {
        try {
            if (question.contains("capacity") && question.contains("foyer")) {
                return handleCapacityQuery(question);
            }
            if (question.matches(".*(foyer|university|residence).*(in|at|near).*")) {
                return handleLocationQuery(question);
            }
        } catch (Exception e) {
            log.error("Database query failed", e);
        }
        return Optional.empty();
    }

    private Optional<String> handleCapacityQuery(String question) {
        if (question.matches(".*(over|more than|greater than).*")) {
            int capacity = extractNumber(question);
            String city = extractCity(question);
            List<Foyer> foyers = city.isEmpty() ?
                    foyerRepository.findByCapaciteGreaterThan(capacity) :
                    foyerRepository.findByCapaciteGreaterThanAndUniversiteVille(capacity, city);
            return Optional.of(formatFoyerList(foyers, city));
        }
        if (question.contains("average")) {
            return handleAverageCapacityQuery(question);
        }
        return Optional.empty();
    }

    private Optional<String> handleAverageCapacityQuery(String question) {
        Double avg = foyerRepository.getAverageCapacity();
        if (question.contains("compare") || question.contains("vs")) {
            String comparison = """
                📊 Database Facts:
                - Average capacity: %.2f beds
                - Range: 50-300 beds
                
                🆚 International Comparison:
                • France: 80-150 beds
                • Germany: 100-200 beds
                """.formatted(avg);
            return Optional.of(comparison);
        }
        return Optional.of(String.format("Average foyer capacity: %.2f beds", avg));
    }

    private Optional<String> handleLocationQuery(String question) {
        String city = extractCity(question);
        if (city.isEmpty()) return Optional.empty();

        List<Foyer> foyers = foyerRepository.findByUniversiteVilleIgnoreCase(city);
        return Optional.of(formatFoyerList(foyers, city));
    }

    private String formatFoyerList(List<Foyer> foyers, String requestedCity) {
        if (foyers.isEmpty()) {
            String nearby = getNearbyCities(requestedCity);
            return String.format(
                    "No foyers found in %s matching your criteria.\n\n" +
                            "🚗 Nearby options: %s",
                    requestedCity,
                    nearby.isEmpty() ? "No nearby cities in database" : nearby
            );
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("🏛️ Foyers in %s:\n", requestedCity));

        foyers.stream()
                .filter(f -> f.getUniversite().getVille().equalsIgnoreCase(requestedCity))
                .forEach(f -> sb.append(String.format(
                        "• %s\n  - Capacity: %d\n  - University: %s\n",
                        f.getNom(),
                        f.getCapacite(),
                        f.getUniversite().getNom()
                )));

        long count = foyers.stream()
                .filter(f -> f.getUniversite().getVille().equalsIgnoreCase(requestedCity))
                .count();

        sb.append(String.format("\nTotal found: %d foyers", count));

        if (count <= 2) {
            String nearby = getNearbyCities(requestedCity);
            if (!nearby.isEmpty()) {
                sb.append("\n\nℹ️ Nearby cities: ").append(nearby);
            }
        }

        return sb.toString();
    }

    private String getNearbyCities(String city) {
        return NEARBY_CITIES.getOrDefault(city, "");
    }

    private String askMistral(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String systemMessage = """
            You are a Tunisian university housing expert. Respond with:
            1. Facts first, then analysis
            2. Use Tunisian terms (foyer universitaire)
            3. Never invent statistics
            """;

        Map<String, Object> request = new HashMap<>();
        request.put("model", "mistral");
        request.put("prompt", systemMessage + "\n\nUSER QUESTION: " + prompt);
        request.put("stream", false);
        request.put("options", Map.of(
                "temperature", 0.3,
                "top_p", 0.9,
                "max_tokens", 500
        ));

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://localhost:11434/api/generate",
                    new HttpEntity<>(request, headers),
                    Map.class);

            return cleanResponse(response.getBody().get("response").toString());
        } catch (Exception e) {
            log.error("Ollama request failed", e);
            return "Service unavailable. Please try again later.";
        }
    }

    private String cleanResponse(String response) {
        return response.replaceAll("(?m)^\\s*$", "")
                .replaceAll("\"\"\"", "")
                .trim();
    }

    private int extractNumber(String text) {
        try {
            Matcher matcher = Pattern.compile("\\d+").matcher(text);
            return matcher.find() ? Integer.parseInt(matcher.group()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String extractCity(String text) {
        Map<String, String> cityMap = Map.of(
                "tunis", "Tunis",
                "sousse", "Sousse",
                "sfax", "Sfax",
                "monastir", "Monastir",
                "nabeul", "Nabeul",
                "mahdia", "Mahdia"
        );

        return cityMap.entrySet().stream()
                .filter(e -> text.contains(e.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse("");
    }

}