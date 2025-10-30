package com.bartoszwalter.students.taxes;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents health insurance contributions.
 */
@Getter
final class HealthInsurance {

    /**
     * The full health insurance rate.
     */
    private final BigDecimal fullRate;

    /**
     * The tax-deductible health insurance rate.
     */
    private final BigDecimal deductibleRate;

    /**
     * Creates health insurance with specified values.
     *
     * @param fullRateValue the full health insurance rate
     * @param deductibleRateValue the tax-deductible health insurance rate
     */
    private HealthInsurance(final BigDecimal fullRateValue,
                            final BigDecimal deductibleRateValue) {
        this.fullRate = fullRateValue;
        this.deductibleRate = deductibleRateValue;
    }

    /**
     * Calculates health insurance from income basis.
     *
     * @param incomeBasis the income basis for calculation
     * @return calculated health insurance
     * @throws IllegalArgumentException if incomeBasis is null or negative
     */
    public static HealthInsurance calculate(final BigDecimal incomeBasis) {
        if (incomeBasis == null
                || incomeBasis.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Income basis must be non-negative");
        }

        BigDecimal fullRateCalculated = incomeBasis
                .multiply(TaxRates.HEALTH_INSURANCE_FULL_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);
        BigDecimal deductibleRateCalculated = incomeBasis
                .multiply(TaxRates.HEALTH_INSURANCE_DEDUCTIBLE_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);

        return new HealthInsurance(fullRateCalculated,
                deductibleRateCalculated);
    }

}
