package sgf.gateway.validation.persistence;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})  // "Indicates the kinds of program element to which an annotation type is applicable."
// Possible values: ANNOTATION_TYPE, CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE

@Retention(RUNTIME)
// "...indicates that annotations with this type of to be retained by the VM so they can be read reflexively at runtime."

@Constraint(validatedBy = AssertUniqueGroupNameValidator.class)
// "Specifies the validator to be used to validate (this annotation type)..."
@Documented
public @interface AssertUniqueGroupName {

    // "Default key for creating error messages in case the constraint is violated."  Please see the ValidationMessages.properties file.
    String message() default "{sgf.gateway.validation.persistence.assertgroupnameunique}";

    // "Allows the specification of validation groups, to which this constraint belongs.  This (attribute) must default to an empty array of type Class<?>."
    Class<?>[] groups() default {};

    // Not well understood by the author/presenter.
    Class<? extends Payload>[] payload() default {};

    String groupNameField();

    String identifierField();
}
