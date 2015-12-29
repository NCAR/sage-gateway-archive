package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.web.controllers.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.util.List;

public class FileUploadFormFileSystemValidator implements ConstraintValidator<FileUploadFormFileSystem, Object> {

    File baseDataDirectory;
    FileSystemFileExistsChecker fileSystemFileExistsChecker;

    public FileUploadFormFileSystemValidator(final File baseDataDirectory, FileSystemFileExistsChecker fileSystemFileExistsChecker) {
        this.baseDataDirectory = baseDataDirectory;
        this.fileSystemFileExistsChecker = fileSystemFileExistsChecker;
    }

    @Override
    public void initialize(FileUploadFormFileSystem arg0) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        FileUploadCommand fileUploadCommand = (FileUploadCommand) object;

        List<MultipartFile> files = fileUploadCommand.getFiles();

        // look at TransferFileProcessor.transfer
        File datasetDirectory = new File(this.baseDataDirectory, fileUploadCommand.getDatasetIdentifier());

        boolean matches;

        for (MultipartFile file : files) {

            // Want case-insensitive comparison.  E.g. FileName.txt matches filename.txt.
            String filename = file.getOriginalFilename();

            if (StringUtils.hasText(filename)) {

                matches = fileSystemFileExistsChecker.isFileSystemFileExists(datasetDirectory, filename, false);

                if (matches) {

                    valid = false;

                    // Customize error message
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate("File " + filename + " (or similar) already exists for this dataset directory.").addConstraintViolation();

                    // reset matches for next file
                    matches = false;
                }

            }
        }

        return valid;
    }
}
