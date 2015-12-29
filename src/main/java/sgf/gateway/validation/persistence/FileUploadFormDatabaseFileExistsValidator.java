package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.web.controllers.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileUploadFormDatabaseFileExistsValidator implements ConstraintValidator<FileUploadFormDatabaseFileExists, Object> {

    LogicalFileRepository logicalFileRepository;


    public FileUploadFormDatabaseFileExistsValidator(final LogicalFileRepository logicalFileRepository) {
        this.logicalFileRepository = logicalFileRepository;
    }

    @Override
    public void initialize(FileUploadFormDatabaseFileExists annotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        FileUploadCommand fileUploadCommand = (FileUploadCommand) object;

        List<MultipartFile> files = fileUploadCommand.getFiles();

        String datasetPersistentId = fileUploadCommand.getDatasetIdentifier();  //persistent_identifier


        for (MultipartFile file : files) {

            String filename = file.getOriginalFilename();

            if (StringUtils.hasText(filename)) {

                // Want case-insensitive comparison.  E.g. FileName.txt matches filename.txt.
                String lowerFilename = file.getOriginalFilename().toLowerCase();

                List<LogicalFile> filesForDataset;

                // Check if any version (case-wise) of this file already exists for this dataset (LogicalFile.name)
                filesForDataset = logicalFileRepository.findByDatasetShortNameAndLogicalFileName(datasetPersistentId, lowerFilename, false);

                if (filesForDataset.size() > 0) {

                    valid = false;

                    // Customize error message
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate("The file " + filename + " (or similar) already exists on the server for this Dataset.  No updates or overwrites are allowed to files at this time.").addConstraintViolation();

                }

            }
        }

        return valid;
    }
}
