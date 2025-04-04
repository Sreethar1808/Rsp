package com.example.DemoProject.RSProject.practice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public interface ExcelValidationRepository extends JpaRepository<ExcelValidationResult, UUID>{

}
