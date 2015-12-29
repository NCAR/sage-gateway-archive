package sgf.gateway.validation.persistence;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})

@Retention(RUNTIME)

@Constraint(validatedBy = PasswordCorrectValidator.class)
public @interface PasswordCorrect {

    String message() default "{sgf.gateway.validation.persistence.PasswordCorrect}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
