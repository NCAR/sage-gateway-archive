package sgf.gateway.web.controllers.security;

/**
 * Command bean containing parameters for an authentication request, including OpenID and local authentication.
 */
public class LoginCommand {

    /**
     * Field name is the same as dictated by the OpenID specification
     */
    private String openid_identifier;

    /**
     * OpenID authentication request URL (built after OpenID normalization and discovery process)
     */
    private String openidRedirectUrl;

    /**
     * Field name is the same as processed by the Spring Security filter
     */
    private String username;

    /**
     * Field name is the same as processed by the Spring Security filter
     */
    private String password;

    /**
     * Login form redirect URL
     */
    private String redirectUrl;

    public String getOpenid_identifier() {
        return openid_identifier;
    }

    public void setOpenid_identifier(String openid_identifier) {
        // note: disregard leading/trailing white spaces
        this.openid_identifier = openid_identifier.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenidRedirectUrl() {
        return openidRedirectUrl;
    }

    public void setOpenidRedirectUrl(String openidRedirectUrl) {
        this.openidRedirectUrl = openidRedirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
