package sgf.gateway.web.controllers.security.openid;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.security.LoginCommand;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * OpenID consumer controller that performs the following functionality:
 * <p/>
 * 1) Retrieves from the HTTP request the openid identifier that was supplied by the user
 * <p/>
 * 2) Resolves the user openid identifier to an OpenID provider endpoint (via OpenID normalization and discovery)
 * <p/>
 * 3) Establishes a shared key association with * the OpenID provider
 * <p/>
 * 4) Redirects the user agent to the OpenID provider with an authentication request
 * <p/>
 * <p/>
 * For compatibility with the Spring Security OpenIDAuthenticationFilter, this controller delegates all functionality to an OpenIDConsumer (which internally
 * uses a shared instance of ConsumerManager).
 */
@Controller
public class OpenidConsumerController {

    private final OpenIdConfiguration openIdConfiguration;

    private final RuntimeUserService runtimeUserService;

    public OpenidConsumerController(OpenIdConfiguration openIdConfiguration, RuntimeUserService runtimeUserService) {

        this.openIdConfiguration = openIdConfiguration;
        this.runtimeUserService = runtimeUserService;
    }

    @ModelAttribute("user")
    public User getUser() throws Exception {

        return this.runtimeUserService.getUser();
    }

    @ModelAttribute("exampleOpenID")
    public String getExampleOpenid() {

        return this.openIdConfiguration.getBaseOpenIdURI() + "joe_smith";
    }

    @RequestMapping(value = {"/ac/guest/secure/sso.htm", "/ac/guest/secure/sso.html"}, method = RequestMethod.GET)
    public String showView() {

        return "/ac/guest/secure/openid/consumer/sso";
    }

    /*
     * I don't believe this method is actually ever called.  The request should be intercepted by the spring security filters.
     * (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @RequestMapping(value = {"/ac/guest/secure/sso.htm", "/ac/guest/secure/sso.html"}, method = RequestMethod.POST)
    public ModelAndView onSubmit(@ModelAttribute("command") LoginCommand command) throws Exception {

        String redirectUrl = command.getOpenidRedirectUrl();

        // redirect user agent to OpenID Provider
        return new ModelAndView(new RedirectView(redirectUrl));
    }

    /**
     * Overridden method attempts to retrieve the user's OpenID from a previously set cookie.
     */
    @ModelAttribute("command")
    public LoginCommand formBackingObject(HttpServletRequest request) {

        LoginCommand command = new LoginCommand();

        // try retrieving openid from session
        String openid = (String) request.getSession().getAttribute(OpenidParameters.SESSION_ATTRIBUTE_OPENID);
        if (StringUtils.hasText(openid)) {

            command.setOpenid_identifier(openid);
        }

        // try retrieving OpenID from cookie
        if (!StringUtils.hasText(command.getOpenid_identifier())) {
            Cookie cookie = OpenidUtils.getOpenidCookie(request);
            if (cookie != null) {
                try {

                    openid = URLDecoder.decode(cookie.getValue(), "UTF-8");

                    command.setOpenid_identifier(openid);

                } catch (UnsupportedEncodingException e) {

                }
            }
        }

        return command;
    }
}
