package sgf.gateway.validation.data;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShortNameCharactersValidatorTest {

    private ShortNameCharactersValidator validator;

    @Before
    public void setup() {

        this.validator = new ShortNameCharactersValidator();
    }

    @Test
    public void testJustTextIsValid() {

        assertThat(validator.isValid("ShortName", null), is(true));
    }

    @Test
    public void testNumbersIsValid() {

        assertThat(validator.isValid("1234567890", null), is(true));
    }

    @Test
    public void testPeriodIsValid() {

        assertThat(validator.isValid("Short.Name", null), is(true));
    }

    @Test
    public void testParenthesisIsValid() {

        assertThat(validator.isValid("(ShortName)", null), is(true));
    }

    @Test
    public void testCommaIsValid() {

        assertThat(validator.isValid("Short,Name", null), is(true));
    }

    @Test
    public void testUnderscoreIsValid() {

        assertThat(validator.isValid("Short_Name", null), is(true));
    }

    @Test
    public void testExclamationPointIsValid() {

        assertThat(validator.isValid("ShortName!", null), is(true));
    }

    @Test
    public void testDollarSignIsValid() {

        assertThat(validator.isValid("ShortName$", null), is(true));
    }

    @Test
    public void testDashIsValid() {

        assertThat(validator.isValid("Short-Name", null), is(true));
    }

    @Test
    public void testAsteriskIsValid() {

        assertThat(validator.isValid("ShortName*", null), is(true));
    }
    @Test

    public void testSingleQuoteIsValid() {

        assertThat(validator.isValid("'ShortName'", null), is(true));
    }

    @Test
    public void testSpaceIsNotValid() {

        assertThat(validator.isValid("Short Name", null), is(false));
    }

    @Test
    public void testPlusSignIsNotValid() {

        assertThat(validator.isValid("Short+Name", null), is(false));
    }

    @Test
    public void testQuestionMarkIsNotValid() {

        assertThat(validator.isValid("ShortName?", null), is(false));
    }
}
