package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for HealthInsurance class.
 */
class HealthInsuranceTest {

    /**
     * Test calculate with valid income basis.
     */
    @Test
    void testCalculateWithValidIncomeBasis() {
        BigDecimal incomeBasis = new BigDecimal("1000.00");
        HealthInsurance result = HealthInsurance.calculate(incomeBasis);

        assertNotNull(result);
        assertEquals(new BigDecimal("90.00"), result.getFullRate());
        assertEquals(new BigDecimal("77.50"),
                result.getDeductibleRate());
    }

    /**
     * Test calculate with null income basis.
     */
    @Test
    void testCalculateWithNullIncomeBasis() {
        assertThrows(IllegalArgumentException.class,
                () -> HealthInsurance.calculate(null));
    }

    /**
     * Test calculate with negative income basis.
     */
    @Test
    void testCalculateWithNegativeIncomeBasis() {
        assertThrows(IllegalArgumentException.class,
                () -> HealthInsurance.calculate(new BigDecimal("-100")));
    }

    /**
     * Test calculate with zero income basis.
     */
    @Test
    void testCalculateWithZeroIncomeBasis() {
        BigDecimal incomeBasis = BigDecimal.ZERO;
        HealthInsurance result = HealthInsurance.calculate(incomeBasis);

        assertNotNull(result);
        assertEquals(new BigDecimal("0.00"), result.getFullRate());
        assertEquals(new BigDecimal("0.00"), result.getDeductibleRate());
    }

    /**
     * Test calculate with large income basis.
     */
    @Test
    void testCalculateWithLargeIncomeBasis() {
        BigDecimal incomeBasis = new BigDecimal("5000.00");
        HealthInsurance result = HealthInsurance.calculate(incomeBasis);

        assertNotNull(result);
        assertEquals(new BigDecimal("450.00"), result.getFullRate());
        assertEquals(new BigDecimal("387.50"),
                result.getDeductibleRate());
    }

    /**
     * Test calculate with decimal income basis.
     */
    @Test
    void testCalculateWithDecimalIncomeBasis() {
        BigDecimal incomeBasis = new BigDecimal("1234.56");
        HealthInsurance result = HealthInsurance.calculate(incomeBasis);

        assertNotNull(result);
        assertEquals(new BigDecimal("111.11"), result.getFullRate());
        assertEquals(new BigDecimal("95.68"), result.getDeductibleRate());
    }
}
