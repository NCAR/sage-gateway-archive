package sgf.gateway.validation.spring.data;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class StartBeforeOrEqualEndDateValidatorTest {

    Errors mockErrors;
    StartBeforeOrEqualEndDateValidator validator;
    String date1;
    String date2;
    String badDate;

    @Before
    public void setUp() throws Exception {

        mockErrors = mock(Errors.class);
        date1 = "2001-02-20";
        date2 = "2001-02-21";
        badDate = "baddate";
    }

    @Test
    public void testStartBeforeEnd() {

        when(mockErrors.getFieldValue("dateField1")).thenReturn(date1);
        when(mockErrors.getFieldValue("dateField2")).thenReturn(date2);

        validator = new StartBeforeOrEqualEndDateValidator("dateField1", "dateField2", "yyyy-MM-dd", "test.error.code", "defaultMessage");

        validator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testEndBeforeStart() {

        when(mockErrors.getFieldValue("dateField1")).thenReturn(date2);
        when(mockErrors.getFieldValue("dateField2")).thenReturn(date1);

        validator = new StartBeforeOrEqualEndDateValidator("dateField1", "dateField2", "yyyy-MM-dd", "test.error.code", "defaultMessage");

        validator.validate(null, mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testDatesSame() {

        when(mockErrors.getFieldValue("dateField1")).thenReturn(date2);
        when(mockErrors.getFieldValue("dateField2")).thenReturn(date2);

        validator = new StartBeforeOrEqualEndDateValidator("dateField1", "dateField2", "yyyy-MM-dd", "test.error.code", "defaultMessage");

        validator.validate(null, mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testBadStartDateFormat() {

        when(mockErrors.getFieldValue("dateField1")).thenReturn(badDate);
        when(mockErrors.getFieldValue("dateField2")).thenReturn(date2);

        validator = new StartBeforeOrEqualEndDateValidator("dateField1", "dateField2", "yyyy-MM-dd", "test.error.code", "defaultMessage");

        validator.validate(null, mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testBadEndDateFormat() {

        when(mockErrors.getFieldValue("dateField1")).thenReturn(badDate);
        when(mockErrors.getFieldValue("dateField2")).thenReturn(date2);

        validator = new StartBeforeOrEqualEndDateValidator("dateField1", "dateField2", "yyyy-MM-dd", "test.error.code", "defaultMessage");

        validator.validate(null, mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }
}
