package com.bartoszwalter.students.taxes;

import java.math.RoundingMode;

/**
 * Contains calculation constants used throughout the application.
 */
public final class TaxConstants {

    private TaxConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Default scale for currency calculations (2 decimal places).
     */
    public static final int CURRENCY_SCALE = 2;

    /**
     * Default rounding mode for currency calculations.
     */
    public static final RoundingMode CURRENCY_ROUNDING_MODE =
            RoundingMode.HALF_UP;

    /**
     * Rounding mode for flooring values.
     */
    public static final RoundingMode FLOOR_ROUNDING_MODE = RoundingMode.DOWN;

    /**
     * Scale for integer calculations (no decimal places).
     */
    public static final int INTEGER_SCALE = 0;
}

