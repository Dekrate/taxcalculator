package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;

/**
 * Represents the result of tax calculation.
 */
public record TaxResult(BigDecimal grossIncome, SocialContributions socialContributions,
                        HealthInsurance healthInsurance, BigDecimal taxDeductibleExpenses,
                        BigDecimal taxableIncome, BigDecimal advanceTax, BigDecimal taxFreeIncome,
                        BigDecimal finalTax, BigDecimal netIncome) {
}
