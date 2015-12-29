package sgf.gateway.service.security.impl.acegi.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import sgf.gateway.service.security.impl.acegi.AcegiUserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AcegiUserDetailsAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    public AcegiUserDetailsAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {

        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if (authentication.getPrincipal() instanceof AcegiUserDetails) {

            super.onAuthenticationSuccess(request, response, authentication);

        } else {

            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
