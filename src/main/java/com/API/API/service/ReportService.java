package com.API.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Summary report with revenue by year, month, and project type
    public Map<String, Object> generateSummaryReport() {
        // Total revenue by year
        String revenueByYearQuery = "SELECT YEAR(paymentDate) AS year, SUM(amount) AS totalRevenue " +
                "FROM payment WHERE paymentStatus = 'Paid' GROUP BY YEAR(paymentDate)";
        List<Map<String, Object>> revenueByYear = jdbcTemplate.queryForList(revenueByYearQuery);

        // Total revenue by month
        String revenueByMonthQuery = "SELECT YEAR(paymentDate) AS year, MONTH(paymentDate) AS month, SUM(amount) AS totalRevenue " +
                "FROM payment WHERE paymentStatus = 'Paid' GROUP BY YEAR(paymentDate), MONTH(paymentDate)";
        List<Map<String, Object>> revenueByMonth = jdbcTemplate.queryForList(revenueByMonthQuery);

        // Revenue by project type
        String revenueByProjectTypeQuery = "SELECT pt.typeName, SUM(p.amount) AS totalRevenue " +
                "FROM project_type pt " +
                "JOIN project pr ON pt.projectTypeId = pr.projectTypeId " +
                "JOIN payment p ON pr.projectId = p.projectId " +
                "WHERE p.paymentStatus = 'Paid' " +
                "GROUP BY pt.typeName";
        List<Map<String, Object>> revenueByProjectType = jdbcTemplate.queryForList(revenueByProjectTypeQuery);

        return Map.of(
                "revenueByYear", revenueByYear,
                "revenueByMonth", revenueByMonth,
                "revenueByProjectType", revenueByProjectType
        );
    }

    // Customer report with project count and total payments
    public List<Map<String, Object>> generateCustomerReport() {
        String query = "SELECT c.name AS customerName, c.email AS customerEmail, " +
                "COUNT(DISTINCT pr.projectId) AS projectCount, " + // Count distinct projects
                "SUM(p.amount) AS totalPaid " +  // Total amount paid
                "FROM customer c " +
                "LEFT JOIN project pr ON c.customerId = pr.customerId " +
                "LEFT JOIN payment p ON c.customerId = p.customerId AND p.paymentStatus = 'Paid' " +
                "GROUP BY c.customerId";
        return jdbcTemplate.queryForList(query);
    }


    // Project type report with revenue per project type
    public List<Map<String, Object>> generateProjectTypeReport() {
        String query = "SELECT pt.typeName, SUM(p.amount) AS totalRevenue " +
                "FROM project_type pt " +
                "JOIN project pr ON pt.projectTypeId = pr.projectTypeId " +
                "JOIN payment p ON pr.projectId = p.projectId " +
                "WHERE p.paymentStatus = 'Paid' " +
                "GROUP BY pt.typeName";
        return jdbcTemplate.queryForList(query);
    }
}
