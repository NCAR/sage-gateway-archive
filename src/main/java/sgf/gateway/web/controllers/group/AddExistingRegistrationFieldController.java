package sgf.gateway.web.controllers.group;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.GroupData;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.group.command.ExistingRegistrationFieldCommand;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class AddExistingRegistrationFieldController {

    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;

    public AddExistingRegistrationFieldController(GroupRepository groupRepository, AccountService accountService, RuntimeUserService runtimeUserService) {

        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
    }

    @ModelAttribute("command")
    public ExistingRegistrationFieldCommand setupCommand() {

        return new ExistingRegistrationFieldCommand();
    }

    @ModelAttribute("groupDataCollection")
    public Collection<GroupData> getGroupData(@PathVariable(value = "group") Group group) {

        Collection<GroupData> groupDataCollection = this.groupRepository.findGroupData();

        groupDataCollection.removeAll(group.getGroupData().keySet());

        return groupDataCollection;
    }

    @RequestMapping(value = "/ac/group/{group}/registrationField/form/existing.html", method = RequestMethod.GET)
    public ModelAndView getExistingForm(@PathVariable(value = "group") Group group) {

        User user = this.runtimeUserService.getUser();
        if (!user.isAdmin(group)) {
            throw new AccessDeniedException("You are not an administrator for group " + group.getName());
        }

        return new ModelAndView("/group/addExistingRegistrationGroupForm");
    }

    @RequestMapping(value = "/ac/group/{group}/registrationField/form/existing.html", method = RequestMethod.POST)
    public ModelAndView postExistingRegistrationFields(@PathVariable(value = "group") Group group, @ModelAttribute("command") @Valid ExistingRegistrationFieldCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        User user = this.runtimeUserService.getUser();
        if (!user.isAdmin(group)) {
            throw new AccessDeniedException("You are not an administrator for group " + group.getName());
        }

        this.accountService.addExistingGroupDataToGroup(group.getIdentifier(), command);

        redirectAttributes.addFlashAttribute("successMessage", "Existing Registration Field(s) have been added to Group.");

        return new ModelAndView("redirect:/ac/group/" + group.getName() + "/registrationField.html");
    }
}
