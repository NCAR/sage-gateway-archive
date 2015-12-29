package sgf.gateway.web.controllers.metrics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.Gateway;


@Controller
public class GoogleAnalyticsController {

    private final boolean enabled;
    private final String trackingId;
    private final Gateway gateway;

    public GoogleAnalyticsController(final boolean enabled, final String trackingId, final Gateway gateway) {

        this.enabled = enabled;
        this.trackingId = trackingId;
        this.gateway = gateway;
    }

    @RequestMapping(value = "/includes/googleanalytics")
    public ModelAndView datasets() throws Exception {

        ModelAndView modelAndView;

        modelAndView = new ModelAndView("/metrics/googleanalytics");
        modelAndView.addObject("enabled", this.enabled);
        modelAndView.addObject("trackingId", this.trackingId);
        modelAndView.addObject("gateway", this.gateway);

        return modelAndView;
    }
}
