package com.example.microservicenada.Controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.microservicenada.Entities.Universite;
import com.example.microservicenada.Services.UniversiteService;


import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/universites")
@RequiredArgsConstructor
public class ControllerUniversite {


    private final UniversiteService universiteService;

    @GetMapping
    public List<Universite> getAllUniversites() {
        return universiteService.getAllUniversites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Universite> getUniversiteById(@PathVariable Long id) {
        Optional<Universite> universite = universiteService.getUniversiteById(id);
        return universite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Universite createUniversite(@RequestBody Universite universite) {
        return universiteService.saveUniversite(universite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversite(@PathVariable Long id) {
        universiteService.deleteUniversite(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Universite> updateUniversite(@PathVariable Long id, @RequestBody Universite updatedUniversite) {
        Universite result = universiteService.updateUniversite(id, updatedUniversite);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportUniversitesToPdf() {
        try {
            // Create a temporary file
            String fileName = "universites_export_" + System.currentTimeMillis() + ".pdf";
            universiteService.exportUniversiteToPdf(fileName);

            // Read the file back into byte array
            byte[] pdfBytes = Files.readAllBytes(Paths.get(fileName));

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "universites_export.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            // Delete the temporary file
            Files.deleteIfExists(Paths.get(fileName));

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportUniversitesToExcel() {
        try {
            // Create a temporary file
            String fileName = "universites_export_" + System.currentTimeMillis() + ".xlsx";
            universiteService.exportUniversiteToExcel(fileName);

            // Create input stream from the file
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=universites_export.xlsx");
            headers.add(HttpHeaders.CACHE_CONTROL, "must-revalidate, post-check=0, pre-check=0");

            // Return the response
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .body(new InputStreamResource(fileInputStream));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

   /* @GetMapping("/advanced-filter")
    public ResponseEntity<List<Universite>> advancedFilter(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String pays,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String foyerNom,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(defaultValue = "contains") String searchMode) {

        try {
            List<Universite> result = universiteService.advancedFilter(
                    nom, ville, pays, email,
                    foyerNom, minCapacity, maxCapacity,
                    UniversiteService.MatchMode.fromString(searchMode));
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    } */
// In ControllerUniversite.java
   @GetMapping("/filter")
   public List<Universite> filterUniversites(
           @RequestParam(required = false) String nom,
           @RequestParam(required = false) String ville,
           @RequestParam(required = false) String pays,
           @RequestParam(required = false) String email) {
       return universiteService.filterUniversites(nom, ville,pays, email);
   }
}
