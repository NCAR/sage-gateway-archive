package sgf.gateway.validation.persistence;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.LogicalFileImpl;
import sgf.gateway.web.controllers.api.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FileUploadDatabaseFileExistsValidatorTest {

    private static final String DATASET_SHORT_NAME = "dataset_name";

    private Dataset mockDataset;

    private LogicalFileRepository mockLogicalFileRepository;

    private FileUploadCommand stubFileUploadCommand;

    ConstraintValidatorContext mockConstraintValidatorContext = mock(ConstraintValidatorContext.class);

    private FileUploadDatabaseFileExistsByCaseValidator validator;
    FileUploadDatabaseFileExistsByCase mockFileUploadDatabaseFileExists = mock(FileUploadDatabaseFileExistsByCase.class);

    MultipartFile mockMultipartFile = mock(MultipartFile.class);

    /* A bit odd in that this test holds the name of the test file constant and then simulates via Lists what would be
     * returned from the database if the DB contained a filename which was:
     * 1. an exact match 2. No match at all 3. A match that differed only by case
     */
    List<LogicalFile> exactMatchFilesForDataset = new ArrayList<LogicalFile>();
    List<LogicalFile> noMatchFilesForDataset = new ArrayList<LogicalFile>();
    List<LogicalFile> offOnlyByCaseFilesForDataset = new ArrayList<LogicalFile>();


    @Before
    public void before() {

        mockLogicalFileRepository = mock(LogicalFileRepository.class);

        mockDataset = mock(Dataset.class);
        when(mockDataset.getShortName()).thenReturn(DATASET_SHORT_NAME);

        stubFileUploadCommand = new FileUploadCommand(mockDataset);
        stubFileUploadCommand.setFile(mockMultipartFile);

        validator = new FileUploadDatabaseFileExistsByCaseValidator(mockLogicalFileRepository);
        validator.initialize(mockFileUploadDatabaseFileExists);

        LogicalFile logicalFileMatch = mock(LogicalFileImpl.class);
        when(logicalFileMatch.getName()).thenReturn("myFile.txt");

        LogicalFile logicalFileOffByCase = mock(LogicalFileImpl.class);
        when(logicalFileOffByCase.getName()).thenReturn("mYfILe.txt");

        exactMatchFilesForDataset.add(logicalFileMatch);
        offOnlyByCaseFilesForDataset.add(logicalFileOffByCase);
        //Don't add any files to noMatchFilesForDataset since none match!

        ConstraintViolationBuilder mockConstraintViolationBuilder = mock(ConstraintViolationBuilder.class);

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(mockConstraintViolationBuilder);

    }


    @Test
    public void testFileExactMatchFound() {

        String testFilename = "myFile.txt";

        when(mockMultipartFile.getOriginalFilename()).thenReturn(testFilename);

        stubFileUploadCommand.setFile(mockMultipartFile);

        when(mockLogicalFileRepository.findByDatasetShortNameAndLogicalFileName(DATASET_SHORT_NAME, testFilename, true)).thenReturn(exactMatchFilesForDataset);
        //when(mockLogicalFileRepository.findByDatasetShortNameAndLogicalFileName(DATASET_SHORT_NAME, testFilename, false )).thenReturn(exactMatchFilesForDataset);

        boolean valid = validator.isValid(stubFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testFileTotallyDifferent() {

        String testFilename = "myFile.txt";

        when(mockMultipartFile.getOriginalFilename()).thenReturn(testFilename);

        stubFileUploadCommand.setFile(mockMultipartFile);

        // Pretend this is a brand new file
        when(mockLogicalFileRepository.findByDatasetShortNameAndLogicalFileName(DATASET_SHORT_NAME, testFilename, true)).thenReturn(noMatchFilesForDataset);
        when(mockLogicalFileRepository.findByDatasetShortNameAndLogicalFileName(DATASET_SHORT_NAME, testFilename, false)).thenReturn(noMatchFilesForDataset);


        boolean valid = validator.isValid(stubFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testFileCaseIsDifferent() {

        String testFilename = "myFile.txt";

        when(mockMultipartFile.getOriginalFilename()).thenReturn(testFilename);

        stubFileUploadCommand.setFile(mockMultipartFile);

        when(mockLogicalFileRepository.findByDatasetShortNameAndLogicalFileName(DATASET_SHORT_NAME, testFilename, true)).thenReturn(noMatchFilesForDataset);
        when(mockLogicalFileRepository.findByDatasetShortNameAndLogicalFileName(DATASET_SHORT_NAME, testFilename, false)).thenReturn(offOnlyByCaseFilesForDataset);

        boolean valid = validator.isValid(stubFileUploadCommand, mockConstraintValidatorContext);

        assertThat(valid, equalTo(false));
    }


}
