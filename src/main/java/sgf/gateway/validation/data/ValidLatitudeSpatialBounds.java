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
@Constraint(validatedBy = ValidLatitudeSpatialBoundsValidator.class)
@Documented
public @interface ValidLatitudeSpatialBounds {

    String message() default "{sgf.gateway.validation.data.search.latitude.spatial.bounds}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String northernLatitudeField();

    String southernLatitudeField();
}
