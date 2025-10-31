package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for TaxRates constants class.
 */
class TaxRatesTest {

    /**
     * Test that constructor throws exception.
     */
    @Test
    void testConstructorThrowsException() {
        try {
            java.lang.reflect.Constructor<TaxRates> constructor =
                    TaxRates.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            java.lang.reflect.InvocationTargetException exception =
                    assertThrows(
                            java.lang.reflect.InvocationTargetException.class,
                            constructor::newInstance);

            assertEquals(UnsupportedOperationException.class,
                    exception.getCause().getClass());
            assertEquals("Utility class", exception.getCause().getMessage());
        } catch (NoSuchMethodException _) {
            // Expected - constructor exists
        }
    }

    /**
     * Test SOCIAL_SECURITY_RATE constant.
     */
    @Test
    void testSocialSecurityRate() {
        assertNotNull(TaxRates.SOCIAL_SECURITY_RATE);
        assertEquals(TaxRates.SOCIAL_SECURITY_RATE,
                new BigDecimal("9.76"));
    }

    /**
     * Test HEALTH_SOCIAL_SECURITY_RATE constant.
     */
    @Test
    void testHealthSocialSecurityRate() {
        assertNotNull(TaxRates.HEALTH_SOCIAL_SECURITY_RATE);
        assertEquals(TaxRates.HEALTH_SOCIAL_SECURITY_RATE,
                new BigDecimal("1.5"));
    }

    /**
     * Test SICKNESS_SOCIAL_SECURITY_RATE constant.
     */
    @Test
    void testSicknessSocialSecurityRate() {
        assertNotNull(TaxRates.SICKNESS_SOCIAL_SECURITY_RATE);
        assertEquals(TaxRates.SICKNESS_SOCIAL_SECURITY_RATE,
                new BigDecimal("2.45"));
    }

    /**
     * Test HEALTH_INSURANCE_FULL_RATE constant.
     */
    @Test
    void testHealthInsuranceFullRate() {
        assertNotNull(TaxRates.HEALTH_INSURANCE_FULL_RATE);
        assertEquals(TaxRates.HEALTH_INSURANCE_FULL_RATE,
                new BigDecimal("9.0"));
    }

    /**
     * Test HEALTH_INSURANCE_DEDUCTIBLE_RATE constant.
     */
    @Test
    void testHealthInsuranceDeductibleRate() {
        assertNotNull(TaxRates.HEALTH_INSURANCE_DEDUCTIBLE_RATE);
        assertEquals(TaxRates.HEALTH_INSURANCE_DEDUCTIBLE_RATE,
                new BigDecimal("7.75"));
    }

    /**
     * Test ADVANCE_TAX_RATE constant.
     */
    @Test
    void testAdvanceTaxRate() {
        assertNotNull(TaxRates.ADVANCE_TAX_RATE);
        assertEquals(TaxRates.ADVANCE_TAX_RATE,
                new BigDecimal("18.0"));
    }

    /**
     * Test EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES constant.
     */
    @Test
    void testEmploymentTaxDeductibleExpenses() {
        assertNotNull(TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES);
        assertEquals(TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES,
                new BigDecimal("111.25"));
    }

    /**
     * Test TAX_FREE_INCOME constant.
     */
    @Test
    void testTaxFreeIncome() {
        assertNotNull(TaxRates.TAX_FREE_INCOME);
        assertEquals(TaxRates.TAX_FREE_INCOME,
                new BigDecimal("46.33"));
    }

    /**
     * Test CIVIL_CONTRACT_DEDUCTIBLE_RATE constant.
     */
    @Test
    void testCivilContractDeductibleRate() {
        assertNotNull(TaxRates.CIVIL_CONTRACT_DEDUCTIBLE_RATE);
        assertEquals(TaxRates.CIVIL_CONTRACT_DEDUCTIBLE_RATE,
                new BigDecimal("20.0"));
    }

    /**
     * Test PERCENTAGE_DIVISOR constant.
     */
    @Test
    void testPercentageDivisor() {
        assertNotNull(TaxRates.PERCENTAGE_DIVISOR);
        assertEquals(TaxRates.PERCENTAGE_DIVISOR,
                new BigDecimal("100.0"));
    }
}

