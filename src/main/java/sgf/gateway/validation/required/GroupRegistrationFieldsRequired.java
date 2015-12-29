package sgf.gateway.validation.required;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = GroupRegistrationFieldsRequiredValidator.class)
@Documented
public @interface GroupRegistrationFieldsRequired {

    String message() default "{sgf.gateway.validation.required.groupRegistrationFieldsRequired}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
