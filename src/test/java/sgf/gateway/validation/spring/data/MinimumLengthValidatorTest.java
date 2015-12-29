package sgf.gateway.validation.spring.data;

import org.junit.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class MinimumLengthValidatorTest {

    @Test
    public void testTooShort() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("12345");

        MinimumLengthValidator minimumLengthValidator = new MinimumLengthValidator("testField", "test.error.code", "defaultMessage", 6);

        minimumLengthValidator.validate(null, mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testJustRight() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("123456");

        MinimumLengthValidator minimumLengthValidator = new MinimumLengthValidator("testField", "test.error.code", "defaultMessage", 6);

        minimumLengthValidator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testTooBig() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("1234567");

        MinimumLengthValidator minimumLengthValidator = new MinimumLengthValidator("testField", "test.error.code", "defaultMessage", 6);

        minimumLengthValidator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }
}
