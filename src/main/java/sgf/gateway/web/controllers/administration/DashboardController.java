package sgf.gateway.web.controllers.administration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.web.controllers.FileDownloadCounter;

@Controller
public class DashboardController {

    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public ModelAndView getDashboard() {

        ModelAndView modelAndView = new ModelAndView("administration/dashboard");

        modelAndView.addObject("diskDownloadCount", FileDownloadCounter.getDiskDownloadCount());
        modelAndView.addObject("srmDownloadCount", FileDownloadCounter.getSrmDownloadCount());

        return modelAndView;
    }
}
