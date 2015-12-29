package sgf.gateway.web.controllers.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.Calendar;

@Controller
public class DefaultFooterController {

    private final RuntimeUserService runtimeUserService;
    private final Gateway gateway;
    private final String view;

    public DefaultFooterController(RuntimeUserService runtimeUserService, Gateway gateway, String view) {

        this.runtimeUserService = runtimeUserService;
        this.view = view;
        this.gateway = gateway;
    }

    @RequestMapping(value = "/includes/footer.html")
    public ModelAndView handleRequest() throws Exception {

        User user = this.runtimeUserService.getUser();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        ModelAndView modelAndView = new ModelAndView(this.view);
        modelAndView.addObject("user", user);
        modelAndView.addObject("gateway", this.gateway);
        modelAndView.addObject("copyrightDate", Integer.toString(currentYear));

        return modelAndView;
    }

}
