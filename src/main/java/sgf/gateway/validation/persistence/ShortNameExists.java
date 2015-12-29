package sgf.gateway.validation.persistence;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ShortNameExistsValidator.class)
@Documented
public @interface ShortNameExists {

    String message() default "{sgf.gateway.validation.persistence.shortnameexists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
