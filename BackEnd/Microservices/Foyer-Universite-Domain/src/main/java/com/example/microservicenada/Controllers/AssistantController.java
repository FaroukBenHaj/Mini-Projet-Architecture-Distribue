package com.example.microservicenada.Controllers;

import com.example.microservicenada.Services.OllamaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final OllamaService ollamaService;

    public AssistantController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody String question) {
        String response = ollamaService.getResponse(question);
        return ResponseEntity.ok(response);
    }
}

