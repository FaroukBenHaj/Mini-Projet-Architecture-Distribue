package com.example.microservicenada.Services;
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
                    universite.setAdresse(updatedUniversite.getAdresse());
                    universite.setEmail(updatedUniversite.getEmail());
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
        headerRow.createCell(2).setCellValue("Address");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Foyer Name");
        headerRow.createCell(5).setCellValue("Foyer Capacity");

        // Populate rows with data
        int rowNum = 1;
        for (Universite universite : universites) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(universite.getId());
            row.createCell(1).setCellValue(universite.getNom());
            row.createCell(2).setCellValue(universite.getAdresse());
            row.createCell(3).setCellValue(universite.getEmail());

            if (universite.getFoyer() != null) {
                row.createCell(4).setCellValue(universite.getFoyer().getNom());
                row.createCell(5).setCellValue(universite.getFoyer().getCapacite());
            }
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

            // Create table with 6 columns
            float[] columnWidths = {50f, 100f, 150f, 100f, 100f, 80f};
            Table table = new Table(columnWidths);

            // Add headers
            table.addHeaderCell(new Cell().add(new Paragraph("ID")));
            table.addHeaderCell(new Cell().add(new Paragraph("Name")));
            table.addHeaderCell(new Cell().add(new Paragraph("Address")));
            table.addHeaderCell(new Cell().add(new Paragraph("Email")));
            table.addHeaderCell(new Cell().add(new Paragraph("Foyer Name")));
            table.addHeaderCell(new Cell().add(new Paragraph("Foyer Capacity")));

            // Add data rows
            for (Universite universite : universites) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(universite.getId()))));
                table.addCell(new Cell().add(new Paragraph(universite.getNom())));
                table.addCell(new Cell().add(new Paragraph(universite.getAdresse())));
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
}
