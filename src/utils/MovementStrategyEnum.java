package utils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MovementStrategyEnum {
    BASIC,
    TIME,
    SUPPORT_BY,
    ONLY_UNDER;

    @JsonCreator
    public static MovementStrategyEnum fromString(String value) {
        return MovementStrategyEnum.valueOf(value.toUpperCase());
    }
}
