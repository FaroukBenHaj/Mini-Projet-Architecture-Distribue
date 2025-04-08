package com.example.microservicenada.Services;
import com.example.microservicenada.Entities.Foyer;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.example.microservicenada.Entities.Universite;
import com.example.microservicenada.Repositories.UniversiteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;




@Service
@RequiredArgsConstructor
public class UniversiteService {
    private final UniversiteRepository universiteRepository;

    public List<Universite> getAllUniversites() {
        return universiteRepository.findAll();
    }

    public Optional<Universite> getUniversiteById(Long id) {
        return universiteRepository.findById(id);
    }

    public Universite saveUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    public void deleteUniversite(Long id) {
        universiteRepository.deleteById(id);
    }

    public Universite updateUniversite(Long id, Universite updatedUniversite) {
        return universiteRepository.findById(id)
                .map(universite -> {
                    universite.setNom(updatedUniversite.getNom());
                    universite.setVille(updatedUniversite.getVille());
                    universite.setPays(updatedUniversite.getPays());universite.setEmail(updatedUniversite.getEmail());
                    universite.setFoyer(updatedUniversite.getFoyer());
                    return universiteRepository.save(universite);
                })
                .orElse(null);
    }

    public void exportUniversiteToExcel(String fileName) throws IOException {
        List<Universite> universites = universiteRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Universites");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("City");
        headerRow.createCell(3).setCellValue("Country");
        headerRow.createCell(4).setCellValue("Email");
        headerRow.createCell(5).setCellValue("Foyer Name");
        headerRow.createCell(6).setCellValue("Foyer Capacity");

        // Populate rows with data
        int rowNum = 1;
        for (Universite universite : universites) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(universite.getId());
            row.createCell(1).setCellValue(universite.getNom());
            row.createCell(2).setCellValue(universite.getVille());
            row.createCell(3).setCellValue(universite.getPays());
            row.createCell(4).setCellValue(universite.getEmail());

            if (universite.getFoyer() != null) {
                row.createCell(5).setCellValue(universite.getFoyer().getNom());
                row.createCell(6).setCellValue(universite.getFoyer().getCapacite()); }
        }

        // Write the Excel file to disk
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    public void exportUniversiteToPdf(String fileName) throws IOException {
        List<Universite> universites = universiteRepository.findAll();

        try (PdfWriter writer = new PdfWriter(new FileOutputStream(fileName));
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Add title
            document.add(new Paragraph("Universities Report")
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                    .setFontSize(16));

            // Create table with 7 columns (added one for country)
            float[] columnWidths = {50f, 100f, 100f, 100f, 100f, 100f, 80f};
            Table table = new Table(columnWidths);

            // Add headers
            table.addHeaderCell(new Cell().add(new Paragraph("ID")));
            table.addHeaderCell(new Cell().add(new Paragraph("Name")));
            table.addHeaderCell(new Cell().add(new Paragraph("City")));
            table.addHeaderCell(new Cell().add(new Paragraph("Country")));
            table.addHeaderCell(new Cell().add(new Paragraph("Email")));
            table.addHeaderCell(new Cell().add(new Paragraph("Foyer Name")));
            table.addHeaderCell(new Cell().add(new Paragraph("Foyer Capacity")));

            // Add data rows
            for (Universite universite : universites) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(universite.getId()))));
                table.addCell(new Cell().add(new Paragraph(universite.getNom())));
                table.addCell(new Cell().add(new Paragraph(universite.getVille())));
                table.addCell(new Cell().add(new Paragraph(universite.getPays())));
                table.addCell(new Cell().add(new Paragraph(universite.getEmail())));

                if (universite.getFoyer() != null) {
                    table.addCell(new Cell().add(new Paragraph(universite.getFoyer().getNom())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(universite.getFoyer().getCapacite()))));
                } else {
                    table.addCell(new Cell().add(new Paragraph("N/A")));
                    table.addCell(new Cell().add(new Paragraph("N/A")));
                }
            }

            document.add(table);

            // Add footer
            document.add(new Paragraph("Generated on: " + java.time.LocalDate.now())
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.RIGHT)
                    .setFontSize(10));
        }
    }

    /* public List<Universite> advancedFilter(
            String nom, String ville, String pays,
            String email, String foyerNom,
            Integer minCapacity, Integer maxCapacity,
            MatchMode matchMode) {

        return universiteRepository.findAll().stream()
                // Direct field filters
                .filter(u -> matchText(u.getNom(), nom, matchMode))
                .filter(u -> matchText(u.getVille(), ville, matchMode))
                .filter(u -> matchText(u.getPays(), pays, matchMode))
                .filter(u -> matchText(u.getEmail(), email, matchMode))

                // Foyer filters
                .filter(u -> foyerNom == null ||
                        (u.getFoyer() != null &&
                                matchText(u.getFoyer().getNom(), foyerNom, matchMode)))
                .filter(u -> checkCapacity(u.getFoyer(), minCapacity, maxCapacity))
                .toList();
    }

    // Text matching utility
    private boolean matchText(String value, String searchTerm, MatchMode mode) {
        if (searchTerm == null) return true;
        if (value == null) return false;

        String val = value.toLowerCase();
        String term = searchTerm.toLowerCase();

        return switch (mode) {
            case EXACT -> val.equals(term);
            case STARTS_WITH -> val.startsWith(term);
            case ENDS_WITH -> val.endsWith(term);
            case CONTAINS -> val.contains(term);
            case REGEX -> val.matches(term);
        };
    }

    // Capacity filter utility
    private boolean checkCapacity(Foyer foyer, Integer min, Integer max) {
        if (foyer == null) return min == null && max == null;
        return (min == null || foyer.getCapacite() >= min) &&
                (max == null || foyer.getCapacite() <= max);
    }

    // Match mode enum
    public enum MatchMode {
        EXACT, STARTS_WITH, ENDS_WITH, CONTAINS, REGEX;

        public static MatchMode fromString(String mode) {
            try {
                return valueOf(mode.toUpperCase());
            } catch (Exception e) {
                return CONTAINS; // default fallback
            }
        }
    } */
    public List<Universite> filterUniversites(String nom, String ville, String pays, String email) {
        return universiteRepository.findAll().stream()
                .filter(u -> nom == null || Optional.ofNullable(u.getNom())
                        .map(s -> s.toLowerCase().contains(nom.toLowerCase()))
                        .orElse(false))
                .filter(u -> ville == null || Optional.ofNullable(u.getVille())
                        .map(s -> s.toLowerCase().contains(ville.toLowerCase()))
                        .orElse(false))
                .filter(u -> pays == null || Optional.ofNullable(u.getPays())
                        .map(s -> s.toLowerCase().contains(pays.toLowerCase()))
                        .orElse(false))
                .filter(u -> email == null || Optional.ofNullable(u.getEmail())
                        .map(s -> s.toLowerCase().contains(email.toLowerCase()))
                        .orElse(false))
                .toList();
    }
}

