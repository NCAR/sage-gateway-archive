package sgf.gateway.validation.spring.data;

import org.junit.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class MaximumLengthValidatorTest {

    @Test
    public void testTooShort() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("12345");

        MaximumLengthValidator maximumLengthValidator = new MaximumLengthValidator("testField", "test.error.code", "defaultMessage", 6);

        maximumLengthValidator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");

    }

    @Test
    public void testJustRight() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("123456");

        MaximumLengthValidator maximumLengthValidator = new MaximumLengthValidator("testField", "test.error.code", "defaultMessage", 6);

        maximumLengthValidator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testTooBig() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("1234567");

        MaximumLengthValidator maximumLengthValidator = new MaximumLengthValidator("testField", "test.error.code", "defaultMessage", 6);

        maximumLengthValidator.validate(null, mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }
}
