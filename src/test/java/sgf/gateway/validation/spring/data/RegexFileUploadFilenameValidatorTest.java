package sgf.gateway.validation.spring.data;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.validation.data.FileNameCharacterChecker;
import sgf.gateway.validation.data.RegexFileUploadFilenameValidator;

import javax.validation.ConstraintValidatorContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegexFileUploadFilenameValidatorTest {

    private static final String GOOD_FILE_NAME = "GoodFileName";
    private static final String BAD_FILE_NAME = "BadFileName";

    private ConstraintValidatorContext constraintValidatorContext;
    private MultipartFile mockMultipartFile;
    private FileNameCharacterChecker mockFileNameCharacterChecker;
    private RegexFileUploadFilenameValidator validator;

    @Before
    public void before() {

        this.constraintValidatorContext = null;
        this.mockMultipartFile = mock(MultipartFile.class);
        this.mockFileNameCharacterChecker = mock(FileNameCharacterChecker.class);

        this.validator = new RegexFileUploadFilenameValidator(mockFileNameCharacterChecker);
    }

    @Test
    public void testNullFilePassesValidation() {

        boolean valid = this.validator.isValid(null, this.constraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testGoodFileName() {

        when(this.mockMultipartFile.getOriginalFilename()).thenReturn(GOOD_FILE_NAME);
        when(mockFileNameCharacterChecker.isFileNameValid(GOOD_FILE_NAME)).thenReturn(true);

        boolean valid = validator.isValid(mockMultipartFile, constraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testBadFileName() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(BAD_FILE_NAME);
        when(mockFileNameCharacterChecker.isFileNameValid(BAD_FILE_NAME)).thenReturn(false);

        boolean valid = validator.isValid(mockMultipartFile, constraintValidatorContext);

        assertThat(valid, equalTo(false));
    }
}
