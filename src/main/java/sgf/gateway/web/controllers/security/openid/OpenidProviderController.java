package sgf.gateway.web.controllers.security.openid;

import org.openid4java.message.*;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.server.ServerException;
import org.openid4java.server.ServerManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.service.security.impl.acegi.OpenidProviderAttributeExchangeFacade;
import sgf.gateway.service.security.impl.acegi.OpenidProviderSRegFacade;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;

public class OpenidProviderController extends AbstractController {

    private final ServerManager manager;

    private final UserRepository userRepository;

    private final RuntimeUserService runtimeUserService;

    public OpenidProviderController(ServerManager serverManager, UserRepository userRepository, RuntimeUserService runtimeUserService) {

        this.manager = serverManager;
        this.userRepository = userRepository;
        this.runtimeUserService = runtimeUserService;

    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpReq, HttpServletResponse httpResp) throws Exception {

        ParameterList request = new ParameterList(httpReq.getParameterMap());

        String mode = null;

        if (request.hasParameter("openid.mode")) {

            mode = request.getParameterValue("openid.mode");
        }

        if ("associate".equals(mode)) {

            String responseText = this.associate(request);
            this.directResponse(httpResp, responseText);

        } else if ("checkid_setup".equals(mode) || "checkid_immediate".equals(mode)) {

            ModelAndView modelAndView = checkId(httpReq, httpResp, request);
            return modelAndView;

        } else if ("check_authentication".equals(mode)) {

            String responseText = this.checkAuthentication(request);
            this.directResponse(httpResp, responseText);

        } else {

            String responseText = this.errorResponse(request);
            this.directResponse(httpResp, responseText);
        }

        return null;
    }

    public String associate(ParameterList request) {

        Message response = this.manager.associationResponse(request);
        String responseText = response.keyValueFormEncoding();

        return responseText;
    }

    public ModelAndView checkId(HttpServletRequest httpReq, HttpServletResponse httpResp, ParameterList request) throws Exception {

        ModelAndView modelAndView;

        // NCH I think we check to see if the user is logged in at this point. If they are, send a response to the
        // consumer.
        // If the user isn't logged in at this point we would ask the user for their credentials (password) and then
        // send a response.
        // to the consumer.

        // --- process an authentication request ---
        AuthRequest authReq = AuthRequest.createAuthRequest(request, manager.getRealmVerifier());

        String userSelectedClaimedId = authReq.getClaimed();

        // TODO make sure this is the right type of call. Perhaps we should check to see if the user is null instead?
        // FIXME this logic is not quite right. If a user logs into gateway1 locally and then goes to gateway2 and uses
        // their
        // gateway1 openid to login at gateway2, the user will never be asked to prove their credentials.
        if (this.runtimeUserService.isUserAuthenticated()) {

            User user = this.runtimeUserService.getUser();

            if (user.getOpenid().equals(userSelectedClaimedId)) {

                Boolean authenticatedAndApproved = Boolean.TRUE;

                String opLocalId = null;

                // if the user chose a different claimed_id than the one in request
                // NCH the above comment comes from the class we borrowed this code from:
                // http://openid4java.googlecode.com/svn/trunk/src/org/openid4java/server/SampleServer.java
                // I'm not sure how or why a end user could have a different claimed id. Perhaps the provider
                // could allow the user to change their username when logging in?
                if (userSelectedClaimedId != null && userSelectedClaimedId.equals(authReq.getClaimed())) {

                    // opLocalId = lookupLocalId(userSelectedClaimedId);
                }

                // Sign after we added extensions.
                Message response = this.manager.authResponse(request, opLocalId, userSelectedClaimedId,
                        authenticatedAndApproved.booleanValue(), false);

                if (response instanceof DirectError) {

                    // TODO Not sure how to handle a direct error properly.
                    throw new RuntimeException("DIRECT ERROR - what do we do here?");

                } else {

                    this.addRequestedAttributes(response, authReq, user);

                    // Sign the auth success message.
                    // This is required as AuthSuccess.buildSignedList has a `todo' tag now.
                    this.manager.sign((AuthSuccess) response);

                    // caller will need to decide which of the following to use:

                    // option1: GET HTTP-redirect to the return_to URL
                    // return response.getDestinationUrl(true);

                    // option2: HTML FORM Redirection
                    modelAndView = new ModelAndView();
                    modelAndView.setView(new RedirectView(response.getDestinationUrl(false)));
                    modelAndView.addAllObjects(response.getParameterMap());

                    // DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                    // httpReq.setAttribute("prameterMap", response.getParameterMap());
                    // redirectStrategy.sendRedirect(httpReq, httpResp, response.getDestinationUrl(false));
                    // return null;
                }

            } else {

                // Added because of GTWY-2277.
                // This logic is the exact same as the logic below.
                HttpSession httpSession = httpReq.getSession();
                httpSession.setAttribute(OpenidParameters.SESSION_ATTRIBUTE_PARAMETERLIST, request);

                String requestSite;

                try {

                    Parameter returnTo = request.getParameter("openid.return_to");

                    URI returnToURI = new URI(returnTo.getValue());

                    requestSite = returnToURI.getHost();

                } catch (Exception e) {

                    requestSite = "unknown";
                }

                // TODO This is an interesting condition. The user is using a different openid then one they used before
                // or someone else
                // is using the same browser.
                modelAndView = new ModelAndView("/idp/login");
                OpenIDLoginCommand openidLoginCommand = new OpenIDLoginCommand();
                openidLoginCommand.setUsername(userSelectedClaimedId);
                modelAndView.addObject("command", openidLoginCommand);
                modelAndView.addObject("requestSite", requestSite);
                modelAndView.addObject("parameters", request.getParameters());
            }
        } else {

            HttpSession httpSession = httpReq.getSession();
            httpSession.setAttribute(OpenidParameters.SESSION_ATTRIBUTE_PARAMETERLIST, request);

            String requestSite;

            try {

                Parameter returnTo = request.getParameter("openid.return_to");

                URI returnToURI = new URI(returnTo.getValue());

                requestSite = returnToURI.getHost();

            } catch (Exception e) {

                requestSite = "unknown";
            }

            modelAndView = new ModelAndView("/idp/login");
            OpenIDLoginCommand openidLoginCommand = new OpenIDLoginCommand();
            openidLoginCommand.setUsername(userSelectedClaimedId);
            modelAndView.addObject("command", openidLoginCommand);
            modelAndView.addObject("requestSite", requestSite);
            modelAndView.addObject("parameters", request.getParameters());
        }

        return modelAndView;
    }

    public String checkAuthentication(ParameterList request) {

        Message response = manager.verify(request);
        String responseText = response.keyValueFormEncoding();

        return responseText;
    }

    public String errorResponse(ParameterList request) {

        Message response = DirectError.createDirectError("Unknown request");
        String responseText = response.keyValueFormEncoding();

        return responseText;
    }

    protected User userInteraction(AuthRequest authReq) throws ServerException {

        String claimedId = authReq.getClaimed();

        User user = this.userRepository.findUserByOpenid(claimedId);

        return user;
    }

    private String directResponse(HttpServletResponse httpResp, String response) throws IOException {
        ServletOutputStream os = httpResp.getOutputStream();
        os.write(response.getBytes());
        os.close();

        return null;
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
}
