package com.bartoszwalter.students.taxes;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents social security contributions.
 */

@Getter
final class SocialContributions {

    /**
     * The social security contribution.
     */
    private final BigDecimal socialSecurity;

    /**
     * The health social security contribution.
     */
    private final BigDecimal healthSocialSecurity;

    /**
     * The sickness social security contribution.
     */
    private final BigDecimal sicknessSocialSecurity;

    /**
     * Creates social contributions with specified values.
     *
     * @param socialSecurityValue the social security contribution
     * @param healthSocialSecurityValue the health social security
     *                                  contribution
     * @param sicknessSocialSecurityValue the sickness social security
     *                                    contribution
     */
    private SocialContributions(final BigDecimal socialSecurityValue,
                                final BigDecimal healthSocialSecurityValue,
                                final BigDecimal sicknessSocialSecurityValue) {
        this.socialSecurity = socialSecurityValue;
        this.healthSocialSecurity = healthSocialSecurityValue;
        this.sicknessSocialSecurity = sicknessSocialSecurityValue;
    }

    /**
     * Calculates social contributions from gross income.
     *
     * @param grossIncome the gross income
     * @return calculated social contributions
     * @throws IllegalArgumentException if grossIncome is null or negative
     */
    public static SocialContributions calculate(
            final BigDecimal grossIncome) {
        if (grossIncome == null
                || grossIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Gross income must be non-negative");
        }

        BigDecimal socialSecurityCalculated = grossIncome
                .multiply(TaxRates.SOCIAL_SECURITY_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);
        BigDecimal healthSocialSecurityCalculated = grossIncome
                .multiply(TaxRates.HEALTH_SOCIAL_SECURITY_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);
        BigDecimal sicknessSocialSecurityCalculated = grossIncome
                .multiply(TaxRates.SICKNESS_SOCIAL_SECURITY_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);

        return new SocialContributions(socialSecurityCalculated,
                healthSocialSecurityCalculated,
                sicknessSocialSecurityCalculated);
    }

    /**
     * Gets the total of all social contributions.
     *
     * @return total social contributions
     */
    public BigDecimal getTotal() {
        return socialSecurity.add(healthSocialSecurity)
                .add(sicknessSocialSecurity);
    }

    /**
     * Gets the income after social contributions deduction.
     *
     * @param grossIncome the gross income
     * @return income after social contributions
     */
    public BigDecimal getIncomeAfterContributions(
            final BigDecimal grossIncome) {
        return grossIncome.subtract(getTotal());
    }

}
