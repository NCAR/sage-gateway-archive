package sgf.gateway.validation.data;

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
@Constraint(validatedBy = ValidDecimalRangeValidator.class)
@Documented
public @interface ValidDecimalRange {

    String message() default "{sgf.gateway.validation.data.decimal.range}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String lower();

    String upper();

    RangeEndpointType lowerEndpointType() default RangeEndpointType.INCLUSIVE;

    RangeEndpointType upperEndpointType() default RangeEndpointType.INCLUSIVE;
}
