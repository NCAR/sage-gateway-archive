package sgf.gateway.validation.spring.data;

import org.junit.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class ConfirmationFieldValidatorTest {

    @Test
    public void testSame() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("same");
        when(mockErrors.getFieldValue("testConfirmField")).thenReturn("same");

        ConfirmationFieldValidator confirmationFieldValidator = new ConfirmationFieldValidator("testField", "testConfirmField", "test.error.code", "defaultMessage");

        confirmationFieldValidator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testSameNewStringInstances() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn(new String("same"));
        when(mockErrors.getFieldValue("testConfirmField")).thenReturn(new String("same"));

        ConfirmationFieldValidator confirmationFieldValidator = new ConfirmationFieldValidator("testField", "testConfirmField", "test.error.code", "defaultMessage");

        confirmationFieldValidator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testDifferent() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("same");
        when(mockErrors.getFieldValue("testConfirmField")).thenReturn("different");

        ConfirmationFieldValidator confirmationFieldValidator = new ConfirmationFieldValidator("testField", "testConfirmField", "test.error.code", "defaultMessage");

        confirmationFieldValidator.validate(null, mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }
}
