package sgf.gateway.web.controllers.exception.stub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThrowSocketException {

    @RequestMapping(value = "/throwSocketException.html", method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {

        throw new java.net.SocketException("Test exception");
    }
}
