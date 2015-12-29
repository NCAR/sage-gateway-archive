package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.web.controllers.api.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileUploadDatabaseFileExistsByCaseValidator implements ConstraintValidator<FileUploadDatabaseFileExistsByCase, Object> {

    LogicalFileRepository logicalFileRepository;

    public FileUploadDatabaseFileExistsByCaseValidator(final LogicalFileRepository logicalFileRepository) {
        this.logicalFileRepository = logicalFileRepository;
    }

    @Override
    public void initialize(FileUploadDatabaseFileExistsByCase annotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        FileUploadCommand fileUploadCommand = (FileUploadCommand) object;

        String datasetId = fileUploadCommand.getDatasetIdentifier();

        MultipartFile file = fileUploadCommand.getFile();

        String filename = file.getOriginalFilename();

        if (StringUtils.hasText(filename)) {

            // If the match differs only by case it's not valid.  Otherwise (exact match or no match) it is.
            if (isFilenameExactMatch(datasetId, filename)) {
                valid = true;
            }
            // Similar but not exact
            else valid = !isFilenameSimilar(datasetId, filename);

            if (!valid) {
                // Customize error message
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("A file with name similar to " + filename + " already exists on the server for this Dataset. File names that differ only by case are not allowed.").addConstraintViolation();
            }
        }

        return valid;
    }

    // Not lowercase filename - check match including case
    protected boolean isFilenameExactMatch(String datasetId, String filename) {

        boolean matches = false;

        List<LogicalFile> filesForDataset;

        filesForDataset = logicalFileRepository.findByDatasetShortNameAndLogicalFileName(datasetId, filename, true);

        if (filesForDataset.size() > 0) {

            matches = true;
        }
        return matches;
    }

    // May differ only by case.  Special Characters?
    protected boolean isFilenameSimilar(String datasetId, String filename) {

        boolean matches = false;

        List<LogicalFile> filesForDataset;

        // Check if any version (case-wise) of this file already exists for this dataset (LogicalFile.name)
        filesForDataset = logicalFileRepository.findByDatasetShortNameAndLogicalFileName(datasetId, filename, false);

        if (filesForDataset.size() > 0) {

            matches = true;
        }
        return matches;
    }
}
