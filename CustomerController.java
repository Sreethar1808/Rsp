package com.example.DemoProject.RSProject.practice;

//import com.example.DemoProject.RSProject.practice.Service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerservice;

    @GetMapping("/download-excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
//        List<CustomerData> customers = customerRepository.findAll();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");

        byte[] excelData = customerservice.generateExcel();
        response.getOutputStream().write(excelData);
    }
}




