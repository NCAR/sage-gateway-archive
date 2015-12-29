package sgf.gateway.web.controllers.download;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.GuestUser;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

import java.net.URI;

@Controller
public class DownloadHelpController {

    private final RuntimeUserService runtimeUserService;
    private final Gateway gateway;

    public DownloadHelpController(RuntimeUserService userService, Gateway gateway) {

        this.runtimeUserService = userService;
        this.gateway = gateway;
    }

    @RequestMapping(value = "/help/download-help")
    public ModelAndView showHelp() {
        User user = runtimeUserService.getUser();

        URI baseUri = gateway.getBaseURL();

        ModelAndView modelAndView = new ModelAndView("help/download-help");
        modelAndView.addObject("userName", getDisplayUserName(user));
        modelAndView.addObject("gatewayBaseUri", baseUri.toString());

        return modelAndView;
    }

    // TODO: implement
    // Server + name + context
    public URI buildBaseUrl() {

        URI baseUri = gateway.getBaseURL();

        return baseUri;
    }

    //Determine which username to display
    public String getDisplayUserName(User user) {

        String username = user.getUserName();

        // if "guest", "<username>"
        if (GuestUser.GUEST_USERNAME.equals(username)) {
            return "<username>";
        }

        // if logged in, show username
        return username;
    }
}
