package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for SocialContributions class.
 */
class SocialContributionsTest {

    /**
     * Test calculate with valid income.
     */
    @Test
    void testCalculateWithValidIncome() {
        BigDecimal income = new BigDecimal("1000.00");
        SocialContributions result = SocialContributions.calculate(income);

        assertNotNull(result);
        assertEquals(new BigDecimal("97.60"),
                result.getSocialSecurity());
        assertEquals(new BigDecimal("15.00"),
                result.getHealthSocialSecurity());
        assertEquals(new BigDecimal("24.50"),
                result.getSicknessSocialSecurity());
    }

    /**
     * Test calculate with null income.
     */
    @Test
    void testCalculateWithNullIncome() {
        assertThrows(IllegalArgumentException.class,
                () -> SocialContributions.calculate(null));
    }

    /**
     * Test calculate with negative income.
     */
    @Test
    void testCalculateWithNegativeIncome() {
        BigDecimal negativeIncome = BigDecimal.valueOf(-100);
        assertThrows(IllegalArgumentException.class,
                () -> SocialContributions.calculate(negativeIncome));
    }

    /**
     * Test getTotal.
     */
    @Test
    void testGetTotal() {
        BigDecimal income = new BigDecimal("1000.00");
        SocialContributions result = SocialContributions.calculate(income);

        BigDecimal total = result.getTotal();
        assertEquals(new BigDecimal("137.10"), total);
    }

    /**
     * Test getIncomeAfterContributions.
     */
    @Test
    void testGetIncomeAfterContributions() {
        BigDecimal income = new BigDecimal("1000.00");
        SocialContributions result = SocialContributions.calculate(income);

        BigDecimal afterContributions =
                result.getIncomeAfterContributions(income);
        assertEquals(new BigDecimal("862.90"), afterContributions);
    }

    /**
     * Test calculate with zero income.
     */
    @Test
    void testCalculateWithZeroIncome() {
        BigDecimal income = BigDecimal.ZERO;
        SocialContributions result = SocialContributions.calculate(income);

        assertNotNull(result);
        assertEquals(new BigDecimal("0.00"),
                result.getSocialSecurity());
        assertEquals(new BigDecimal("0.00"),
                result.getHealthSocialSecurity());
        assertEquals(new BigDecimal("0.00"),
                result.getSicknessSocialSecurity());
    }

    /**
     * Test calculate with large income.
     */
    @Test
    void testCalculateWithLargeIncome() {
        BigDecimal income = new BigDecimal("10000.00");
        SocialContributions result = SocialContributions.calculate(income);

        assertNotNull(result);
        assertEquals(new BigDecimal("976.00"),
                result.getSocialSecurity());
        assertEquals(new BigDecimal("150.00"),
                result.getHealthSocialSecurity());
        assertEquals(new BigDecimal("245.00"),
                result.getSicknessSocialSecurity());
    }
}

