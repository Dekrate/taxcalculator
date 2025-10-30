package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;

/**
 * Contains tax rate constants used in calculations.
 */
public final class TaxRates {

    private TaxRates() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Social security contribution rate (9.76%).
     */
    public static final BigDecimal SOCIAL_SECURITY_RATE =
            new BigDecimal("9.76");

    /**
     * Health social security contribution rate (1.5%).
     */
    public static final BigDecimal HEALTH_SOCIAL_SECURITY_RATE =
            new BigDecimal("1.5");

    /**
     * Sickness social security contribution rate (2.45%).
     */
    public static final BigDecimal SICKNESS_SOCIAL_SECURITY_RATE =
            new BigDecimal("2.45");

    /**
     * Health insurance rate - full (9%).
     */
    public static final BigDecimal HEALTH_INSURANCE_FULL_RATE =
            new BigDecimal("9.0");

    /**
     * Health insurance rate - deductible (7.75%).
     */
    public static final BigDecimal HEALTH_INSURANCE_DEDUCTIBLE_RATE =
            new BigDecimal("7.75");

    /**
     * Advance tax rate (18%).
     */
    public static final BigDecimal ADVANCE_TAX_RATE =
            new BigDecimal("18.0");

    /**
     * Tax deductible expenses for employment contract (111.25 PLN).
     */
    public static final BigDecimal EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES =
            new BigDecimal("111.25");

    /**
     * Tax free income monthly (46.33 PLN).
     */
    public static final BigDecimal TAX_FREE_INCOME =
            new BigDecimal("46.33");

    /**
     * Civil contract tax deductible expenses rate (20%).
     */
    public static final BigDecimal CIVIL_CONTRACT_DEDUCTIBLE_RATE =
            new BigDecimal("20.0");

    /**
     * Percentage divisor constant.
     */
    public static final BigDecimal PERCENTAGE_DIVISOR = new BigDecimal("100.0");
}
