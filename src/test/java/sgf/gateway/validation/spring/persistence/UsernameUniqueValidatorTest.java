package sgf.gateway.validation.spring.persistence;

import org.junit.Test;
import org.springframework.validation.Errors;
import sgf.gateway.dao.security.UserRepository;

import static org.mockito.Mockito.*;

public class UsernameUniqueValidatorTest {

    @Test
    public void testUniqueUsername() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("username");

        UserRepository mockUserRepository = mock(UserRepository.class);
        when(mockUserRepository.isUsernameUniqueIgnoreCase("username")).thenReturn(true);


        UsernameUniqueValidator usernameUniqueValidator = new UsernameUniqueValidator("testField", "test.error.code", "defaultMessage", mockUserRepository);

        usernameUniqueValidator.validate("testField", mockErrors);

        verify(mockErrors, never()).reject("test.error.code", "defaultMessage");
    }

    @Test
    public void testDuplicateUsername() {

        Errors mockErrors = mock(Errors.class);
        when(mockErrors.getFieldValue("testField")).thenReturn("username");

        UserRepository mockUserRepository = mock(UserRepository.class);
        when(mockUserRepository.isUsernameUniqueIgnoreCase("username")).thenReturn(false);


        UsernameUniqueValidator usernameUniqueValidator = new UsernameUniqueValidator("testField", "test.error.code", "defaultMessage", mockUserRepository);

        usernameUniqueValidator.validate("testField", mockErrors);

        verify(mockErrors).reject("test.error.code", "defaultMessage");
    }
}
