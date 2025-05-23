package io.swagger.v3.core.oas.models;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.Hidden;

/**
 * Enum holds values different from names.  Schema model will derive Integer value from jackson annotation JsonValue on public method.
 */
public enum JacksonIntegerValueEnum {
    FIRST(2),
    SECOND(4),
    THIRD(6),
    @Hidden HIDDEN(-1);

    private final int value;

    JacksonIntegerValueEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }
}
