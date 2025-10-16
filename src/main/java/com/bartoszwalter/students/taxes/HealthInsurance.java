package com.bartoszwalter.students.taxes;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents health insurance contributions.
 */
@Getter
final class HealthInsurance {

    private final BigDecimal fullRate;
    private final BigDecimal deductibleRate;

    /**
     * Creates health insurance with specified values.
     *
     * @param fullRate the full health insurance rate
     * @param deductibleRate the tax-deductible health insurance rate
     */
    private HealthInsurance(final BigDecimal fullRate, final BigDecimal deductibleRate) {
        this.fullRate = fullRate;
        this.deductibleRate = deductibleRate;
    }

    /**
     * Calculates health insurance from income basis.
     *
     * @param incomeBasis the income basis for calculation
     * @return calculated health insurance
     * @throws IllegalArgumentException if incomeBasis is null or negative
     */
    public static HealthInsurance calculate(BigDecimal incomeBasis) {
        if (incomeBasis == null || incomeBasis.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Income basis must be non-negative");
        }

        BigDecimal fullRate = incomeBasis.multiply(TaxRates.HEALTH_INSURANCE_FULL_RATE)
            .divide(TaxRates.PERCENTAGE_DIVISOR, TaxConstants.CURRENCY_SCALE, TaxConstants.CURRENCY_ROUNDING_MODE);
        BigDecimal deductibleRate = incomeBasis.multiply(TaxRates.HEALTH_INSURANCE_DEDUCTIBLE_RATE)
            .divide(TaxRates.PERCENTAGE_DIVISOR, TaxConstants.CURRENCY_SCALE, TaxConstants.CURRENCY_ROUNDING_MODE);

        return new HealthInsurance(fullRate, deductibleRate);
    }

}
