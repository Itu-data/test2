package com.example.mohapi_parollmanagement_system;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {

    /**
     * Exports a list of employee payslip details to a CSV file.
     * @param employees List of employees
     * @param filePath Path to save the CSV file
     */
    public static void exportToCSV(List<Employee> employees, String filePath) {
        SalaryCalculator calculator = new SalaryCalculator();

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV headers
            writer.write("Name,Department,Position,Basic Salary,Working Hours,Gross,Deductions,Net\n");

            // Write data for each employee
            for (Employee emp : employees) {
                double gross = calculator.calculateGross(emp.getBasicSalary(), emp.getWorkingHours());
                double deductions = calculator.calculateDeductions(gross);
                double net = calculator.calculateNet(gross, deductions);

                writer.write(String.format("%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f\n",
                        emp.getName(),
                        emp.getDepartment(),
                        emp.getPosition(),
                        emp.getBasicSalary(),
                        emp.getWorkingHours(),
                        gross,
                        deductions,
                        net));
            }

            System.out.println("CSV export completed successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
