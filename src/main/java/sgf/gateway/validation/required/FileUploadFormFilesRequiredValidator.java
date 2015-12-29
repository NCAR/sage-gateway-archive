package sgf.gateway.validation.required;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.web.controllers.dataset.file.FileUploadCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileUploadFormFilesRequiredValidator implements ConstraintValidator<FileUploadFormFilesRequired, Object> {

    @Override
    public void initialize(FileUploadFormFilesRequired fileUploadFormFilesRequired) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        FileUploadCommand fileUploadCommand = (FileUploadCommand) object;

        List<MultipartFile> files = fileUploadCommand.getFiles();

        for (MultipartFile file : files) {

            if (StringUtils.hasText(file.getOriginalFilename())) {

                valid = true;
                break;
            }
        }

        return valid;
    }

}
