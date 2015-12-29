package sgf.gateway.web.controllers.metrics;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class OffsiteLinkMetricsController {

    // Redirect so request so we collect access metrics
    @RequestMapping(value = "/redirect.html", method = RequestMethod.GET)
    public ModelAndView redirect(@RequestParam(value = "link", required = true) String link) {

        ModelAndView result;

        RedirectView redirectView = new RedirectView(link);
        result = new ModelAndView(redirectView);

        return result;
    }

}
