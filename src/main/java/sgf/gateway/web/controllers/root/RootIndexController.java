package sgf.gateway.web.controllers.root;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootIndexController {

    @RequestMapping(value = "/root/index", method = RequestMethod.GET)
    public String getESGRootView() {

        return "root/index";
    }

    @RequestMapping(value = "/cadis/root/index", method = RequestMethod.GET)
    public String getCADISRootView() {

        return "cadis/root/index";
    }
}
