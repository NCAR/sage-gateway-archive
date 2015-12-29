package sgf.gateway.validation.spring.type;

import org.junit.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class EmailTypeValidatorTest {

    @Test
    public void testValidEmail() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("test@test.com");

        EmailTypeValidator emailTypeValidator = new EmailTypeValidator("testField", "test.error.code", "defaultMessage");

        emailTypeValidator.validate("testField", mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testInvalidEmail() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("test@test@com");

        EmailTypeValidator emailTypeValidator = new EmailTypeValidator("testField", "test.error.code", "defaultMessage");

        emailTypeValidator.validate("testField", mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }
}
