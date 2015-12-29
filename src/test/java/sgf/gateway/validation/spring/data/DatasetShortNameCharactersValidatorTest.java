package sgf.gateway.validation.spring.data;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.validation.data.DatasetShortNameCharactersValidator;
import sgf.gateway.web.controllers.cadis.publish.command.CadisDatasetCommand;

import javax.validation.ConstraintValidatorContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatasetShortNameCharactersValidatorTest {

    private static final String PLAIN_NAME = "DatasetName";
    private static final String NAME_WITH_SPACE = "Dataset Name";
    private static final String NAME_WITH_GOOD_CHARS = "Data(set)1_a.b,c!d$e'f*g-";
    private static final String NAME_WITH_BAD_CHARS = "Dataset=Name";
    private static final String NAME_WITH_BAD_CHARS2 = "Dat^aset+Name";
    private static final String UNTRIMMED_NAME = "DatasetName  ";

    CadisDatasetCommand mockCadisDatasetCommand;
    ConstraintValidatorContext mockConstraintValidatorContext;

    DatasetShortNameCharactersValidator validator;

    @Before
    public void before() {

        mockCadisDatasetCommand = mock(CadisDatasetCommand.class);
        mockConstraintValidatorContext = mock(ConstraintValidatorContext.class);
        validator = new DatasetShortNameCharactersValidator();
    }

    @Test
    public void testPlainName() {

        when(mockCadisDatasetCommand.getShortName()).thenReturn(PLAIN_NAME);

        boolean valid = validator.isValid(mockCadisDatasetCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testNameWithSpace() {

        when(mockCadisDatasetCommand.getShortName()).thenReturn(NAME_WITH_SPACE);

        boolean valid = validator.isValid(mockCadisDatasetCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }

    @Test
    public void testGoodChars() {

        when(mockCadisDatasetCommand.getShortName()).thenReturn(NAME_WITH_GOOD_CHARS);

        boolean valid = validator.isValid(mockCadisDatasetCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }


    @Test
    public void testBadName() {

        when(mockCadisDatasetCommand.getShortName()).thenReturn(NAME_WITH_BAD_CHARS);

        boolean valid = validator.isValid(mockCadisDatasetCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }

    @Test
    public void testBadName2() {

        when(mockCadisDatasetCommand.getShortName()).thenReturn(NAME_WITH_BAD_CHARS2);

        boolean valid = validator.isValid(mockCadisDatasetCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }

    @Test
    public void testUntrimmedName() {

        when(mockCadisDatasetCommand.getShortName()).thenReturn(UNTRIMMED_NAME);

        boolean valid = validator.isValid(mockCadisDatasetCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }
}
