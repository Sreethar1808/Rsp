package com.example.DemoProject.RSProject.practice;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.*;

@Entity
@Table(name = "excel_validation_result")
public class ExcelValidationResult {
    
    @Id
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "total_count")
    private int totalCount;

    @Column(name = "valid_count")
    private int validCount;

    @Column(name = "invalid_count")
    private int invalidCount;

    @Lob
    @Column(name = "valid_file", columnDefinition = "LONGBLOB")
    private byte[] validFile;

    @Lob
    @Column(name = "invalid_file", columnDefinition = "LONGBLOB")
    private byte[] invalidFile;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "uploaded_date", nullable = false)
    private LocalDateTime uploadedDate;

    public ExcelValidationResult() {
        this.uuid = UUID.randomUUID();  // Ensure UUID is set on creation
    }

    public ExcelValidationResult(UUID id,String fileName, LocalDateTime uploadedDate, int totalCount, int validCount, int invalidCount, byte[] validFile, byte[] invalidFile) {
        this.uuid=id;
        this.fileName = fileName;
        this.uploadedDate = uploadedDate;
        this.totalCount = totalCount;
        this.validCount = validCount;
        this.invalidCount = invalidCount;
        this.validFile = validFile;
        this.invalidFile = invalidFile;
    }

    // Getters and Setters...

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getValidCount() {
        return validCount;
    }

    public void setValidCount(int validCount) {
        this.validCount = validCount;
    }

    public int getInvalidCount() {
        return invalidCount;
    }

    public void setInvalidCount(int invalidCount) {
        this.invalidCount = invalidCount;
    }

    public byte[] getValidFile() {
        return validFile;
    }

    public void setValidFile(byte[] validFile) {
        this.validFile = validFile;
    }

    public byte[] getInvalidFile() {
        return invalidFile;
    }

    public void setInvalidFile(byte[] invalidFile) {
        this.invalidFile = invalidFile;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}
