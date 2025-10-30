package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;


/**
 * Represents the result of a tax calculation.
 * @param grossIncome Gross income of the person.
 * @param socialContributions Social contributions of the person.
 * @param healthInsurance Health insurance of the person.
 * @param taxDeductibleExpenses Tax-deductible expenses of the person.
 * @param taxableIncome Taxable income of the person.
 * @param advanceTax Advance tax of the person.
 * @param taxFreeIncome Tax-free income of the person.
 * @param finalTax Final tax of the person.
 * @param netIncome Net income of the person.
 */
public record TaxResult(BigDecimal grossIncome,
                        SocialContributions socialContributions,
                        HealthInsurance healthInsurance,
                        BigDecimal taxDeductibleExpenses,
                        BigDecimal taxableIncome,
                        BigDecimal advanceTax,
                        BigDecimal taxFreeIncome,
                        BigDecimal finalTax,
                        BigDecimal netIncome) {
}
