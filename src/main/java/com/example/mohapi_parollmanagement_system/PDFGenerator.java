package com.example.mohapi_parollmanagement_system;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.example.mohapipayroll_management_system.Database; // <-- Correct import

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class PDFGenerator {

    public static void generatePayslip(int employeeId, String filePath) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet employeeResultSet = null;
        ResultSet payrollResultSet = null;

        try {
            // Establish the database connection
            conn = Database.connect(); // Using your static connect method

            // Fetch employee info
            stmt = conn.prepareStatement("SELECT * FROM employee WHERE employee_id = ?");
            stmt.setInt(1, employeeId);
            employeeResultSet = stmt.executeQuery();

            if (!employeeResultSet.next()) {
                System.out.println("Employee not found.");
                return;
            }

            // Fetch payroll info
            stmt = conn.prepareStatement("SELECT * FROM payroll WHERE employee_id = ?");
            stmt.setInt(1, employeeId);
            payrollResultSet = stmt.executeQuery();

            if (!payrollResultSet.next()) {
                System.out.println("Payroll not found.");
                return;
            }

            // Create PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Payroll Payslip\n\n", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            // Employee details
            document.add(new Paragraph("Employee ID: " + employeeResultSet.getInt("employee_id")));
            document.add(new Paragraph("Name: " + employeeResultSet.getString("name")));
            document.add(new Paragraph("Position: " + employeeResultSet.getString("position")));
            document.add(new Paragraph("Department: " + employeeResultSet.getString("department")));
            document.add(new Paragraph(" "));

            // Salary Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Description");
            table.addCell("Amount");

            // Add salary components - Ensure non-null values
            table.addCell("Basic Salary");
            table.addCell(formatAmount(payrollResultSet, "basic_salary"));

            table.addCell("Overtime");
            table.addCell(formatAmount(payrollResultSet, "overtime"));

            table.addCell("Deductions");
            table.addCell(formatAmount(payrollResultSet, "deductions"));

            table.addCell("Gross Salary");
            table.addCell(formatAmount(payrollResultSet, "gross_salary"));

            table.addCell("Net Salary");
            table.addCell(formatAmount(payrollResultSet, "net_salary"));

            document.add(table);
            document.close();

            System.out.println("Payslip generated at: " + filePath);

        } catch (SQLException | DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (employeeResultSet != null) employeeResultSet.close();
                if (payrollResultSet != null) payrollResultSet.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to format the amount as a string
    private static String formatAmount(ResultSet rs, String column) throws SQLException {
        double amount = rs.getDouble(column);
        return String.format("$%.2f", amount);  // Format as currency
    }

    // Test method
    public static void main(String[] args) {
        generatePayslip(1, "Payslip_Employee_1.pdf");
    }
}
