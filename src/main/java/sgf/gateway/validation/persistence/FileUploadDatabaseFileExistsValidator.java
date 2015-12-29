package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.web.controllers.api.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileUploadDatabaseFileExistsValidator implements ConstraintValidator<FileUploadDatabaseFileExists, FileUploadCommand> {

    LogicalFileRepository logicalFileRepository;


    public FileUploadDatabaseFileExistsValidator(final LogicalFileRepository logicalFileRepository) {
        this.logicalFileRepository = logicalFileRepository;
    }

    @Override
    public void initialize(FileUploadDatabaseFileExists annotation) {

    }

    @Override
    public boolean isValid(FileUploadCommand fileUploadCommand, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        String filename = this.getFileNameFromCommand(fileUploadCommand);

        String datasetId = fileUploadCommand.getDatasetIdentifier();

        if (StringUtils.hasText(filename)) {

            valid = this.isFileValid(datasetId, filename);

            if (!valid) {

                // Customize error message
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("The file " + filename + " (or similar) already exists on the server for this Dataset.  No updates or overwrites are allowed to files at this time.").addConstraintViolation();
            }

        }

        return valid;
    }

    protected String getFileNameFromCommand(FileUploadCommand command) {

        MultipartFile file = command.getFile();

        String filename = file.getOriginalFilename();

        return filename;
    }

    protected boolean isFileValid(String datasetPersistentId, String filename) {

        // Want case-insensitive comparison.  E.g. FileName.txt matches filename.txt.
        boolean fileExists = false;

        List<LogicalFile> filesForDataset;

        // Check if any version (case-wise) of this file already exists for this dataset (LogicalFile.name)
        filesForDataset = logicalFileRepository.findByDatasetShortNameAndLogicalFileName(datasetPersistentId, filename, false);

        if (filesForDataset.size() > 0) {
            fileExists = true;
        }

        return !fileExists;

    }

}
