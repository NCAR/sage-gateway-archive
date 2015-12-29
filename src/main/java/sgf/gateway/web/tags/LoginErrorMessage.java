package sgf.gateway.web.tags;

import org.springframework.security.web.WebAttributes;
import sgf.gateway.security.authentication.BadCredentialsWithOpenIDGuessesException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

public class LoginErrorMessage extends TagSupport {

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() {

        HttpSession httpSession = pageContext.getSession();

        synchronized (httpSession) {

            Throwable throwable = (Throwable) httpSession.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

            if (throwable != null) {

                String errorMessage = throwable.getMessage();

                pageContext.getRequest().setAttribute("errorMessage", errorMessage);

                if (throwable instanceof BadCredentialsWithOpenIDGuessesException) {

                    BadCredentialsWithOpenIDGuessesException exception = (BadCredentialsWithOpenIDGuessesException) throwable;

                    pageContext.getRequest().setAttribute("openidGuesses", exception.getOpenidGuesses());
                }
            }

            httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
        }

        return SKIP_BODY;
    }
}
