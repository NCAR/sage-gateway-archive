package sgf.gateway.web.controllers.cadis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchOptionsController {

    String viewName;

    public SearchOptionsController(String viewName) {

        this.viewName = viewName;
    }

    @RequestMapping(value = "/cadis/searchOptions.html", method = RequestMethod.GET)
    public ModelAndView showSearchOptionsPage() {

        ModelAndView modelAndView = new ModelAndView(viewName);

        return modelAndView;
    }


}
