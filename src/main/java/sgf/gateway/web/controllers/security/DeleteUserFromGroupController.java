package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

@Controller
public class DeleteUserFromGroupController {

    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;

    private String view;

    public DeleteUserFromGroupController(GroupRepository groupRepository, AccountService accountService, RuntimeUserService runtimeUserService) {

        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
    }

    @ModelAttribute("command")
    public Group setupCommand() {

        Group group = new Group();

        return group;
    }

    @RequestMapping(value = "/ac/user/deleteUserFromGroup.html", method = RequestMethod.POST)
    public ModelAndView postForm(@ModelAttribute("command") Group command, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        Group group = groupRepository.findGroupByName(command.getName());

        User user = this.runtimeUserService.getUser();

        this.accountService.removeUserFromGroup(user, group);

        return new ModelAndView(this.getView());
    }

    public String getView() {

        return view;
    }

    public void setView(String view) {

        this.view = view;
    }

}
