package sgf.gateway.web.controllers.metrics;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DashboardController {

    @RequestMapping(value = "/dashboard.html")
    public ModelAndView redirectDashboardRequest() {

        RedirectView redirectView = new RedirectView("/metrics/summaryStatistics.html");
        redirectView.setStatusCode(HttpStatus.PERMANENT_REDIRECT);

        ModelAndView modelAndView = new ModelAndView(redirectView);

        return modelAndView;
    }
}
