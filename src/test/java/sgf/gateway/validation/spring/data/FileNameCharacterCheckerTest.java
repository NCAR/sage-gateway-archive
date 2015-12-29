package sgf.gateway.validation.spring.data;

import org.junit.Test;
import sgf.gateway.validation.data.FileNameCharacterChecker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FileNameCharacterCheckerTest {

    // Filename may contain alphanumeric, underbar, dot, dash, or space
    private static final String PLAIN_FILE_NAME = "fileName";
    private static final String FILE_NAME_WITH_SPACE = "File Name";
    private static final String FILE_NAME_GOOD_CHARS = "Filename1_a.b-c";
    private static final String FILE_NAME_WITH_BAD_CHARS = "File=Na(me)";
    private static final String FILE_NAME_WITH_CODE = "File%20Name";
    private static final String UNTRIMMED_FILE_NAME = " FileName ";

    private FileNameCharacterChecker checker = new FileNameCharacterChecker();

    @Test
    public void testValidFileNames() {

        this.testValidName(PLAIN_FILE_NAME);
        this.testValidName(FILE_NAME_WITH_SPACE);
        this.testValidName(FILE_NAME_GOOD_CHARS);
        this.testValidName(UNTRIMMED_FILE_NAME);
    }

    public void testValidName(String filename) {

        boolean valid = checker.isFileNameValid(filename);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testInValidFileNames() {

        this.testInValidFileName(null);
        this.testInValidFileName("");
        this.testInValidFileName(" ");
        this.testInValidFileName(FILE_NAME_WITH_BAD_CHARS);
        this.testInValidFileName(FILE_NAME_WITH_CODE);
    }

    private void testInValidFileName(String filename) {

        boolean valid = checker.isFileNameValid(filename);

        assertThat(valid, equalTo(false));
    }
}
