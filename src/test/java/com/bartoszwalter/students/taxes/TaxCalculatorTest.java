package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for TaxCalculator class.
 */
class TaxCalculatorTest {

    private final TaxCalculator calculator = new TaxCalculator();

    /**
     * Test calculateTax with employment contract.
     */
    @Test
    void testCalculateTaxWithEmploymentContract() {
        BigDecimal income = new BigDecimal("5000.00");
        TaxResult result = calculator.calculateTax(income,
                ContractType.EMPLOYMENT);

        assertNotNull(result);
        assertEquals(income, result.grossIncome());
    }

    /**
     * Test calculateTax with civil contract.
     */
    @Test
    void testCalculateTaxWithCivilContract() {
        BigDecimal income = new BigDecimal("5000.00");
        TaxResult result = calculator.calculateTax(income,
                ContractType.CIVIL);

        assertNotNull(result);
        assertEquals(income, result.grossIncome());
    }

    /**
     * Test calculateTax with null income.
     */
    @Test
    void testCalculateTaxWithNullIncome() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTax(null,
                        ContractType.EMPLOYMENT));
    }

    /**
     * Test calculateTax with negative income.
     */
    @Test
    void testCalculateTaxWithNegativeIncome() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTax(new BigDecimal("-100"),
                        ContractType.EMPLOYMENT));
    }

    /**
     * Test calculateTax with zero income.
     */
    @Test
    void testCalculateTaxWithZeroIncome() {
        BigDecimal income = BigDecimal.ZERO;
        TaxResult result = calculator.calculateTax(income,
                ContractType.EMPLOYMENT);

        assertNotNull(result);
        assertEquals(income, result.grossIncome());
    }

    /**
     * Test that different strategies produce different results.
     */
    @Test
    void testDifferentStrategiesProduceDifferentResults() {
        BigDecimal income = new BigDecimal("5000.00");

        TaxResult employmentResult = calculator.calculateTax(income,
                ContractType.EMPLOYMENT);
        TaxResult civilResult = calculator.calculateTax(income,
                ContractType.CIVIL);

        // Employment has tax free income, civil doesn't
        assertEquals(new BigDecimal("46.33"),
                employmentResult.taxFreeIncome());
        assertEquals(BigDecimal.ZERO, civilResult.taxFreeIncome());

        // Different tax deductible expenses
        assertEquals(TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES,
                employmentResult.taxDeductibleExpenses());
    }

    /**
     * Test constructor creates calculator with strategies.
     */
    @Test
    void testConstructorCreatesStrategies() {
        TaxCalculator newCalculator = new TaxCalculator();
        BigDecimal income = new BigDecimal("1000.00");

        TaxResult result = newCalculator.calculateTax(income,
                ContractType.EMPLOYMENT);
        assertNotNull(result);
    }

    /**
     * Test calculateTax with large income.
     */
    @Test
    void testCalculateTaxWithLargeIncome() {
        BigDecimal income = new BigDecimal("50000.00");
        TaxResult result = calculator.calculateTax(income,
                ContractType.EMPLOYMENT);

        assertNotNull(result);
        assertEquals(income, result.grossIncome());
    }

    /**
     * Test calculateTax with null contract type.
     */
    @Test
    void testCalculateTaxWithNullContractType() {
        BigDecimal income = new BigDecimal("5000.00");
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTax(income, null));
    }
}



