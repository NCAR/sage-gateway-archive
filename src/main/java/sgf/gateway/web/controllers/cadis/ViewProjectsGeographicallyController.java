package sgf.gateway.web.controllers.cadis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ViewProjectsGeographicallyController {

    String viewName;

    public ViewProjectsGeographicallyController(String viewName) {

        this.viewName = viewName;
    }

    @RequestMapping(value = "/viewProjectsGeographically.html", method = RequestMethod.GET)
    public ModelAndView showDataServicesPage() {

        ModelAndView modelAndView = new ModelAndView(viewName);

        return modelAndView;
    }


}
