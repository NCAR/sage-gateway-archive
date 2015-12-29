package sgf.gateway.validation.spring.required;

import org.junit.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class RequiredFieldValidatorTest {

    @Test
    public void testFieldThere() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("here");

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("testField", "test.error.code", "defaultMessage");

        requiredFieldValidator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testFieldNotThere() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("");

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("testField", "test.error.code", "defaultMessage");

        requiredFieldValidator.validate(null, mockErrors);

        verify(mockErrors).rejectValue("testField", "test.error.code", null, "defaultMessage");
    }

    @Test
    public void testFieldOnlyWhitespace() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("           ");

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("testField", "test.error.code", "defaultMessage");

        requiredFieldValidator.validate(null, mockErrors);

        verify(mockErrors).rejectValue("testField", "test.error.code", null, "defaultMessage");
    }
}
