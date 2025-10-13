package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;

/**
 * Strategy interface for calculating taxes based on contract type.
 */
public interface TaxCalculationStrategy {

    /**
     * Calculates tax for the given gross income.
     *
     * @param grossIncome the gross income amount
     * @return the tax calculation result
     * @throws IllegalArgumentException if grossIncome is null or negative
     */
    TaxResult calculateTax(BigDecimal grossIncome);

}
