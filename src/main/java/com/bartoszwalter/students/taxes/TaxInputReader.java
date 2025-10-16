package com.bartoszwalter.students.taxes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Handles reading user input for tax calculations.
 * Separates input logic from calculation logic (Single Responsibility Principle).
 */
public class TaxInputReader {

    private static final Logger LOGGER = Logger.getLogger(TaxInputReader.class.getName());
    private final BufferedReader reader;

    /**
     * Creates a new input reader with the specified input stream.
     *
     * @param inputStream the input stream to read from
     */
    public TaxInputReader(final InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    /**
     * Reads income from user input with validation.
     *
     * @return the income amount
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if the input is not a valid number
     */
    public BigDecimal readIncome() throws IOException {
        LOGGER.info("Enter income: ");
        String input = reader.readLine();

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Income cannot be empty");
        }

        try {
            BigDecimal income = new BigDecimal(input.trim());
            if (income.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Income cannot be negative");
            }
            return income;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid income format: " + input, e);
        }
    }

    /**
     * Reads contract type from user input with validation.
     *
     * @return the contract type
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if the input is not valid
     */
    public ContractType readContractType() throws IOException {
        LOGGER.info("Contract Type: (E)mployment, (C)ivil: ");
        String input = reader.readLine();

        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Contract type cannot be empty");
        }

        char code = Character.toUpperCase(input.trim().charAt(0));
        return ContractType.fromCode(code);
    }

    /**
     * Closes the reader.
     *
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException {
        reader.close();
    }
}

