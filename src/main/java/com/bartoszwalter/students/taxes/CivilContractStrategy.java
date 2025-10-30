package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;

/**
 * Tax calculation strategy for civil contracts (Umowa cywilnoprawna).
 * <p>
 * This class is designed for extension. Subclasses should override
 * the calculateTax method to provide custom tax calculation logic
 * while maintaining the contract's invariants.
 * </p>
 */
public class CivilContractStrategy implements TaxCalculationStrategy {

    /**
     * Calculates tax for civil contract.
     *
     * @param grossIncome the gross income amount
     * @return tax calculation result
     * @throws IllegalArgumentException if grossIncome is null or negative
     */
    @Override
    public TaxResult calculateTax(final BigDecimal grossIncome) {
        if (grossIncome == null
                || grossIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Gross income must be non-negative");
        }

        // Calculate social contributions
        SocialContributions socialContributions =
                SocialContributions.calculate(grossIncome);

        // Income after social contributions
        BigDecimal incomeAfterContributions =
                socialContributions
                        .getIncomeAfterContributions(grossIncome);

        // Income basis for health insurance
        HealthInsurance healthInsurance =
                HealthInsurance.calculate(incomeAfterContributions);

        // Tax deductible expenses (20% for civil contracts)
        BigDecimal taxDeductibleExpenses = incomeAfterContributions
                .multiply(TaxRates.CIVIL_CONTRACT_DEDUCTIBLE_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);

        // Taxable income
        BigDecimal taxableIncome =
                incomeAfterContributions.subtract(taxDeductibleExpenses);
        BigDecimal roundedTaxableIncome = taxableIncome
                .setScale(TaxConstants.INTEGER_SCALE,
                        TaxConstants.FLOOR_ROUNDING_MODE);

        // Calculate advance tax
        BigDecimal advanceTax = roundedTaxableIncome
                .multiply(TaxRates.ADVANCE_TAX_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);

        // No tax free income for civil contracts
        BigDecimal taxFreeIncome = BigDecimal.ZERO;

        // Final tax to pay (after health insurance deduction)
        BigDecimal finalTax = advanceTax
                .subtract(healthInsurance.getDeductibleRate())
                .subtract(taxFreeIncome);
        BigDecimal roundedFinalTax = finalTax
                .setScale(TaxConstants.INTEGER_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);

        // Calculate net income
        BigDecimal netIncome = grossIncome
            .subtract(socialContributions.getTotal())
            .subtract(healthInsurance.getFullRate())
            .subtract(roundedFinalTax);

        return new TaxResult(
            grossIncome,
            socialContributions,
            healthInsurance,
            taxDeductibleExpenses,
            roundedTaxableIncome,
            advanceTax,
            taxFreeIncome,
            roundedFinalTax,
            netIncome
        );
    }

}
