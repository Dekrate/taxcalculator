package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for TaxConstants class.
 */
class TaxConstantsTest {

    /**
     * Test that constructor throws exception.
     */
    @Test
    void testConstructorThrowsException() {
        try {
            java.lang.reflect.Constructor<TaxConstants> constructor =
                    TaxConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            java.lang.reflect.InvocationTargetException exception =
                    assertThrows(
                            java.lang.reflect.InvocationTargetException.class,
                            constructor::newInstance);

            assertEquals(UnsupportedOperationException.class,
                    exception.getCause().getClass());
            assertEquals("Utility class", exception.getCause().getMessage());
        } catch (NoSuchMethodException _) {
            // Expected - constructor exists
        }
    }

    /**
     * Test CURRENCY_SCALE constant.
     */
    @Test
    void testCurrencyScale() {
        assertEquals(2, TaxConstants.CURRENCY_SCALE);
    }

    /**
     * Test CURRENCY_ROUNDING_MODE constant.
     */
    @Test
    void testCurrencyRoundingMode() {
        assertNotNull(TaxConstants.CURRENCY_ROUNDING_MODE);
        assertEquals(RoundingMode.HALF_UP,
                TaxConstants.CURRENCY_ROUNDING_MODE);
    }

    /**
     * Test FLOOR_ROUNDING_MODE constant.
     */
    @Test
    void testFloorRoundingMode() {
        assertNotNull(TaxConstants.FLOOR_ROUNDING_MODE);
        assertEquals(RoundingMode.DOWN,
                TaxConstants.FLOOR_ROUNDING_MODE);
    }

    /**
     * Test INTEGER_SCALE constant.
     */
    @Test
    void testIntegerScale() {
        assertEquals(0, TaxConstants.INTEGER_SCALE);
    }
}

