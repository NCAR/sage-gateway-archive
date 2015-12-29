package sgf.gateway.validation.persistence;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FileUploadDatabaseFileExistsValidator.class)
@Documented
public @interface FileUploadDatabaseFileExists {

    String message() default "{sgf.gateway.validation.persistence.fileuploadformdatabasefileexists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
