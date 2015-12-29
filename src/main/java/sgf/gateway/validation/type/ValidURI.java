package sgf.gateway.validation.type;

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
@Constraint(validatedBy = ValidURIValidator.class)
@Documented
public @interface ValidURI {

    String message() default "{sgf.gateway.validation.type.uriformat}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
