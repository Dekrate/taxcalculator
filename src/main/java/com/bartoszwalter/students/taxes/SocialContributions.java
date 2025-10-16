package com.bartoszwalter.students.taxes;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represents social security contributions.
 */

@Getter
final class SocialContributions {

	private final BigDecimal socialSecurity;
	private final BigDecimal healthSocialSecurity;
	private final BigDecimal sicknessSocialSecurity;

	/**
	 * Creates social contributions with specified values.
	 *
	 * @param socialSecurity         the social security contribution
	 * @param healthSocialSecurity   the health social security contribution
	 * @param sicknessSocialSecurity the sickness social security contribution
	 */
	private SocialContributions(final BigDecimal socialSecurity, final BigDecimal healthSocialSecurity,
	                            final BigDecimal sicknessSocialSecurity) {
		this.socialSecurity = socialSecurity;
		this.healthSocialSecurity = healthSocialSecurity;
		this.sicknessSocialSecurity = sicknessSocialSecurity;
	}

	/**
	 * Calculates social contributions from gross income.
	 *
	 * @param grossIncome the gross income
	 * @return calculated social contributions
	 * @throws IllegalArgumentException if grossIncome is null or negative
	 */
	public static SocialContributions calculate(final BigDecimal grossIncome) {
		if (grossIncome == null || grossIncome.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Gross income must be non-negative");
		}

		BigDecimal socialSecurity = grossIncome.multiply(TaxRates.SOCIAL_SECURITY_RATE)
				.divide(TaxRates.PERCENTAGE_DIVISOR, TaxConstants.CURRENCY_SCALE, TaxConstants.CURRENCY_ROUNDING_MODE);
		BigDecimal healthSocialSecurity = grossIncome.multiply(TaxRates.HEALTH_SOCIAL_SECURITY_RATE)
				.divide(TaxRates.PERCENTAGE_DIVISOR, TaxConstants.CURRENCY_SCALE, TaxConstants.CURRENCY_ROUNDING_MODE);
		BigDecimal sicknessSocialSecurity = grossIncome.multiply(TaxRates.SICKNESS_SOCIAL_SECURITY_RATE)
				.divide(TaxRates.PERCENTAGE_DIVISOR, TaxConstants.CURRENCY_SCALE, TaxConstants.CURRENCY_ROUNDING_MODE);

		return new SocialContributions(socialSecurity, healthSocialSecurity, sicknessSocialSecurity);
	}

	/**
	 * Gets the total of all social contributions.
	 *
	 * @return total social contributions
	 */
	public BigDecimal getTotal() {
		return socialSecurity.add(healthSocialSecurity).add(sicknessSocialSecurity);
	}

	/**
	 * Gets the income after social contributions deduction.
	 *
	 * @param grossIncome the gross income
	 * @return income after social contributions
	 */
	public BigDecimal getIncomeAfterContributions(final BigDecimal grossIncome) {
		return grossIncome.subtract(getTotal());
	}

}
