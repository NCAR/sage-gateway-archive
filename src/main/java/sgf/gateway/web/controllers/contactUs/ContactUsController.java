package sgf.gateway.web.controllers.contactUs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.Gateway;

@Controller
public class ContactUsController {

    private Gateway gateway;

    ContactUsController(Gateway gateway) {
        this.gateway = gateway;
    }

    @RequestMapping(value = {"/contactus/contact-us.htm", "/contactus/contact-us.html"}, method = RequestMethod.GET)
    public ModelAndView showContactPage() {

        ModelAndView modelAndView = new ModelAndView("contactus/contact-us");
        modelAndView.addObject("gateway", gateway);

        return modelAndView;
    }
}
