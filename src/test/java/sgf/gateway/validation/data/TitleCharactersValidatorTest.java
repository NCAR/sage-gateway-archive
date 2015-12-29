package sgf.gateway.validation.data;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TitleCharactersValidatorTest {

    private TitleCharactersValidator validator;

    @Before
    public void setup() {

        this.validator = new TitleCharactersValidator();
    }

    @Test
    public void testJustTextIsValid() {

        assertThat(validator.isValid("Title", null), is(true));
    }

    @Test
    public void testSpaceIsValid() {

        assertThat(validator.isValid("Title Title", null), is(true));
    }

    @Test
    public void testNumbersIsValid() {

        assertThat(validator.isValid("1234567890", null), is(true));
    }

    @Test
    public void testPeriodIsValid() {

        assertThat(validator.isValid("Title.", null), is(true));
    }

    @Test
    public void testParenthesisIsValid() {

        assertThat(validator.isValid("(ShortName)", null), is(true));
    }

    @Test
    public void testUnderscoreIsValid() {

        assertThat(validator.isValid("Short_Name", null), is(true));
    }

    @Test
    public void testDashIsValid() {

        assertThat(validator.isValid("Title-Title", null), is(true));
    }

    @Test
    public void testQuestionMarkIsValid() {

        assertThat(validator.isValid("Title?", null), is(true));
    }

    @Test
    public void testSingleQuoteIsNotValid() {

        assertThat(validator.isValid("'Title'", null), is(false));
    }

    @Test
    public void testPlusSignIsNotValid() {

        assertThat(validator.isValid("Title+Title", null), is(false));
    }

    @Test
    public void testCommaIsNotValid() {

        assertThat(validator.isValid("Title,title", null), is(false));
    }

    @Test
    public void testDollarSignIsNotValid() {

        assertThat(validator.isValid("Title$", null), is(false));
    }

    @Test
    public void testExclamationPointIsNoteValid() {

        assertThat(validator.isValid("Title!", null), is(false));
    }

    @Test
    public void testAsteriskIsNotValid() {

        assertThat(validator.isValid("Title*", null), is(false));
    }
}
