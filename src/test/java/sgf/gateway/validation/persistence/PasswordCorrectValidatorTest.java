package sgf.gateway.validation.persistence;

import org.junit.Test;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.CryptoService;
import sgf.gateway.service.security.RuntimeUserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PasswordCorrectValidatorTest {

    @Test
    public void passwordCorrectTest() {

        PasswordCorrectValidator validator = this.createValidator(true);

        assertThat(validator.isValid("doesNotMatter", null), is(true));
    }

    public PasswordCorrectValidator createValidator(boolean isValid) {

        User mockUser = mock(User.class);

        RuntimeUserService mockRuntimeUserService = mock(RuntimeUserService.class);
        when(mockRuntimeUserService.getUser()).thenReturn(mockUser);

        CryptoService mockCryptoService = mock(CryptoService.class);
        when(mockCryptoService.validate(anyString(), anyString())).thenReturn(isValid);

        return new PasswordCorrectValidator(mockRuntimeUserService, mockCryptoService);
    }

    @Test
    public void passwordNotCorrectTest() {

        PasswordCorrectValidator validator = this.createValidator(false);

        assertThat(validator.isValid("doesNotMatter", null), is(false));
    }
}
