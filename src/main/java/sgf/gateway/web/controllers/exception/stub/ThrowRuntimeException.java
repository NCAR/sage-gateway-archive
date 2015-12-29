package sgf.gateway.web.controllers.exception.stub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThrowRuntimeException {

    @RequestMapping(value = "/throwRuntimeException.html", method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
        throw new RuntimeException("Test RuntimeException");
    }
}
