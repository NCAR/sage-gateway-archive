package sgf.gateway.web.controllers.group;


import org.safehaus.uuid.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.GroupDataType;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.GroupDataRequest;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.group.command.RegistrationFieldCommand;

import javax.validation.Valid;

@Controller
public class AddRegistrationFieldController {

    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;

    public AddRegistrationFieldController(AccountService accountService, RuntimeUserService runtimeUserService) {

        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
    }

    @ModelAttribute("command")
    public RegistrationFieldCommand setupCommand() {

        return new RegistrationFieldCommand();
    }

    @ModelAttribute("groupDataTypes")
    public GroupDataType[] setupGroupDataTypes() {

        return GroupDataType.values();
    }


    @RequestMapping(value = "/ac/group/{group}/registrationField/form/add.html", method = RequestMethod.GET)
    public ModelAndView getNewForm(@PathVariable(value = "group") Group group) {

        User user = this.runtimeUserService.getUser();
        if (!user.isAdmin(group)) {
            throw new AccessDeniedException("You are not an administrator for group " + group.getName());
        }

        return new ModelAndView("/group/addRegistrationGroupForm");
    }

    @RequestMapping(value = "/ac/group/{group}/registrationField.html", method = RequestMethod.POST)
    public ModelAndView postNewRegistrationField(@PathVariable(value = "group") Group group, @ModelAttribute("command") @Valid RegistrationFieldCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        User user = this.runtimeUserService.getUser();
        if (!user.isAdmin(group)) {
            throw new AccessDeniedException("You are not an administrator for group " + group.getName());
        }

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView("/group/addRegistrationGroupForm");

        } else {

            GroupDataRequest request = this.wrapRegistrationFieldCommand(command);

            this.accountService.addGroupDataToGroup(group.getIdentifier(), request);

            redirectAttributes.addFlashAttribute("successMessage", "Registration Field created.");

            modelAndView = new ModelAndView("redirect:/ac/group/" + group.getName() + "/registrationField.html");
        }

        return modelAndView;
    }

    private GroupDataRequest wrapRegistrationFieldCommand(RegistrationFieldCommand command) {

        return new GroupDataRequestWrapper(command);
    }

    private class GroupDataRequestWrapper implements GroupDataRequest {

        private RegistrationFieldCommand command;

        private GroupDataRequestWrapper(RegistrationFieldCommand command) {

            this.command = command;
        }

        @Override
        public UUID getIdentifier() {
            return UUID.valueOf(this.command.getIdentifier());
        }

        @Override
        public String getName() {
            return this.command.getName();
        }

        @Override
        public String getDescription() {
            return this.command.getDescription();
        }

        @Override
        public String getValue() {
            return this.command.getValue();
        }

        @Override
        public GroupDataType getGroupDataType() {
            return this.command.getGroupDataType();
        }
    }
}
