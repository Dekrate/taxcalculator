package com.bartoszwalter.students.taxes;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the type of employment contract.
 */
public enum ContractType {
    /**
     * Employment contract (Umowa o pracę).
     */
    EMPLOYMENT('E', "EMPLOYMENT"),

    /**
     * Civil contract (Umowa cywilnoprawna).
     */
    CIVIL('C', "CIVIL");

    private static final Map<Character, ContractType> CODE_MAP =
            Stream.of(values()).collect(Collectors.toMap(type -> type.code, type -> type));

    private final char code;
    private final String displayName;

    ContractType(char code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * Returns the contract type based on the character code.
     *
     * @param code the character code
     * @return the corresponding ContractType
     * @throws IllegalArgumentException if the code is invalid
     */
    public static ContractType fromCode(char code) {
        ContractType type = CODE_MAP.get(code);
        if (type == null) {
            throw new IllegalArgumentException("Unknown contract type: " + code);
        }
        return type;
    }

    /**
     * Gets the character code for this contract type.
     *
     * @return the character code
     */
    public char getCode() {
        return code;
    }

    /**
     * Gets the display name for this contract type.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
