package sgf.gateway.security.authentication;

import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

public class BadCredentialsWithOpenIDGuessesException extends BadCredentialsException {

    private static final long serialVersionUID = 1L;

    private List<String> openidGuesses;

    public BadCredentialsWithOpenIDGuessesException(String message) {

        super(message);
    }

    public void setOpenidGuesses(List<String> openidGuesses) {

        this.openidGuesses = openidGuesses;
    }

    public List<String> getOpenidGuesses() {

        return this.openidGuesses;
    }
}