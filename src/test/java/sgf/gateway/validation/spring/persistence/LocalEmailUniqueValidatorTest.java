package sgf.gateway.validation.spring.persistence;

import org.junit.Test;
import org.springframework.validation.Errors;
import sgf.gateway.dao.security.UserRepository;

import static org.mockito.Mockito.*;

public class LocalEmailUniqueValidatorTest {

    @Test
    public void testUniqueEmail() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("test@test.com");

        UserRepository mockUserRepository = mock(UserRepository.class);
        when(mockUserRepository.isLocalEmailUnique("test@test.com")).thenReturn(true);

        LocalEmailUniqueValidator emailUniqueValidator = new LocalEmailUniqueValidator("testField", "test.error.code", "defaultMessage", mockUserRepository);

        emailUniqueValidator.validate("testField", mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testDuplicateEmail() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("test@test.com");

        UserRepository mockUserRepository = mock(UserRepository.class);
        when(mockUserRepository.isLocalEmailUnique("test@test.com")).thenReturn(false);

        LocalEmailUniqueValidator emailUniqueValidator = new LocalEmailUniqueValidator("testField", "test.error.code", "defaultMessage", mockUserRepository);

        emailUniqueValidator.validate("testField", mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }
}
