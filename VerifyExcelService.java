package com.example.DemoProject.RSProject.practice;

//package com.example.DemoProject.RSProject.practice;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.ByteArrayInputStream;

@Service
public class VerifyExcelService {

	private final ExcelValidationRepository repository;

    public VerifyExcelService(ExcelValidationRepository repository) {
        this.repository = repository;
    }
    
    public UUID validateExcel(InputStream inputstream, String fName) throws IOException {
        List<String[]> validData = new ArrayList<>();
        List<String[]> invalidData = new ArrayList<>();
        String fileName = fName;
        
        Workbook workbook = new XSSFWorkbook(inputstream);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); 

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String customerId = getCellValue(row.getCell(0));
            String name = getCellValue(row.getCell(1));
            String phoneNumber = getCellValue(row.getCell(2));
            String dob = getCellValue(row.getCell(3));

            List<String> issues = new ArrayList<>();

            
            if (name == null || name.trim().isEmpty() || !name.matches("^[a-zA-Z ]+$")) {
                issues.add("Invalid Name");
            }

           
            if (phoneNumber == null || !phoneNumber.matches("^\\d{10}$")) {
                issues.add("Invalid Phone Number");
            }

            // Validate Date of Birth
            if (dob == null || dob.trim().isEmpty()) {
                issues.add("Date of Birth Missing");
            }

            if (issues.isEmpty()) {
                validData.add(new String[]{customerId, "Completed"});
            } else {
                invalidData.add(new String[]{customerId, String.join(", ", issues)});
            }
        }

        workbook.close();

        
        byte[] validExcel = createExcel(validData, new String[]{"Customer ID", "Status"});
        byte[] invalidExcel = createExcel(invalidData, new String[]{"Customer ID", "Issue"});

        UUID id = UUID.randomUUID();
        ExcelValidationResult validationResult = new ExcelValidationResult(id,fileName, LocalDateTime.now(), (validData.size()+invalidData.size()),validData.size(),invalidData.size(),  validExcel, invalidExcel);

        ExcelValidationResult finalResult =saveValidationResult(validationResult);
        return id;
    }
    
    @Transactional
    public ExcelValidationResult saveValidationResult(ExcelValidationResult validationResult) {
        return repository.save(validationResult);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }

    private byte[] createExcel(List<String[]> data, String[] headers) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet("Sheet1");

        
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        
        int rowNum = 1;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                row.createCell(i).setCellValue(rowData[i]);
            }
        }

        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

//    private byte[] createZip(byte[] validFile, byte[] invalidFile) throws IOException {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
//            
//            
//            if (validFile != null && validFile.length > 0) {
//                ZipEntry validEntry = new ZipEntry("ValidData.xlsx");
//                zipOutputStream.putNextEntry(validEntry);
//                zipOutputStream.write(validFile);
//                zipOutputStream.closeEntry();
//            }
//
//            
//            if (invalidFile != null && invalidFile.length > 0) {
//                ZipEntry invalidEntry = new ZipEntry("InvalidData.xlsx");
//                zipOutputStream.putNextEntry(invalidEntry);
//                zipOutputStream.write(invalidFile);
//                zipOutputStream.closeEntry();
//            }
//        }
//        return byteArrayOutputStream.toByteArray();
//    }

}
