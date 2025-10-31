package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for CivilContractStrategy class.
 */
class CivilContractStrategyTest {

    private final CivilContractStrategy strategy =
            new CivilContractStrategy();

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
        assertEquals(BigDecimal.ZERO, result.taxFreeIncome());
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
        BigDecimal negativeIncome = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateTax(negativeIncome));
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
        assertEquals(BigDecimal.ZERO, result.taxFreeIncome());
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
     * Test that health insurance is calculated from income after
     * social contributions.
     */
    @Test
    void testHealthInsuranceCalculation() {
        BigDecimal grossIncome = new BigDecimal("1000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        HealthInsurance healthInsurance = result.healthInsurance();
        // Health insurance based on income after contributions (862.90)
        assertEquals(new BigDecimal("77.66"), healthInsurance.getFullRate());
        assertEquals(new BigDecimal("66.87"),
                healthInsurance.getDeductibleRate());
    }

    /**
     * Test that tax deductible expenses are 20% of income after
     * contributions.
     */
    @Test
    void testTaxDeductibleExpenses() {
        BigDecimal grossIncome = new BigDecimal("1000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        // 20% of 862.90 = 172.58
        assertEquals(new BigDecimal("172.58"),
                result.taxDeductibleExpenses());
    }

    /**
     * Test that taxable income is rounded down.
     */
    @Test
    void testTaxableIncomeRoundedDown() {
        BigDecimal grossIncome = new BigDecimal("1000.99");
        TaxResult result = strategy.calculateTax(grossIncome);

        assertNotNull(result.taxableIncome());
        assertEquals(0, result.taxableIncome().scale());
    }

    /**
     * Test final tax calculation without tax free income.
     */
    @Test
    void testFinalTaxCalculation() {
        BigDecimal grossIncome = new BigDecimal("5000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        // Verify that final tax is properly calculated
        assertNotNull(result.finalTax());
        assertEquals(0, result.finalTax().scale());
        assertEquals(BigDecimal.ZERO, result.taxFreeIncome());
    }

    /**
     * Test that income after contributions is used in calculations.
     */
    @Test
    void testIncomeAfterContributionsUsage() {
        BigDecimal grossIncome = new BigDecimal("5000.00");
        TaxResult result = strategy.calculateTax(grossIncome);

        SocialContributions contributions = result.socialContributions();
        BigDecimal incomeAfterContributions =
                contributions.getIncomeAfterContributions(grossIncome);

        // Verify health insurance is based on income after contributions
        HealthInsurance expectedHealth =
                HealthInsurance.calculate(incomeAfterContributions);
        assertEquals(expectedHealth.getFullRate(),
                result.healthInsurance().getFullRate());
    }
}

