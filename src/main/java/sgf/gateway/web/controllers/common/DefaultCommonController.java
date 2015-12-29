package sgf.gateway.web.controllers.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.List;

/**
 * Controller that stores a few common objects (user, gateway, workspaces) into request scope before rendering a
 * requested view.
 */
@Controller
public class DefaultCommonController {

    /**
     * The runtime user service.
     */
    private final RuntimeUserService runtimeUserService;

    /**
     * The current gateway.
     */
    private final Gateway gateway;

    private String view;

    /**
     * Instantiates a new default controller.
     *
     * @param runtimeUserService the runtime user service
     * @param gateway            the gateway
     */
    public DefaultCommonController(RuntimeUserService runtimeUserService, Gateway gateway) {

        this.runtimeUserService = runtimeUserService;
        this.gateway = gateway;
    }

    @RequestMapping(value = "/account/user/index.html", method = RequestMethod.GET)
    public ModelAndView handleAccountIndex() throws Exception {

        ModelMap modelMap = new ModelMap();
        User user = this.runtimeUserService.getUser();

        modelMap.put("user", user);

        modelMap.put("gateway", gateway);
        // return all groups that the user is administrator of, for this gateway or the system gateway

        List<Group> adminGroups = user.getAdminGroups();
        modelMap.put("adminGroups", adminGroups);

        return new ModelAndView("account/user/index", modelMap);
    }

    @RequestMapping(value = "/ac/user/index.html", method = RequestMethod.GET)
    public ModelAndView handleAccountUserIndex() throws Exception {

        ModelMap modelMap = new ModelMap();
        User user = this.runtimeUserService.getUser();

        modelMap.put("user", user);

        modelMap.put("gateway", gateway);
        // return all groups that the user is administrator of, for this gateway or the system gateway

        List<Group> adminGroups = user.getAdminGroups();
        modelMap.put("adminGroups", adminGroups);

        return new ModelAndView(this.view, modelMap);
    }

    public void setView(String view) {

        this.view = view;
    }
}
