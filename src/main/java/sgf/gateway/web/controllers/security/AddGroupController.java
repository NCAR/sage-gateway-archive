package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

import javax.validation.Valid;


@Controller
public class AddGroupController {

    private static final String FORM_VIEW = "/ac/admin/addGroupForm";
    private static final String SUCCESS_VIEW = "/ac/admin/addGroupSubmitted";

    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;

    public AddGroupController(GroupRepository groupRepository, AccountService accountService,
                              RuntimeUserService runtimeUserService) {

        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;

    }

    @ModelAttribute(value = "command")
    public AddGroupCommand setupCommand() {
        return new AddGroupCommand();
    }

    @RequestMapping(value = "/ac/root/createGroup.html", method = RequestMethod.GET)
    protected ModelAndView showForm() throws Exception {

        ModelAndView modelAndView = new ModelAndView(FORM_VIEW);

        return modelAndView;
    }

    @RequestMapping(value = "/ac/root/createGroup.html", method = RequestMethod.POST)
    protected ModelAndView onSubmit(@ModelAttribute("command") @Valid AddGroupCommand command, BindingResult bindingResult) throws Exception {

        User user = this.runtimeUserService.getUser();
        Group rootGroup = this.groupRepository.findGroupByName(Group.GROUP_ROOT);
        SecurityControllersUtils.checkAdminRole(user, rootGroup);

        String resultView;

        if (bindingResult.hasErrors()) {

            resultView = FORM_VIEW;

        } else {

            // create new group
            this.accountService.createGroup(command, user);

            resultView = SUCCESS_VIEW;
        }

        return new ModelAndView(resultView);
    }

}
