package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for TaxResult record.
 */
class TaxResultTest {

    /**
     * Test creating TaxResult with all fields.
     */
    @Test
    void testCreateTaxResult() {
        BigDecimal grossIncome = new BigDecimal("5000.00");
        SocialContributions contributions =
                SocialContributions.calculate(grossIncome);
        HealthInsurance health =
                HealthInsurance.calculate(grossIncome);
        BigDecimal taxDeductible = new BigDecimal("111.25");
        BigDecimal taxableIncome = new BigDecimal("4888.75");
        BigDecimal advanceTax = new BigDecimal("879.98");
        BigDecimal taxFreeIncome = new BigDecimal("46.33");
        BigDecimal finalTax = new BigDecimal("356.00");
        BigDecimal netIncome = new BigDecimal("4093.90");

        TaxResult result = new TaxResult(
            grossIncome,
            contributions,
            health,
            taxDeductible,
            taxableIncome,
            advanceTax,
            taxFreeIncome,
            finalTax,
            netIncome
        );

        assertNotNull(result);
        assertEquals(grossIncome, result.grossIncome());
        assertEquals(contributions, result.socialContributions());
        assertEquals(health, result.healthInsurance());
        assertEquals(taxDeductible, result.taxDeductibleExpenses());
        assertEquals(taxableIncome, result.taxableIncome());
        assertEquals(advanceTax, result.advanceTax());
        assertEquals(taxFreeIncome, result.taxFreeIncome());
        assertEquals(finalTax, result.finalTax());
        assertEquals(netIncome, result.netIncome());
    }

    /**
     * Test TaxResult toString.
     */
    @Test
    void testTaxResultToString() {
        BigDecimal grossIncome = new BigDecimal("5000.00");
        SocialContributions contributions =
                SocialContributions.calculate(grossIncome);
        HealthInsurance health =
                HealthInsurance.calculate(grossIncome);

        TaxResult result = new TaxResult(
            grossIncome,
            contributions,
            health,
            new BigDecimal("111.25"),
            new BigDecimal("4888.75"),
            new BigDecimal("879.98"),
            new BigDecimal("46.33"),
            new BigDecimal("356.00"),
            new BigDecimal("4093.90")
        );

        String toString = result.toString();
        assertNotNull(toString);
        // Record toString contains field names and values
    }

    /**
     * Test TaxResult with zero values.
     */
    @Test
    void testTaxResultWithZeroValues() {
        BigDecimal zero = BigDecimal.ZERO;
        SocialContributions contributions =
                SocialContributions.calculate(zero);
        HealthInsurance health = HealthInsurance.calculate(zero);

        TaxResult result = new TaxResult(
            zero,
            contributions,
            health,
            zero,
            zero,
            zero,
            zero,
            zero,
            zero
        );

        assertNotNull(result);
        assertEquals(zero, result.grossIncome());
        assertEquals(zero, result.finalTax());
        assertEquals(zero, result.netIncome());
    }

    /**
     * Test TaxResult with large values.
     */
    @Test
    void testTaxResultWithLargeValues() {
        BigDecimal grossIncome = new BigDecimal("100000.00");
        SocialContributions contributions =
                SocialContributions.calculate(grossIncome);
        HealthInsurance health =
                HealthInsurance.calculate(grossIncome);

        TaxResult result = new TaxResult(
            grossIncome,
            contributions,
            health,
            new BigDecimal("111.25"),
            new BigDecimal("99888.75"),
            new BigDecimal("17979.98"),
            new BigDecimal("46.33"),
            new BigDecimal("8934"),
            new BigDecimal("81156.00")
        );

        assertNotNull(result);
        assertEquals(grossIncome, result.grossIncome());
    }
}


