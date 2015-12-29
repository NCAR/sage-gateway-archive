package sgf.gateway.integration.metrics.tds.transformer.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldSpecifiedStrategyTest {

    private FieldSpecifiedStrategyImpl strategy;

    @Before
    public void setup() {
        strategy = new FieldSpecifiedStrategyImpl();
    }

    @Test
    public void testDash() {
        Boolean isSpecified = strategy.isSpecified("-");
        assertFalse("Dash means not specified", isSpecified);
    }

    @Test
    public void testEmpty() {
        Boolean isSpecified = strategy.isSpecified("");
        assertFalse("Empty means not specified", isSpecified);
    }

    @Test
    public void testWhitespace() {
        Boolean isSpecified = strategy.isSpecified("  ");
        assertFalse("Whitespace means not specified", isSpecified);
    }

    @Test
    public void testDashWithWhitespace() {
        Boolean isSpecified = strategy.isSpecified(" - ");
        assertFalse("Dash means not specified", isSpecified);
    }

    @Test
    public void testValue() {
        Boolean isSpecified = strategy.isSpecified("value");
        assertTrue("Non whitespace means specified", isSpecified);
    }
}
