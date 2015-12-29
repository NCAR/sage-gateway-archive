package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.web.controllers.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUploadFormDupFilenameInListValidator implements ConstraintValidator<FileUploadFormDupFilenameInList, Object> {

    @Override
    public void initialize(FileUploadFormDupFilenameInList arg0) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        FileUploadCommand fileUploadCommand = (FileUploadCommand) object;

        List<MultipartFile> files = fileUploadCommand.getFiles();

        // Make a Set for file names to check dups
        Set<String> fileset = new HashSet<String>();

        boolean matches = false;

        for (MultipartFile file : files) {

            // Want case-insensitive comparison.  E.g. FileName.txt matches filename.txt.
            String filename = file.getOriginalFilename();

            if (StringUtils.hasText(filename)) {

                String lowerFilename = file.getOriginalFilename().toLowerCase();

                // Can't add, already there
                if (!fileset.add(lowerFilename)) {
                    matches = true;
                }

                if (matches) {

                    valid = false;

                    // Customize error message
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate("File " + filename + " is duplicated in the upload file list.").addConstraintViolation();

                    // reset matches for next file
                    matches = false;
                }

            }
        }

        return valid;
    }
}
