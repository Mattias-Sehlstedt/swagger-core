package io.swagger.v3.core.filter.resources;

import io.swagger.v3.core.filter.AbstractSpecFilter;
import io.swagger.v3.core.model.ApiDescription;
import io.swagger.v3.oas.models.Operation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Sample filter to remove the /flights resource. Note that /flights/x remains
 **/
public class NoSimpleFlightsOperationsFilter extends AbstractSpecFilter {

    public static final String SIMPLE_FLIGHTS_RESOURCE = "/flights";

    @Override
    public Optional<Operation> filterOperation(Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        if (api.getPath().equals(SIMPLE_FLIGHTS_RESOURCE)) {
            return Optional.empty();
        }
        return Optional.of(operation);
    }
}