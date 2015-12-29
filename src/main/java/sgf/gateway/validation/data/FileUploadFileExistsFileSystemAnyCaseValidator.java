package sgf.gateway.validation.data;

import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.web.controllers.api.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;

public class FileUploadFileExistsFileSystemAnyCaseValidator implements ConstraintValidator<FileUploadFileExistsFileSystemAnyCase, FileUploadCommand> {

    File baseDataDirectory;
    FileSystemFileExistsChecker fileSystemFileExistsChecker;

    public FileUploadFileExistsFileSystemAnyCaseValidator(final File baseDataDirectory, FileSystemFileExistsChecker fileSystemFileExistsChecker) {
        this.baseDataDirectory = baseDataDirectory;
        this.fileSystemFileExistsChecker = fileSystemFileExistsChecker;
    }

    @Override
    public void initialize(FileUploadFileExistsFileSystemAnyCase arg0) {

    }

    @Override
    public boolean isValid(FileUploadCommand fileUploadCommand, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        MultipartFile file = fileUploadCommand.getFile();

        if (file != null) {

            String filename = file.getOriginalFilename();

            File datasetDirectory = getFileDirectoryForDataset(fileUploadCommand);

            valid = this.isFileNameUnique(datasetDirectory, filename);

            if (!valid) {

                // Customize error message
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("File " + filename + " (or similar) already exists for this dataset directory.").addConstraintViolation();
            }
        }
        return valid;
    }

    protected File getFileDirectoryForDataset(FileUploadCommand command) {

        File file = new File(this.baseDataDirectory, command.getDatasetUUID().toString());

        return file;
    }

    protected boolean isFileNameUnique(File datasetDirectory, String filename) {

        // Case-insensitive comparison.  E.g. FileName.txt does not match filename.txt.
        boolean fileExists = !fileSystemFileExistsChecker.isFileSystemFileExists(datasetDirectory, filename, false);

        return fileExists;
    }
}
