package sgf.gateway.integration.metrics.tds.transformer.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogEntryTokenizerTest {

    private LogEntryTokenizerImpl tokenizer;

    @Before
    public void setup() {

        List<String> keys = new ArrayList<String>();

        keys.add("key1");
        keys.add("key2");
        keys.add("key3");

        tokenizer = new LogEntryTokenizerImpl(keys);
    }

    @Test
    public void tokenCount() {

        String entry = "value1 'value 2' '-'";

        Map<String, String> tokens = tokenizer.tokenize(entry);

        assertThat(tokens.size(), is(3));
    }

    @Test
    public void tokenValue() {

        String entry = "value1 'value2' 'value 3'";

        Map<String, String> tokens = tokenizer.tokenize(entry);

        assertThat(tokens.get("key1"), is("value1"));
        assertThat(tokens.get("key2"), is("value2"));
        assertThat(tokens.get("key3"), is("value 3"));
    }

    @Test
    public void tokenEmptyValue() {

        String entry = "- '-' ''";

        Map<String, String> tokens = tokenizer.tokenize(entry);

        assertThat(tokens.get("key1"), is("-"));
        assertThat(tokens.get("key2"), is("-"));
        assertThat(tokens.get("key3"), is(""));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void incorrectFieldCount() {

        String entry = "value1 'value2'";

        Map<String, String> tokens = tokenizer.tokenize(entry);
    }
}
