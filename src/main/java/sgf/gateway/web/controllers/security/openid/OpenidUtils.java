package sgf.gateway.web.controllers.security.openid;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class OpenidUtils {

    /**
     * Method to extract the OpenID cookie from an HTTP request
     *
     * @param request
     * @return
     */
    public static Cookie getOpenidCookie(HttpServletRequest request) {

        Cookie openidCookie = null;

        if (request.getCookies() != null) {

            for (Cookie cookie : request.getCookies()) {

                if (cookie.getName().equals(OpenidParameters.OPENID_COOKIE_NAME)) {

                    openidCookie = cookie;
                    break;
                }
            }
        }
        return openidCookie;
    }
}
