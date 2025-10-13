package com.bartoszwalter.students.taxes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Formats and displays tax calculation results.
 */
public class TaxResultFormatter {

    private static final Logger LOGGER = Logger.getLogger(TaxResultFormatter.class.getName());
    private static final String ROUNDED_LABEL = " rounded = ";

    /**
     * Displays the tax calculation result.
     *
     * @param result the tax result to display
     * @param contractType the contract type
     */
    public void displayResult(TaxResult result, ContractType contractType) {
        LOGGER.info(() -> String.format("Contract Type: %s", contractType.getDisplayName()));

        LOGGER.info(() -> String.format("Gross Income: %s",
            formatDecimal(result.grossIncome())));

        displaySocialContributions(result.socialContributions());
        displayHealthInsurance(result);
        displayTaxCalculation(result);

        LOGGER.info(() -> String.format("Net Income: %s",
            formatDecimal(result.netIncome())));
        LOGGER.info("");
    }

    /**
     * Displays social contributions.
     *
     * @param contributions the social contributions
     */
    private void displaySocialContributions(SocialContributions contributions) {
        LOGGER.info(() -> String.format("Social security tax: %s",
            formatDecimal(contributions.getSocialSecurity())));

        LOGGER.info(() -> String.format("Health social security tax: %s",
            formatDecimal(contributions.getHealthSocialSecurity())));

        LOGGER.info(() -> String.format("Sickness social security tax: %s",
            formatDecimal(contributions.getSicknessSocialSecurity())));
    }

    /**
     * Displays health insurance information.
     *
     * @param result the tax result
     */
    private void displayHealthInsurance(TaxResult result) {
        HealthInsurance health = result.healthInsurance();

        LOGGER.info(() -> String.format("Income basis for health social security: %s",
            formatDecimal(result.grossIncome())));

        LOGGER.info(() -> String.format("Health social security tax: 9%% = %s, 7.75%% = %s",
            formatDecimal(health.getFullRate()),
            formatDecimal(health.getDeductibleRate())));
    }

    /**
     * Displays tax calculation details.
     *
     * @param result the tax result
     */
    private void displayTaxCalculation(TaxResult result) {
        LOGGER.info(() -> String.format("Tax deductible expenses: %s",
            formatDecimal(result.taxDeductibleExpenses())));

        LOGGER.info(() -> String.format("Income to be taxed: %s%s%s",
            formatDecimal(result.taxableIncome()),
            ROUNDED_LABEL,
            formatInteger(result.taxableIncome())));

        LOGGER.info(() -> String.format("Advance tax 18%%: %s",
            formatDecimal(result.advanceTax())));

        if (result.taxFreeIncome().compareTo(BigDecimal.ZERO) > 0) {
            LOGGER.info(() -> String.format("Tax free income: %s",
                formatDecimal(result.taxFreeIncome())));

            BigDecimal reducedTax = result.advanceTax().subtract(result.taxFreeIncome());
            LOGGER.info(() -> String.format("Reduced tax: %s",
                formatDecimal(reducedTax)));
        }

        LOGGER.info(() -> String.format("Advance tax paid: %s%s%s",
            formatDecimal(result.finalTax()),
            ROUNDED_LABEL,
            formatInteger(result.finalTax())));
    }

    /**
     * Formats a BigDecimal value as decimal with 2 places.
     *
     * @param value the value to format
     * @return formatted string
     */
    private String formatDecimal(BigDecimal value) {
        DecimalFormat formatter = new DecimalFormat("#.00");
        return formatter.format(value);
    }

    /**
     * Formats a BigDecimal value as integer.
     *
     * @param value the value to format
     * @return formatted string
     */
    private String formatInteger(BigDecimal value) {
        DecimalFormat formatter = new DecimalFormat("#");
        return formatter.format(value);
    }
}
