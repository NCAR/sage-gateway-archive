package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.web.controllers.api.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;

public class FileUploadFileExistsOnFileSystemValidator implements ConstraintValidator<FileUploadFileExistsOnFileSystem, FileUploadCommand> {

    File baseDataDirectory;
    FileSystemFileExistsChecker fileSystemFileExistsChecker;

    public FileUploadFileExistsOnFileSystemValidator(final File baseDataDirectory, FileSystemFileExistsChecker fileSystemFileExistsChecker) {
        this.baseDataDirectory = baseDataDirectory;
        this.fileSystemFileExistsChecker = fileSystemFileExistsChecker;
    }

    @Override
    public void initialize(FileUploadFileExistsOnFileSystem annotation) {

    }

    @Override
    public boolean isValid(FileUploadCommand fileUploadCommand, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        String filename = this.getFileNameFromCommand(fileUploadCommand);

        if (StringUtils.hasText(filename)) {

            valid = this.isFilenameMatch(fileUploadCommand);

            if (!valid) {

                // Customize error message
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("A file with name simlar to " + filename + " already exists for this dataset directory. File names which differ by case only are not allowed.").addConstraintViolation();
            }
        }

        return valid;
    }

    protected String getFileNameFromCommand(FileUploadCommand command) {

        MultipartFile file = command.getFile();

        String filename = file.getOriginalFilename();

        return filename;
    }

    protected boolean isFilenameMatch(FileUploadCommand command) {

        boolean valid = false;

        String filename = this.getFileNameFromCommand(command);

        File datasetDirectory = this.getFileDirectoryForDataset(command);

        if (isFilenameSimilar(datasetDirectory, filename)) {

            if (isFilenameExactMatch(datasetDirectory, filename)) {

                valid = true;
            }

        } else {

            valid = true;
        }

        return valid;
    }

    protected File getFileDirectoryForDataset(FileUploadCommand command) {

        File file = new File(this.baseDataDirectory, command.getDatasetIdentifier());

        return file;
    }

    protected boolean isFilenameExactMatch(File datasetDirectory, String filename) {
        //boolean matches = FileUploadUtils.isFileSystemFileExists(datasetDirectory, filename, true);
        boolean matches = this.fileSystemFileExistsChecker.isFileSystemFileExists(datasetDirectory, filename, true);
        return matches;
    }

    protected boolean isFilenameSimilar(File datasetDirectory, String filename) {
        //boolean matches = FileUploadUtils.isFileSystemFileExists(datasetDirectory, filename, false);
        boolean matches = this.fileSystemFileExistsChecker.isFileSystemFileExists(datasetDirectory, filename, false);
        return matches;
    }

}
