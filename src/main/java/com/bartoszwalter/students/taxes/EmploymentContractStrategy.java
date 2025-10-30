package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;

/**
 * Tax calculation strategy for employment contracts (Umowa o pracę).
 * <p>
 * This class is designed for extension. Subclasses should override
 * the calculateTax method to provide custom tax calculation logic
 * while maintaining the contract's invariants.
 * </p>
 */
public class EmploymentContractStrategy
        implements TaxCalculationStrategy {

    /**
     * Calculates tax for employment contract.
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

        // Health insurance based on gross income
        HealthInsurance healthInsurance =
                HealthInsurance.calculate(grossIncome);

        // Tax deductible expenses (fixed for employment)
        BigDecimal taxDeductibleExpenses =
                TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES;

        // Taxable income
        BigDecimal taxableIncome =
                grossIncome.subtract(taxDeductibleExpenses);
        BigDecimal roundedTaxableIncome = taxableIncome
                .setScale(TaxConstants.INTEGER_SCALE,
                        TaxConstants.FLOOR_ROUNDING_MODE);

        // Calculate advance tax
        BigDecimal advanceTax = roundedTaxableIncome
                .multiply(TaxRates.ADVANCE_TAX_RATE)
                .divide(TaxRates.PERCENTAGE_DIVISOR,
                        TaxConstants.CURRENCY_SCALE,
                        TaxConstants.CURRENCY_ROUNDING_MODE);

        // Tax free income applies to employment contracts
        BigDecimal taxFreeIncome = TaxRates.TAX_FREE_INCOME;

        // Calculate tax after reductions
        BigDecimal reducedTax = advanceTax.subtract(taxFreeIncome);

        // Final tax to pay
        BigDecimal finalTax =
                reducedTax.subtract(healthInsurance.getDeductibleRate());
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
