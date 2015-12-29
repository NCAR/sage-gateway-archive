package sgf.gateway.web.controllers.security.openid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.impl.acegi.AcegiUserDetails;
import sgf.gateway.service.security.impl.acegi.OpenidConsumerAttributeExchangeFacade;

import javax.validation.Valid;
import java.util.List;

/**
 * This controller is used when a new User is logging into the Gateway via a remote OpenID.
 *
 * @author nhook
 */
@Controller
public class OpenidRegistrationController {

    private final AccountService accountService;

    public OpenidRegistrationController(final AccountService accountService) {

        this.accountService = accountService;
    }

    @ModelAttribute("command")
    public OpenidRegistrationCommand setupCommand() {

        OpenIDAuthenticationToken openidAuthenticationToken = this.getOpenIDAuthenticationToken();

        OpenidConsumerAttributeExchangeFacade facade = this.getOpenidConsumerAttributeExchangeFacade(openidAuthenticationToken);

        OpenidRegistrationCommand openidRegistrationCommand = new OpenidRegistrationCommand();
        openidRegistrationCommand.setOpenid(openidAuthenticationToken.getIdentityUrl());
        openidRegistrationCommand.setFirstName(facade.getFirstName());
        openidRegistrationCommand.setLastName(facade.getLastName());
        openidRegistrationCommand.setEmail(facade.getEmail());

        return openidRegistrationCommand;
    }

    public OpenIDAuthenticationToken getOpenIDAuthenticationToken() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        OpenIDAuthenticationToken openidAuthenticationToken;

        if (authentication instanceof OpenIDAuthenticationToken) {

            openidAuthenticationToken = (OpenIDAuthenticationToken) securityContext.getAuthentication();

        } else {

            throw new RuntimeException("Accessing Openid Registration Page without a OpenIDAuthenticationToken");
        }

        return openidAuthenticationToken;
    }

    public OpenidConsumerAttributeExchangeFacade getOpenidConsumerAttributeExchangeFacade(OpenIDAuthenticationToken openidAuthenticationToken) {

        List<OpenIDAttribute> attributes = openidAuthenticationToken.getAttributes();

        OpenidConsumerAttributeExchangeFacade facade = new OpenidConsumerAttributeExchangeFacade(attributes);

        return facade;
    }

    @RequestMapping(value = {"/openid/registration"}, method = RequestMethod.GET)
    protected String getForm() {

        return "/ac/guest/secure/registration/openidRegistration";
    }

    @RequestMapping(value = {"/openid/registration"}, method = RequestMethod.POST)
    protected String onSubmit(@ModelAttribute("command") @Valid OpenidRegistrationCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String view;

        if (bindingResult.hasErrors()) {

            view = "/ac/guest/secure/registration/openidRegistration";

        } else {

            User user = this.accountService.registerRemoteUser(command);

            this.setNewUserDetailsOnSecurityContext(user);

            view = "redirect:/";
        }

        return view;
    }

    public void setNewUserDetailsOnSecurityContext(User user) {

        AcegiUserDetails acegiUserDetails = new AcegiUserDetails(user);

        OpenIDAuthenticationToken openidAuthenticationToken = this.getOpenIDAuthenticationToken();

        // TODO Check to see if this call is required or can be removed.
        openidAuthenticationToken.setDetails(acegiUserDetails);

        OpenIDAuthenticationToken successOpenidAuthenticationToken = new OpenIDAuthenticationToken(acegiUserDetails, acegiUserDetails.getAuthorities(), openidAuthenticationToken.getIdentityUrl(), openidAuthenticationToken.getAttributes());

        SecurityContext securityContext = SecurityContextHolder.getContext();

        securityContext.setAuthentication(successOpenidAuthenticationToken);
    }
}
