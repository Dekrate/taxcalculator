package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for EmploymentContractStrategy class.
 */
class EmploymentContractStrategyTest {

    private final EmploymentContractStrategy strategy =
            new EmploymentContractStrategy();

    /**
     * Test calculateTax with typical income.
     */
    @Test
    void testCalculateTaxWithTypicalIncome() {
        BigDecimal grossIncome = new BigDecimal("5000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        assertNotNull(result);
        assertEquals(grossIncome, result.grossIncome());
        assertNotNull(result.socialContributions());
        assertNotNull(result.healthInsurance());
        assertEquals(TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES,
                result.taxDeductibleExpenses());
        assertEquals(new BigDecimal("46.33"), result.taxFreeIncome());
        assertNotNull(result.finalTax());
        assertNotNull(result.netIncome());
    }

    /**
     * Test calculateTax with null income.
     */
    @Test
    void testCalculateTaxWithNullIncome() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateTax(null));
    }

    /**
     * Test calculateTax with negative income.
     */
    @Test
    void testCalculateTaxWithNegativeIncome() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateTax(new BigDecimal("-100")));
    }

    /**
     * Test calculateTax with zero income.
     */
    @Test
    void testCalculateTaxWithZeroIncome() {
        BigDecimal grossIncome = BigDecimal.ZERO;
        TaxResult result = strategy.calculateTax(grossIncome);

        assertNotNull(result);
        assertEquals(grossIncome, result.grossIncome());
    }

    /**
     * Test calculateTax with small income.
     */
    @Test
    void testCalculateTaxWithSmallIncome() {
        BigDecimal grossIncome = new BigDecimal("1000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        assertNotNull(result);
        assertEquals(grossIncome, result.grossIncome());
        assertEquals(new BigDecimal("888"), result.taxableIncome());
    }

    /**
     * Test calculateTax with large income.
     */
    @Test
    void testCalculateTaxWithLargeIncome() {
        BigDecimal grossIncome = new BigDecimal("10000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        assertNotNull(result);
        assertEquals(grossIncome, result.grossIncome());
        assertNotNull(result.netIncome());
    }

    /**
     * Test that health insurance is calculated from gross income.
     */
    @Test
    void testHealthInsuranceCalculation() {
        BigDecimal grossIncome = new BigDecimal("1000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        HealthInsurance healthInsurance = result.healthInsurance();
        assertEquals(new BigDecimal("90.00"), healthInsurance.getFullRate());
        assertEquals(new BigDecimal("77.50"),
                healthInsurance.getDeductibleRate());
    }

    /**
     * Test that taxable income is rounded down.
     */
    @Test
    void testTaxableIncomeRoundedDown() {
        BigDecimal grossIncome = new BigDecimal("1000.99");
        TaxResult result = strategy.calculateTax(grossIncome);

        assertEquals(new BigDecimal("889"), result.taxableIncome());
    }

    /**
     * Test final tax calculation with deductions.
     */
    @Test
    void testFinalTaxCalculation() {
        BigDecimal grossIncome = new BigDecimal("5000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        // Verify that final tax is properly calculated
        assertNotNull(result.finalTax());
        assertEquals(0, result.finalTax().scale());
    }
}


