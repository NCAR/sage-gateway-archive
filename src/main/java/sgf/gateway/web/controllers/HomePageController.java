package sgf.gateway.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.web.controllers.security.openid.OpenIdConfiguration;

@Controller
public class HomePageController {

    private final String view;

    private OpenIdConfiguration openIdConfiguration;

    public HomePageController(String view, OpenIdConfiguration openIdConfiguration) {

        this.view = view;
        this.openIdConfiguration = openIdConfiguration;
    }

    @RequestMapping(value = {"/home.htm", "/home.html"}, method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {

        ModelAndView modelAndView = new ModelAndView(this.view);

        modelAndView.addObject("registrationLink", openIdConfiguration.getNewUserRegistrationLink().toString());
        modelAndView.addObject("gettingStarted", "http://cmip-pcmdi.llnl.gov/cmip5/data_getting_started.html?submenuheader=3");

        return modelAndView;
    }
}
