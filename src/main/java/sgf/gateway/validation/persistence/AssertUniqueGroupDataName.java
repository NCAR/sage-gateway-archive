package sgf.gateway.validation.persistence;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AssertUniqueGroupDataNameValidator.class)
public @interface AssertUniqueGroupDataName {

    String message() default "{sgf.gateway.validation.persistence.assertgroupdatatypeunique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String nameField();

    String identifierField();
}
