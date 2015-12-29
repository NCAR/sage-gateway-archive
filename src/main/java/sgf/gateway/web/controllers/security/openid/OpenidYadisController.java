package sgf.gateway.web.controllers.security.openid;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for OpenID Yadis discovery: this controller processes all requests sent to the Gateway Yadis servlet, and
 * returns the Gateway OpenID provider endpoint embedded within an XML document.
 */
public class OpenidYadisController extends AbstractController {

    private Gateway gateway;

    private UserRepository userRepository;

    public OpenidYadisController(Gateway gateway, UserRepository userRepository) {
        super();
        this.gateway = gateway;
        this.userRepository = userRepository;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String openid = request.getRequestURL().toString();

        User user = userRepository.findUserByOpenid(openid);

        if (user == null) {
            // TODO: Is a 404 response the correct type of response to return here? Maybe?
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not resolve the OpenId: " + openid);

            return null;
        }

        ModelMap model = new ModelMap();

        model.addAttribute("user", user);
        model.addAttribute("gateway", gateway);

        response.setContentType("application/xrds+xml");

        ModelAndView result = null;

        if ("GET".equalsIgnoreCase(request.getMethod())) {

            result = new ModelAndView("/security/yadis/discovery", model);
        }

        return result;
    }

}
