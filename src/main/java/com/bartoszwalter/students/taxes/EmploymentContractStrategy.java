package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;

/**
 * Tax calculation strategy for employment contracts (Umowa o pracę).
 */
public class EmploymentContractStrategy implements TaxCalculationStrategy {

    @Override
    public TaxResult calculateTax(BigDecimal grossIncome) {
        if (grossIncome == null || grossIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Gross income must be non-negative");
        }

        // Calculate social contributions
        SocialContributions socialContributions = SocialContributions.calculate(grossIncome);

        // Health insurance based on gross income
        HealthInsurance healthInsurance = HealthInsurance.calculate(grossIncome);

        // Tax deductible expenses (fixed for employment)
        BigDecimal taxDeductibleExpenses = TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES;

        // Taxable income
        BigDecimal taxableIncome = grossIncome.subtract(taxDeductibleExpenses);
        BigDecimal roundedTaxableIncome = taxableIncome.setScale(TaxConstants.INTEGER_SCALE,
            TaxConstants.FLOOR_ROUNDING_MODE);

        // Calculate advance tax
        BigDecimal advanceTax = roundedTaxableIncome.multiply(TaxRates.ADVANCE_TAX_RATE)
            .divide(TaxRates.PERCENTAGE_DIVISOR, TaxConstants.CURRENCY_SCALE, TaxConstants.CURRENCY_ROUNDING_MODE);

        // Tax free income applies to employment contracts
        BigDecimal taxFreeIncome = TaxRates.TAX_FREE_INCOME;

        // Calculate tax after reductions
        BigDecimal reducedTax = advanceTax.subtract(taxFreeIncome);

        // Final tax to pay
        BigDecimal finalTax = reducedTax.subtract(healthInsurance.getDeductibleRate());
        BigDecimal roundedFinalTax = finalTax.setScale(TaxConstants.INTEGER_SCALE, TaxConstants.CURRENCY_ROUNDING_MODE);

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
