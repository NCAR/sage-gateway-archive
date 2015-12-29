package sgf.gateway.validation.data;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.web.controllers.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class RegexFileUploadFormFilenamesValidator implements ConstraintValidator<RegexFileUploadFormFilenames, Object> {

    FileNameCharacterChecker fileNameCharacterChecker;

    public RegexFileUploadFormFilenamesValidator(FileNameCharacterChecker fileNameCharacterChecker) {

        this.fileNameCharacterChecker = fileNameCharacterChecker;
    }

    @Override
    public void initialize(RegexFileUploadFormFilenames arg0) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        FileUploadCommand fileUploadCommand = (FileUploadCommand) object;

        List<MultipartFile> files = fileUploadCommand.getFiles();

        for (MultipartFile file : files) {

            String filename = file.getOriginalFilename();

            if (StringUtils.hasText(filename)) {

                valid = fileNameCharacterChecker.isFileNameValid(filename);

                if (!valid) {

                    break;
                }
            }
        }

        return valid;
    }
}
