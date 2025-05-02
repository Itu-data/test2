package com.example.mohapi_parollmanagement_system;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.example.mohapipayroll_management_system.Database; // Corrected import

import java.io.FileOutputStream;
import java.sql.*;

public class PayslipGenerator {

    public static void generatePayslip(int employeeId, String filePath) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // ✅ Use the correct method name from your Database class
            conn = Database.connect();
            if (conn == null) {
                System.out.println("Failed to establish database connection.");
                return;
            }

            // Fetch employee and payroll info using JOIN
            String query = "SELECT e.employee_id, e.name, e.department, e.position, " +
                    "p.basic_salary, p.overtime, p.deductions, p.net_salary " +
                    "FROM employee e JOIN payroll p ON e.employee_id = p.employee_id " +
                    "WHERE e.employee_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Employee or payroll data not found for employee_id: " + employeeId);
                return;
            }

            // Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Payslip\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Employee details
            document.add(new Paragraph("Employee ID: " + rs.getInt("employee_id")));
            document.add(new Paragraph("Name: " + rs.getString("name")));
            document.add(new Paragraph("Department: " + rs.getString("department")));
            document.add(new Paragraph("Position: " + rs.getString("position")));
            document.add(new Paragraph(" ")); // spacing

            // Salary breakdown table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Description");
            table.addCell("Amount");

            table.addCell("Basic Salary");
            table.addCell(formatAmount(rs.getDouble("basic_salary")));

            table.addCell("Overtime");
            table.addCell(formatAmount(rs.getDouble("overtime")));

            table.addCell("Deductions");
            table.addCell(formatAmount(rs.getDouble("deductions")));

            table.addCell("Net Salary");
            table.addCell(formatAmount(rs.getDouble("net_salary")));

            document.add(table);

            document.close();
            System.out.println("Payslip generated: " + filePath);

        } catch (Exception e) {
            System.out.println("Error generating payslip.");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to format the amount as a string
    private static String formatAmount(double amount) {
        if (amount == 0) {
            return "$0.00"; // handle case when amount is 0
        }
        return String.format("$%.2f", amount);  // Format as currency
    }

    public static void main(String[] args) {
        generatePayslip(1, "Payslip_1.pdf");
    }
}
