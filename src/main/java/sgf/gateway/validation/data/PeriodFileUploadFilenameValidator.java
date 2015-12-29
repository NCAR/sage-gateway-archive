package sgf.gateway.validation.data;


import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeriodFileUploadFilenameValidator implements ConstraintValidator<PeriodFileUploadFilename, MultipartFile> {

    @Override
    public void initialize(PeriodFileUploadFilename arg0) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        if (multipartFile != null) {

            String filename = multipartFile.getOriginalFilename();

            if (StringUtils.hasText(filename)) {

                if (filename.startsWith(".")) {

                    valid = false;
                }
            }
        }

        return valid;
    }
}
