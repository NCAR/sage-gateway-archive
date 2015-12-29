package sgf.gateway.validation.spring.data;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.validation.data.PeriodFileUploadFilenameValidator;

import javax.validation.ConstraintValidatorContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PeriodFileUploadFilenameValidatorTest {

    // Filename may contain alphanumeric, underbar, dot, dash, or space
    private static final String GOOD_FILE_NAME_1 = "fileName";
    private static final String GOOD_FILE_NAME_2 = "File.Name";
    private static final String BAD_FILE_NAME = ".fileName";

    ConstraintValidatorContext mockConstraintValidatorContext;
    MultipartFile mockMultipartFile;

    PeriodFileUploadFilenameValidator validator;

    @Before
    public void before() {

        mockConstraintValidatorContext = mock(ConstraintValidatorContext.class);
        mockMultipartFile = mock(MultipartFile.class);

        validator = new PeriodFileUploadFilenameValidator();
    }

    @Test
    public void testGoodFileNames() {


        this.testFileNameIsGood(GOOD_FILE_NAME_1);
        this.testFileNameIsGood(GOOD_FILE_NAME_2);
    }

    public void testFileNameIsGood(String filename) {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(filename);

        boolean valid = validator.isValid(mockMultipartFile, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testBadFileName() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(BAD_FILE_NAME);

        boolean valid = validator.isValid(mockMultipartFile, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }
}
