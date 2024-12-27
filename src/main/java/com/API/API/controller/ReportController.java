package com.API.API.controller;

import com.API.API.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Endpoint for summary report
    @GetMapping("/summary")
    public Map<String, Object> getSummaryReport() {
        return reportService.generateSummaryReport();
    }

    // Endpoint for customer report
    @GetMapping("/customer")
    public List<Map<String, Object>> getCustomerReport() {
        return reportService.generateCustomerReport();
    }

    // Endpoint for project type report
    @GetMapping("/project-type")
    public List<Map<String, Object>> getProjectTypeReport() {
        return reportService.generateProjectTypeReport();
    }
}
