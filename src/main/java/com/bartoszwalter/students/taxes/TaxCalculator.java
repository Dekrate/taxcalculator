package com.bartoszwalter.students.taxes;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main calculator class for computing taxes based on contract type.
 * Uses Strategy pattern to handle different contract types.
 */
public class TaxCalculator {

    private static final Logger LOGGER = Logger.getLogger(TaxCalculator.class.getName());

    private final Map<ContractType, TaxCalculationStrategy> strategies;
    private final TaxResultFormatter formatter;

    /**
     * Creates a new TaxCalculator with all available strategies.
     */
    public TaxCalculator() {
        this.strategies = new EnumMap<>(ContractType.class);
        this.strategies.put(ContractType.EMPLOYMENT, new EmploymentContractStrategy());
        this.strategies.put(ContractType.CIVIL, new CivilContractStrategy());
        this.formatter = new TaxResultFormatter();
    }

    /**
     * Calculates tax based on income and contract type.
     *
     * @param grossIncome the gross income amount
     * @param contractType the type of contract
     * @return the tax calculation result
     * @throws IllegalArgumentException if contract type is not supported or grossIncome is invalid
     */
    public TaxResult calculateTax(BigDecimal grossIncome, ContractType contractType) {
        if (grossIncome == null || grossIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Gross income must be non-negative");
        }

        TaxCalculationStrategy strategy = strategies.get(contractType);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported contract type: " + contractType);
        }

        return strategy.calculateTax(grossIncome);
    }

    /**
     * Reads input from the user and performs tax calculation.
     *
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if user input is invalid
     */
    public void processUserInput() throws IOException {
        TaxInputReader inputReader = new TaxInputReader(System.in);

        try {
            BigDecimal income = inputReader.readIncome();
            ContractType contractType = inputReader.readContractType();

            TaxResult result = calculateTax(income, contractType);
            formatter.displayResult(result, contractType);
        } finally {
            inputReader.close();
        }
    }

    /**
     * Main entry point of the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        TaxCalculator calculator = new TaxCalculator();

        try {
            calculator.processUserInput();
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid input: {0}", e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading input", e);
        }
    }
}
