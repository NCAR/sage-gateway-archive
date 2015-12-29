package sgf.gateway.web.controllers.exception.stub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThrowArgumentException {

    @RequestMapping(value = "/throwArgumentException.html", method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
        throw new IllegalArgumentException("Test exception.");
    }

}
