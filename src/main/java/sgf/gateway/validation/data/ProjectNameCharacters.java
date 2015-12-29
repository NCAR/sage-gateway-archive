package sgf.gateway.validation.data;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ProjectNameCharactersValidator.class)
@Documented
public @interface ProjectNameCharacters {

    String message() default "{sgf.gateway.validation.data.projectnamecharacters}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String nameField();  //title
}
