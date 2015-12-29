package sgf.gateway.validation.type;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidURIValidatorTest {

    private ValidURIValidator validator;

    @Before
    public void setup() {
        this.validator = new ValidURIValidator();
    }

    @Test
    public void ifNullValueThenValid() {

        assertThat(this.validator.isValid(null, null), is(true));
    }

    @Test
    public void ifEmptyStringThenPass() {

        assertThat(this.validator.isValid("", null), is(true));
    }

    @Test
    public void ifBlankWhiteSpaceThenFail() {

        assertThat(this.validator.isValid(" ", null), is(false));
    }

    @Test
    public void ifNoSchemaThenFail() {

        assertThat(this.validator.isValid("test.com", null), is(false));
    }

    @Test
    public void ifNoHostThenFail() {

        assertThat(this.validator.isValid("http:", null), is(false));
    }

    @Test
    public void ifInvalidUriThenFail() {

        assertThat(this.validator.isValid("http://", null), is(false));
    }

    @Test
    public void ifValidUriThenPass() {

        assertThat(this.validator.isValid("http://test.com", null), is(true));
    }
}
