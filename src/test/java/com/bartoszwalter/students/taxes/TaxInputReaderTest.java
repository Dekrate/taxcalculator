package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for TaxInputReader class.
 */
class TaxInputReaderTest {

    private TaxInputReader reader;
    private InputStream inputStream;

    /**
     * Clean up after each test.
     */
    @AfterEach
    void tearDown() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    /**
     * Test readIncome with valid input.
     */
    @Test
    void testReadIncomeWithValidInput() throws IOException {
        inputStream = new ByteArrayInputStream("5000.00\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        BigDecimal income = reader.readIncome();
        assertEquals(new BigDecimal("5000.00"), income);
    }

    /**
     * Test readIncome with integer input.
     */
    @Test
    void testReadIncomeWithIntegerInput() throws IOException {
        inputStream = new ByteArrayInputStream("5000\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        BigDecimal income = reader.readIncome();
        assertEquals(new BigDecimal("5000"), income);
    }

    /**
     * Test readIncome with empty input.
     */
    @Test
    void testReadIncomeWithEmptyInput() {
        inputStream = new ByteArrayInputStream("\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        assertThrows(IllegalArgumentException.class,
                () -> reader.readIncome());
    }

    /**
     * Test readIncome with null input.
     */
    @Test
    void testReadIncomeWithNullInput() {
        inputStream = new ByteArrayInputStream("".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        assertThrows(IllegalArgumentException.class,
                () -> reader.readIncome());
    }

    /**
     * Test readIncome with negative input.
     */
    @Test
    void testReadIncomeWithNegativeInput() {
        inputStream = new ByteArrayInputStream("-100\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        assertThrows(IllegalArgumentException.class,
                () -> reader.readIncome());
    }

    /**
     * Test readIncome with invalid format.
     */
    @Test
    void testReadIncomeWithInvalidFormat() {
        inputStream = new ByteArrayInputStream("abc\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        assertThrows(IllegalArgumentException.class,
                () -> reader.readIncome());
    }

    /**
     * Test readIncome with whitespace.
     */
    @Test
    void testReadIncomeWithWhitespace() throws IOException {
        inputStream = new ByteArrayInputStream("  5000.00  \n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        BigDecimal income = reader.readIncome();
        assertEquals(new BigDecimal("5000.00"), income);
    }

    /**
     * Test readContractType with 'E'.
     */
    @Test
    void testReadContractTypeEmployment() throws IOException {
        inputStream = new ByteArrayInputStream("E\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        ContractType type = reader.readContractType();
        assertEquals(ContractType.EMPLOYMENT, type);
    }

    /**
     * Test readContractType with 'C'.
     */
    @Test
    void testReadContractTypeCivil() throws IOException {
        inputStream = new ByteArrayInputStream("C\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        ContractType type = reader.readContractType();
        assertEquals(ContractType.CIVIL, type);
    }

    /**
     * Test readContractType with lowercase 'e'.
     */
    @Test
    void testReadContractTypeWithLowercase() throws IOException {
        inputStream = new ByteArrayInputStream("e\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        ContractType type = reader.readContractType();
        assertEquals(ContractType.EMPLOYMENT, type);
    }

    /**
     * Test readContractType with empty input.
     */
    @Test
    void testReadContractTypeWithEmptyInput() {
        inputStream = new ByteArrayInputStream("\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        assertThrows(IllegalArgumentException.class,
                () -> reader.readContractType());
    }

    /**
     * Test readContractType with null input.
     */
    @Test
    void testReadContractTypeWithNullInput() {
        inputStream = new ByteArrayInputStream("".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        assertThrows(IllegalArgumentException.class,
                () -> reader.readContractType());
    }

    /**
     * Test readContractType with invalid code.
     */
    @Test
    void testReadContractTypeWithInvalidCode() {
        inputStream = new ByteArrayInputStream("X\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        assertThrows(IllegalArgumentException.class,
                () -> reader.readContractType());
    }

    /**
     * Test readContractType with whitespace.
     */
    @Test
    void testReadContractTypeWithWhitespace() throws IOException {
        inputStream = new ByteArrayInputStream("  E  \n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        ContractType type = reader.readContractType();
        assertEquals(ContractType.EMPLOYMENT, type);
    }

    /**
     * Test close method.
     */
    @Test
    void testClose() throws IOException {
        inputStream = new ByteArrayInputStream("5000\n".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        reader.close();
        // No exception should be thrown
    }

    /**
     * Test constructor creates reader.
     */
    @Test
    void testConstructor() {
        inputStream = new ByteArrayInputStream("".getBytes(
                StandardCharsets.UTF_8));
        reader = new TaxInputReader(inputStream);

        // Reader should be created without exception
    }
}
