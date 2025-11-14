package io.swagger.v3.core.resolving.v31.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class ModelWithNullable {

    @Schema(nullable = true)
    private String property;

    @Schema(types = {"string", "null"})
    private String property2;

    public String getProperty() {
        return property;
    }

    public String getProperty2() {
        return property2;
    }

}
