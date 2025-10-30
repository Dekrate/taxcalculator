package com.bartoszwalter.students.taxes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the type of employment contract.
 */
@Getter
@AllArgsConstructor
public enum ContractType {
    /**
     * Employment contract.
     */
    EMPLOYMENT('E', "EMPLOYMENT"),

    /**
     * Civil contract.
     */
    CIVIL('C', "CIVIL");

    /**
     * Map for quick lookup by character code.
     */
    private static final Map<Character, ContractType> CODE_MAP =
            Stream.of(values()).collect(
                    Collectors.toMap(type -> type.code, type -> type));

    /**
     * Character code for a contract type.
     */
    private final char code;
    /**
     * Display name for this contract type.
     */
    private final String displayName;

    /**
     * Returns the contract type based on the character code.
     *
     * @param code the character code
     * @return the corresponding ContractType
     * @throws IllegalArgumentException if the code is invalid
     */
    public static ContractType fromCode(final char code) {
        ContractType type = CODE_MAP.get(code);
        if (type == null) {
            throw new IllegalArgumentException(
                    "Unknown contract type: " + code);
        }
        return type;
    }

}
