package sgf.gateway.validation.spring.data;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.validation.data.FileNameCharacterChecker;
import sgf.gateway.validation.data.RegexFileUploadFormFilenamesValidator;
import sgf.gateway.web.controllers.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegexFileUploadFormFilenamesValidatorTest {

    private static final String PLAIN_FILE_NAME = "fileName";
    private static final String FILE_NAME_WITH_SPACE = "File Name";
    private static final String FILE_NAME_GOOD_CHARS = "Filename1_a.b-c";
    private static final String FILE_NAME_WITH_BAD_CHARS = "File=Na(me)";
    private static final String FILE_NAME_WITH_CODE = "File%20Name";
    private static final String UNTRIMMED_FILE_NAME = " FileName ";

    List<MultipartFile> filesToTest;

    FileUploadCommand mockFileUploadCommand;
    ConstraintValidatorContext mockConstraintValidatorContext;
    MultipartFile mockMultipartFile;
    FileNameCharacterChecker mockFileNameCharacterChecker;

    RegexFileUploadFormFilenamesValidator validator;

    @Before
    public void before() {

        mockFileUploadCommand = mock(FileUploadCommand.class);
        mockConstraintValidatorContext = mock(ConstraintValidatorContext.class);
        mockMultipartFile = mock(MultipartFile.class);
        mockFileNameCharacterChecker = mock(FileNameCharacterChecker.class);

        validator = new RegexFileUploadFormFilenamesValidator(mockFileNameCharacterChecker);
        filesToTest = new ArrayList<MultipartFile>();

        // List needs to exist to avoid NPE, but the crux is returning the given fileName, which is really the test subject
        filesToTest.add(mockMultipartFile);

        when(mockFileUploadCommand.getFiles()).thenReturn(filesToTest);
    }

    @Test
    public void testPlainFileName() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(PLAIN_FILE_NAME);
        when(mockFileNameCharacterChecker.isFileNameValid(PLAIN_FILE_NAME)).thenReturn(true);

        boolean valid = validator.isValid(mockFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testFileNameWithSpace() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(FILE_NAME_WITH_SPACE);
        when(mockFileNameCharacterChecker.isFileNameValid(FILE_NAME_WITH_SPACE)).thenReturn(true);

        boolean valid = validator.isValid(mockFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testFileNameGoodChars() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(FILE_NAME_GOOD_CHARS);
        when(mockFileNameCharacterChecker.isFileNameValid(FILE_NAME_GOOD_CHARS)).thenReturn(true);

        boolean valid = validator.isValid(mockFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testFileNameBadChars() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(FILE_NAME_WITH_BAD_CHARS);
        when(mockFileNameCharacterChecker.isFileNameValid(FILE_NAME_WITH_BAD_CHARS)).thenReturn(false);

        boolean valid = validator.isValid(mockFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }

    @Test
    public void testFileNameWithCode() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(FILE_NAME_WITH_CODE);
        when(mockFileNameCharacterChecker.isFileNameValid(FILE_NAME_WITH_CODE)).thenReturn(false);

        boolean valid = validator.isValid(mockFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }

    @Test
    public void testUntrimmedFileName() {

        when(mockMultipartFile.getOriginalFilename()).thenReturn(UNTRIMMED_FILE_NAME);
        when(mockFileNameCharacterChecker.isFileNameValid(UNTRIMMED_FILE_NAME)).thenReturn(true);

        boolean valid = validator.isValid(mockFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }


}
