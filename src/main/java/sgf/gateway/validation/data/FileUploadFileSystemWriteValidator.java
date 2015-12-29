package sgf.gateway.validation.data;

import sgf.gateway.web.controllers.api.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;

public class FileUploadFileSystemWriteValidator implements ConstraintValidator<FileUploadFileSystemWrite, Object> {

    File baseDataDirectory;

    public FileUploadFileSystemWriteValidator(final File baseDataDirectory) {
        this.baseDataDirectory = baseDataDirectory;
    }

    @Override
    public void initialize(FileUploadFileSystemWrite arg0) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        FileUploadCommand fileUploadCommand = (FileUploadCommand) object;

        // look at TransferFileProcessor.transfer
        File uploadDir = new File(this.baseDataDirectory, fileUploadCommand.getDatasetIdentifier());

        if (!uploadDir.canWrite()) {

            valid = false;

            // Customize error message
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Cannot write to " + uploadDir).addConstraintViolation();

        }

        return valid;
    }
}
