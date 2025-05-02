// model/SalaryCalculator.java
package com.example.mohapi_parollmanagement_system;

public class SalaryCalculator {

    // Constants for easy configuration
    private static final double HOURLY_RATE = 10.0;
    private static final double DEDUCTION_RATE = 0.10;

    /**
     * Calculates the gross salary.
     * @param basicSalary The basic salary.
     * @param workingHours The number of extra working hours.
     * @return The gross salary.
     */
    public double calculateGross(double basicSalary, double workingHours) {
        return basicSalary + (workingHours * HOURLY_RATE);
    }

    /**
     * Calculates total deductions based on gross salary.
     * @param gross The gross salary.
     * @return The amount deducted.
     */
    public double calculateDeductions(double gross) {
        return gross * DEDUCTION_RATE;
    }

    /**
     * Calculates the net salary.
     * @param gross The gross salary.
     * @param deductions The total deductions.
     * @return The net salary.
     */
    public double calculateNet(double gross, double deductions) {
        return gross - deductions;
    }
}
