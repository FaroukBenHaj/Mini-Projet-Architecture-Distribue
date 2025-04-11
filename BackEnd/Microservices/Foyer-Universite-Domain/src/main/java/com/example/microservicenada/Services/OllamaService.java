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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class OllamaService {

    private final RestTemplate restTemplate;
    private final FoyerRepository foyerRepository;
    private final UniversiteRepository universiteRepository;

    public OllamaService(RestTemplate restTemplate,
                         FoyerRepository foyerRepository,
                         UniversiteRepository universiteRepository) {
        this.restTemplate = restTemplate;
        this.foyerRepository = foyerRepository;
        this.universiteRepository = universiteRepository;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String getResponse(String prompt) {
        // First try to answer from database
        Optional<String> dbAnswer = tryAnswerFromDatabase(prompt);
        if (dbAnswer.isPresent()) {
            return dbAnswer.get();
        }

        // If not a database question, use Mistral
        return askMistral(prompt);
    }

    private Optional<String> tryAnswerFromDatabase(String question) {
        try {
            // Handle capacity queries
            if (question.toLowerCase().contains("capacity") &&
                    question.toLowerCase().contains("foyer")) {

                if (question.contains("over") || question.contains("more than")) {
                    int capacity = extractNumber(question);
                    List<Foyer> foyers = foyerRepository.findByCapaciteGreaterThan(capacity);
                    return Optional.of(formatFoyerList(foyers));
                }
                else if (question.contains("average")) {
                    // Existing average calculation
                    if (question.toLowerCase().contains("compare") ||
                            question.toLowerCase().contains("vs")) {
                        // NEW ENHANCED COMPARISON CODE GOES HERE
                        Double avg = foyerRepository.getAverageCapacity();
                        String comparisonContext = """
                        Database Result:
                        - Average foyer capacity: %.2f
                        
                        Additional Context:
                        - Typical student dorm capacity range: 50-200
                        - Hotel room capacity is typically 1-2 (single/double)
                        - Hostel dorm capacity is typically 4-12
                        """.formatted(avg);

                        return Optional.of(comparisonContext);
                    } else {
                        // Original simple average response
                        Double avg = foyerRepository.getAverageCapacity();
                        return Optional.of("Average foyer capacity: " + avg);
                    }
                }
            }

            // Handle university location queries
            if (question.toLowerCase().matches(".*(foyer|university).*(in|at).*")) {
                String ville = extractCity(question);
                List<Foyer> foyers = foyerRepository.findByUniversiteVille(ville);
                return Optional.of(formatFoyerList(foyers));
            }

        } catch (Exception e) {
            // Fall back to LLM if database query fails
        }
        return Optional.empty();
    }

    private String askMistral(String prompt) {
        String ollamaUrl = "http://localhost:11434/api/generate";

        // System message to guide Mistral's responses
        String systemMessage = """
                 ROLE:
                                                             You are a Tunisian University Housing Assistant specializing in foyers étudiants (student residences).
                                                            \s
                                                             RESPONSE RULES:
                                                            \s
                                                             1. HOUSING QUESTIONS (foyers/universities/assignments):
                                                                • FIRST display exact database facts
                                                                • THEN add analysis with:
                                                                  - Bullet points
                                                                  - Tunisian context
                                                                  - French terms (foyer universitaire, cité universitaire)
                                                                • Example structure:
                                                                  "Database shows:
                                                                  - [Fact 1]
                                                                  - [Fact 2]
                                                                  \s
                                                                   Context:
                                                                   • [Comparison to Tunisian averages]
                                                                   • [Local terminology explanation]"
                                                            \s
                                                             2. COMPARISONS:
                                                                • Always compare to:
                                                                  - Tunisian averages (foyers: 50-300 beds)
                                                                  - International equivalents (dorms, hostels)
                                                                • Use:
                                                                  "Compared to [X], Tunisian foyers are [Y] because..."
                                                            \s
                                                             3. GENERAL QUESTIONS:
                                                                • Concise factual answers (1-2 sentences max)
                                                                • When unsure: "I don't have information about [topic]. Would you like housing-related help?"
                                                            \s
                                                             TECHNICAL NOTES:
                                                             - All capacities in student beds
                                                             - Tunisian terms > French terms > English terms
                                                             - Never invent statistics
            """;

        Map<String, Object> request = new HashMap<>();
        request.put("model", "mistral");
        request.put("prompt", systemMessage + "\n\nUSER QUESTION: " + prompt);
        request.put("stream", false);
        request.put("options", Map.of("temperature", 0.5));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    ollamaUrl,
                    new HttpEntity<>(request, headers),
                    Map.class);

            return response.getBody().get("response").toString();
        } catch (Exception e) {
            return "I couldn't process your request. Please try again later.";
        }
    }

    // Helper methods (extractNumber, extractCity, formatFoyerList)
    private int extractNumber(String text) {
        try {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(text);
            return matcher.find() ? Integer.parseInt(matcher.group()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String extractCity(String text) {
        String[] tunisianCities = {
                "Tunis", "Sfax", "Sousse", "Kairouan", "Bizerte",
                "Gabès", "Ariana", "Gafsa", "Monastir", "Ben Arous",
                "Nabeul", "Kasserine", "Mahdia", "Medenine", "Zaghouan",
                "Béja", "Jendouba", "Kébili", "Le Kef", "Manouba",
                "Médenine", "Siliana", "Tataouine", "Tozeur"
        };

        // First check for exact matches
        for (String city : tunisianCities) {
            if (text.toLowerCase().contains(city.toLowerCase())) {
                return city;
            }
        }

        // Then check for common misspellings/alternatives
        Map<String, String> alternatives = Map.of(
                "tunisia", "Tunis",
                "sfax", "Sfax",
                "soussa", "Sousse",
                "monastir", "Monastir"
        );

        for (Map.Entry<String, String> entry : alternatives.entrySet()) {
            if (text.toLowerCase().contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return "";
    }

    private String formatFoyerList(List<Foyer> foyers) {
        if (foyers.isEmpty()) return "No matching foyers found";

        StringBuilder sb = new StringBuilder();
        for (Foyer f : foyers) {
            sb.append(f.getNom())
                    .append(" (Capacity: ").append(f.getCapacite())
                    .append(", ").append(f.getUniversite().getNom())
                    .append(")\n");
        }
        return sb.toString();
    }
}