package sgf.gateway.validation.data;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegexFileUploadFilenameValidator implements ConstraintValidator<RegexFileUploadFilename, MultipartFile> {

    private FileNameCharacterChecker fileNameCharacterChecker;

    public RegexFileUploadFilenameValidator(FileNameCharacterChecker fileNameCharacterChecker) {

        this.fileNameCharacterChecker = fileNameCharacterChecker;
    }

    @Override
    public void initialize(RegexFileUploadFilename arg0) {

    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        if (file != null) {

            String filename = file.getOriginalFilename();

            valid = this.fileNameCharacterChecker.isFileNameValid(filename);
        }

        return valid;
    }
}
