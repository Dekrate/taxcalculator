package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for TaxCalculator main method and processUserInput.
 */
class TaxCalculatorMainTest {

    private InputStream originalSystemIn;

    /**
     * Save original System.in before each test.
     */
    @BeforeEach
    void setUp() {
        originalSystemIn = System.in;
    }

    /**
     * Restore original System.in after each test.
     */
    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
    }

    /**
     * Test main method with valid employment contract input.
     */
    @Test
    void testMainWithValidEmploymentInput() {
        String input = "5000\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        assertDoesNotThrow(() -> TaxCalculator.main(new String[]{}));
    }

    /**
     * Test main method with valid civil contract input.
     */
    @Test
    void testMainWithValidCivilInput() {
        String input = "5000\nC\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        assertDoesNotThrow(() -> TaxCalculator.main(new String[]{}));
    }

    /**
     * Test main method with invalid income - covers catch
     * IllegalArgumentException.
     */
    @Test
    void testMainWithInvalidIncomeCatchesException() {
        String input = "abc\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        // Should not throw - exception is caught inside main
        TaxCalculator.main(new String[]{});
    }

    /**
     * Test main method with invalid contract type - covers catch
     * IllegalArgumentException.
     */
    @Test
    void testMainWithInvalidContractTypeCatchesException() {
        String input = "5000\nX\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        // Should not throw - exception is caught inside main
        TaxCalculator.main(new String[]{});
    }

    /**
     * Test main method with negative income - covers catch
     * IllegalArgumentException.
     */
    @Test
    void testMainWithNegativeIncomeCatchesException() {
        String input = "-100\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        // Should not throw - exception is caught inside main
        TaxCalculator.main(new String[]{});
    }

    /**
     * Test main method with IOException by closing stream early.
     */
    @Test
    void testMainWithIOExceptionCatchesException() {
        // Create a stream that will cause IOException
        InputStream failingStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Simulated IO error");
            }
        };
        System.setIn(failingStream);

        // Should not throw - IOException is caught inside main
        TaxCalculator.main(new String[]{});
    }

    /**
     * Test processUserInput with valid employment input.
     */
    @Test
    void testProcessUserInputWithValidEmployment() {
        String input = "5000\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        TaxCalculator calculator = new TaxCalculator();
        assertDoesNotThrow(calculator::processUserInput);
    }

    /**
     * Test processUserInput with valid civil input.
     */
    @Test
    void testProcessUserInputWithValidCivil() {
        String input = "5000\nC\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        TaxCalculator calculator = new TaxCalculator();
        assertDoesNotThrow(calculator::processUserInput);
    }

    /**
     * Test processUserInput with zero income.
     */
    @Test
    void testProcessUserInputWithZeroIncome() {
        String input = "0\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        TaxCalculator calculator = new TaxCalculator();
        assertDoesNotThrow(calculator::processUserInput);
    }

    /**
     * Test processUserInput with large income.
     */
    @Test
    void testProcessUserInputWithLargeIncome() {
        String input = "100000\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        TaxCalculator calculator = new TaxCalculator();
        assertDoesNotThrow(calculator::processUserInput);
    }

    /**
     * Test processUserInput with decimal income.
     */
    @Test
    void testProcessUserInputWithDecimalIncome() {
        String input = "5000.50\nC\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        TaxCalculator calculator = new TaxCalculator();
        assertDoesNotThrow(calculator::processUserInput);
    }

    /**
     * Test processUserInput throws IllegalArgumentException on invalid
     * input.
     */
    @Test
    void testProcessUserInputThrowsOnInvalidIncome() {
        String input = "invalid\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        TaxCalculator calculator = new TaxCalculator();
        assertThrows(IllegalArgumentException.class,
                calculator::processUserInput);
    }

    /**
     * Test processUserInput throws IllegalArgumentException on invalid
     * contract type.
     */
    @Test
    void testProcessUserInputThrowsOnInvalidContractType() {
        String input = "5000\nZ\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        TaxCalculator calculator = new TaxCalculator();
        assertThrows(IllegalArgumentException.class,
                calculator::processUserInput);
    }

    /**
     * Test processUserInput throws IOException on stream error.
     */
    @Test
    void testProcessUserInputThrowsIOException() {
        InputStream failingStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Simulated IO error");
            }
        };
        System.setIn(failingStream);

        TaxCalculator calculator = new TaxCalculator();
        assertThrows(IOException.class, calculator::processUserInput);
    }

    /**
     * Test main with empty arguments.
     */
    @Test
    void testMainWithEmptyArguments() {
        String input = "1000\nE\n";
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        assertDoesNotThrow(() -> TaxCalculator.main(new String[]{}));
    }
}
