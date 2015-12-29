package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogEntryTokenizerTest {

    private LogEntryTokenizerImpl tokenizer;

    @Before
    public void setup() {
        tokenizer = new LogEntryTokenizerImpl();
    }

    @Test
    public void tokenCount() {

        String entry = "KEYA=Value.A KEYB=Value-B KEYC=Value:C";

        Map<String, String> tokens = tokenizer.tokenize(entry);

        assertThat(tokens.size(), is(3));
    }

    @Test
    public void tokenEmpty() {

        String entry = "KEYA=Value.A KEYB=";

        Map<String, String> tokens = tokenizer.tokenize(entry);

        assertThat(tokens.get("KEYA"), is("Value.A"));
        assertThat(tokens.get("KEYB"), is(""));
    }

    @Test
    public void tokenValue() {

        String entry = "KEYA=Value.A KEYB=Value-B KEYC=Value:C";

        Map<String, String> tokens = tokenizer.tokenize(entry);

        assertThat(tokens.get("KEYA"), is("Value.A"));
        assertThat(tokens.get("KEYB"), is("Value-B"));
        assertThat(tokens.get("KEYC"), is("Value:C"));
    }
}
