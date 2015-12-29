package sgf.gateway.validation.persistence;

import org.springframework.util.StringUtils;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.CryptoService;
import sgf.gateway.service.security.RuntimeUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordCorrectValidator implements ConstraintValidator<PasswordCorrect, String> {

    private final RuntimeUserService runtimeUserService;
    private final CryptoService cryptoService;

    public PasswordCorrectValidator(RuntimeUserService runtimeUserService, CryptoService cryptoService) {

        this.runtimeUserService = runtimeUserService;
        this.cryptoService = cryptoService;
    }

    @Override
    public void initialize(PasswordCorrect PasswordCorrect) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        if (StringUtils.hasText(password)) {

            User user = runtimeUserService.getUser();

            valid = cryptoService.validate(password, user.getPassword());
        }

        return valid;
    }
}
