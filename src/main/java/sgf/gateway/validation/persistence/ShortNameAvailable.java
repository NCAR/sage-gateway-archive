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
@Constraint(validatedBy = ShortNameAvailableValidator.class)
@Documented
public @interface ShortNameAvailable {

    String message() default "{sgf.gateway.validation.persistence.shortnameavailable}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
