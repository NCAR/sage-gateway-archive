package sgf.gateway.web.controllers.security.openid;

import org.openid4java.message.*;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.server.ServerManager;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.impl.acegi.FreeBSDCryptPasswordEncoder;
import sgf.gateway.service.security.impl.acegi.OpenidProviderAttributeExchangeFacade;
import sgf.gateway.service.security.impl.acegi.OpenidProviderSRegFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class OpenidProviderLoginController implements MessageSourceAware {

    private final ServerManager manager;
    private final UserRepository userRepository;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private String view = "/idp/login";

    public OpenidProviderLoginController(ServerManager serverManager, UserRepository userRepository) {

        this.manager = serverManager;
        this.userRepository = userRepository;
    }

    @ModelAttribute("command")
    public OpenIDLoginCommand formBackingObject(HttpServletRequest httpsRequest) throws Exception {

        OpenIDLoginCommand command = new OpenIDLoginCommand();

        // Move this code to the get/post methods?
        // Maybe put this method in a method called checkCookiesAreEnabled?
        HttpSession httpSession = httpsRequest.getSession();

        ParameterList parameterList = (ParameterList) httpSession.getAttribute(OpenidParameters.SESSION_ATTRIBUTE_PARAMETERLIST);

        if (isParameterListNull(parameterList)) {

            CookiesMustBeEnabledException cookiesMustBeEnabledException = new CookiesMustBeEnabledException(messages.getMessage("AuthenticationException.cookiesMustBeEnabled", "Cookies must be enabled"));

            httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, cookiesMustBeEnabledException);

            ModelAndView modelAndView = new ModelAndView(this.view);
            modelAndView.addObject("command", command);

            throw new ModelAndViewDefiningException(modelAndView);
        }

        AuthRequest authReq = AuthRequest.createAuthRequest(parameterList, this.manager.getRealmVerifier());

        command.setUsername(authReq.getClaimed());

        return command;
    }

    private boolean isParameterListNull(ParameterList parameterList) {

        boolean isNull = false;

        if (parameterList == null) {

            isNull = true;
        }

        return isNull;
    }

    @RequestMapping(value = {"/openid/login.htm", "/openid/login.html"}, method = RequestMethod.GET)
    public ModelAndView getNewForm() {

        return new ModelAndView(this.view);
    }

    @RequestMapping(value = {"/openid/login.htm", "/openid/login.html"}, method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletRequest httpServletRequest, HttpSession httpSession, @ModelAttribute("command") OpenIDLoginCommand command) throws Exception {

        ModelAndView modelAndView;

        User user = this.userRepository.findUserByOpenid(command.getUsername());

        String userPassword = user.getPassword();

        String commandPassword = command.getPassword();

        FreeBSDCryptPasswordEncoder freeBSDCryptPasswordEncoder = new FreeBSDCryptPasswordEncoder();

        if (freeBSDCryptPasswordEncoder.isPasswordValid(userPassword, commandPassword, null)) {

            if (user.getStatus() == Status.VALID) {

                ParameterList parameterList = new ParameterList(httpServletRequest.getParameterMap());

                AuthRequest authReq = AuthRequest.createAuthRequest(parameterList, manager.getRealmVerifier());

                String userSelectedClaimedId = authReq.getClaimed();

                String opLocalId = null;

                // if the user chose a different claimed_id than the one in request
                // NCH the above comment comes from the class we borrowed this code from:
                // http://openid4java.googlecode.com/svn/trunk/src/org/openid4java/server/SampleServer.java
                // I'm not sure how or why a end user could have a different claimed id.
                if (userSelectedClaimedId != null && userSelectedClaimedId.equals(authReq.getClaimed())) {

                    //opLocalId = lookupLocalId(userSelectedClaimedId);
                }

                Message authResponse = this.manager.authResponse(parameterList, opLocalId, userSelectedClaimedId, true, false);

                if (authResponse instanceof DirectError) {

                    // TODO Not sure how to handle a direct error properly.
                    throw new Exception("DIRECT ERROR - what do we do here?");

                } else {

                    this.addRequestedAttributes(authResponse, authReq, user);

                    // Sign the auth success message.
                    // This is required as AuthSuccess.buildSignedList has a `todo' tag now.
                    this.manager.sign((AuthSuccess) authResponse);

                    // set session-scope authentication flag
                    //httpSession.setAttribute(OpenidParameters.SESSION_ATTRIBUTE_AUTHENTICATED, Boolean.TRUE); // keep separate from ParameterList

                    // caller will need to decide which of the following to use:

                    // option1: GET HTTP-redirect to the return_to URL
                    //return response.getDestinationUrl(true);

                    // option2: HTML FORM Redirection
                    modelAndView = new ModelAndView();
                    modelAndView.setView(new RedirectView(authResponse.getDestinationUrl(false)));
                    modelAndView.addAllObjects(authResponse.getParameterMap());
                }

            } else {

                LockedException lockedException = new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));

                httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, lockedException);

                modelAndView = new ModelAndView(this.view);
            }


        } else {

            BadCredentialsException badCredentialsException = new BadCredentialsException(messages.getMessage("OpenidProviderLoginController.badCredentials", "Bad credentials"));

            httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, badCredentialsException);

            modelAndView = new ModelAndView(this.view);
        }

        return modelAndView;
    }

    private void addRequestedAttributes(Message response, AuthRequest authReq, User user) throws MessageException {

        // Attribute Exchange
        if (authReq.hasExtension(AxMessage.OPENID_NS_AX)) {

            OpenidProviderAttributeExchangeFacade openidProviderAttributeExchangeFacade = new OpenidProviderAttributeExchangeFacade(response, authReq, user);
            openidProviderAttributeExchangeFacade.attachAttributesToResponse();
        }

        // Simple Registration
        if (authReq.hasExtension(SRegMessage.OPENID_NS_SREG)) {
            OpenidProviderSRegFacade openidProviderSRegFacade = new OpenidProviderSRegFacade(response, authReq, user);
            openidProviderSRegFacade.attachAttributesToResponse();
        }
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

        this.messages = new MessageSourceAccessor(messageSource);
    }
}
