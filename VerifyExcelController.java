package com.example.DemoProject.RSProject.practice;

//import com.example.DemoProject.RSProject.service.VerifyExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/excel")
public class VerifyExcelController {

    private final VerifyExcelService excelValidationService;

    public VerifyExcelController(VerifyExcelService excelValidationService) {
        this.excelValidationService = excelValidationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateExcel(@RequestParam("file") MultipartFile file) throws IOException {
        UUID id = excelValidationService.validateExcel(file.getInputStream(), file.getOriginalFilename());
        return ResponseEntity.ok(id.toString());  // ✅ Return UUID as plain text
    }



}