package sgf.gateway.web.controllers.exception.stub;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThrowAcegiException {

    @RequestMapping(value = "/throwAcegiException.html", method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
        throw new AccessDeniedException("Testing acegi message handling");
    }

}
