package tn.esprit.keycloak.Controller;

import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestController
@RequestMapping("/auth")  // Changed from "/api" to "/auth"
public class KeycloakController {

    @GetMapping("/test")
    public ResponseEntity<String> getCurrentUser(Authentication authentication) {
        var principal = (KeycloakPrincipal<?>) authentication.getPrincipal();
        var token = principal.getKeycloakSecurityContext().getToken();
        var username = token.getPreferredUsername();
        return ResponseEntity.ok("Hello, " + username);
    }
}