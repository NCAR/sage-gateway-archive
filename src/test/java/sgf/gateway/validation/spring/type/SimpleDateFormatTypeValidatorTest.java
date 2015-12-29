package sgf.gateway.validation.spring.type;

import org.junit.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class SimpleDateFormatTypeValidatorTest {

    @Test
    public void testValidDate() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("20110831");

        SimpleDateFormatTypeValidator simpleDateFormatTypeValidator = new SimpleDateFormatTypeValidator("yyyyMMdd", "testField", "test.error.code", "defaultMessage");

        simpleDateFormatTypeValidator.validate("testField", mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testInvalidDateWithLetters() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("AD20110831");

        SimpleDateFormatTypeValidator simpleDateFormatTypeValidator = new SimpleDateFormatTypeValidator("yyyyMMdd", "testField", "test.error.code", "defaultMessage");

        simpleDateFormatTypeValidator.validate("testField", mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testInvalidDateWithLettersAtEnd() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("20110831TEST");

        SimpleDateFormatTypeValidator simpleDateFormatTypeValidator = new SimpleDateFormatTypeValidator("yyyyMMdd", "testField", "test.error.code", "defaultMessage");

        simpleDateFormatTypeValidator.validate("testField", mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testInvalidDate() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("201108311236");

        SimpleDateFormatTypeValidator simpleDateFormatTypeValidator = new SimpleDateFormatTypeValidator("yyyyMMdd", "testField", "test.error.code", "defaultMessage");

        simpleDateFormatTypeValidator.validate("testField", mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }
}
