package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for ContractType enum.
 */
class ContractTypeTest {

    /**
     * Test that fromCode returns EMPLOYMENT for 'E'.
     */
    @Test
    void testFromCodeEmployment() {
        ContractType type = ContractType.fromCode('E');
        assertEquals(ContractType.EMPLOYMENT, type);
    }

    /**
     * Test that fromCode returns CIVIL for 'C'.
     */
    @Test
    void testFromCodeCivil() {
        ContractType type = ContractType.fromCode('C');
        assertEquals(ContractType.CIVIL, type);
    }

    /**
     * Test that fromCode throws exception for invalid code.
     */
    @Test
    void testFromCodeInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> ContractType.fromCode('X'));
    }

    /**
     * Test that fromCode throws exception for lowercase 'e'.
     */
    @Test
    void testFromCodeLowercase() {
        assertThrows(IllegalArgumentException.class,
                () -> ContractType.fromCode('e'));
    }

    /**
     * Test getCode for EMPLOYMENT.
     */
    @Test
    void testGetCodeEmployment() {
        assertEquals('E', ContractType.EMPLOYMENT.getCode());
    }

    /**
     * Test getCode for CIVIL.
     */
    @Test
    void testGetCodeCivil() {
        assertEquals('C', ContractType.CIVIL.getCode());
    }

    /**
     * Test getDisplayName for EMPLOYMENT.
     */
    @Test
    void testGetDisplayNameEmployment() {
        assertEquals("EMPLOYMENT",
                ContractType.EMPLOYMENT.getDisplayName());
    }

    /**
     * Test getDisplayName for CIVIL.
     */
    @Test
    void testGetDisplayNameCivil() {
        assertEquals("CIVIL", ContractType.CIVIL.getDisplayName());
    }

    /**
     * Test enum values.
     */
    @Test
    void testValues() {
        ContractType[] types = ContractType.values();
        assertEquals(2, types.length);
    }

    /**
     * Test enum valueOf.
     */
    @Test
    void testValueOf() {
        assertEquals(ContractType.EMPLOYMENT,
                ContractType.valueOf("EMPLOYMENT"));
        assertEquals(ContractType.CIVIL,
                ContractType.valueOf("CIVIL"));
    }
}



