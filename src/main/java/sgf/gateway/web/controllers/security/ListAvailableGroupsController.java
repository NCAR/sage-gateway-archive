package sgf.gateway.web.controllers.security;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListAvailableGroupsController extends AbstractController {

    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;
    private final UserRepository userRepository;

    private String view;

    public ListAvailableGroupsController(AccountService accountService, RuntimeUserService runtimeUserService, UserRepository userRepository) {

        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
        this.userRepository = userRepository;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {

        User user = runtimeUserService.getUser(); // from user in session context
        user = this.userRepository.getUser(user.getIdentifier()); // reload from database
        List<Group> groups = accountService.listUnsubscribedGroups(user);

        return new ModelAndView(getView(), "groups", groups);

    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
