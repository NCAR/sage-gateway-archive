package sgf.gateway.web.controllers.cadis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AboutUsController {

    private String viewName;

    public AboutUsController(String viewName) {

        this.viewName = viewName;
    }

    @RequestMapping(value = "/about/aboutUs.html", method = RequestMethod.GET)
    public ModelAndView showAboutUsPage() {

        ModelAndView modelAndView = new ModelAndView(viewName);

        return modelAndView;
    }


}
