package sgf.gateway.web.controllers.cadis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HowToContributeController {

    public HowToContributeController() {

    }

    @RequestMapping(value = "/howToContribute.html", method = RequestMethod.GET)
    public ModelAndView showDataServicesPage() {

        ModelAndView modelAndView = new ModelAndView("cadis/howToContribute");

        return modelAndView;
    }
}
