package sgf.gateway.integration.metrics.tds.transformer.impl;

import sgf.gateway.integration.metrics.tds.transformer.FieldSpecifiedStrategy;

public class FieldSpecifiedStrategyImpl implements FieldSpecifiedStrategy {

    private static final String unspecifiedField = "-";

    @Override
    public Boolean isSpecified(String field) {

        Boolean specified = false;
        String trimmedField = field.trim();

        if (!trimmedField.equals(unspecifiedField) && !trimmedField.isEmpty()) {
            specified = true;
        }

        return specified;
    }
}
