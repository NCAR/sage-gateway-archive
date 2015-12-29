package sgf.gateway.web.controllers.group;

import org.safehaus.uuid.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.GroupData;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

@Controller
public class DeleteRegistrationFieldController {

    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final RuntimeUserService runtimeUserService;

    public DeleteRegistrationFieldController(GroupRepository groupRepository, AccountService accountService, RuntimeUserService runtimeUserService) {

        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/ac/group/{group}/registrationField/{id}/form/confirmDelete.html", method = RequestMethod.GET)
    public ModelAndView deleteConfirm(@PathVariable(value = "group") Group group, @PathVariable(value = "id") UUID identifier) {

        User user = this.runtimeUserService.getUser();
        if (!user.isAdmin(group)) {
            throw new AccessDeniedException("You are not an administrator for group " + group.getName());
        }

        GroupData groupData = this.groupRepository.getGroupData(identifier);

        ModelAndView modelAndView = new ModelAndView("group/confirm-delete-group-data");
        modelAndView.addObject("groupData", groupData);

        return modelAndView;
    }

    @RequestMapping(value = "/ac/group/{group}/registrationField/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteGroupData(@PathVariable(value = "group") Group group, @PathVariable(value = "id") UUID identifier, @RequestParam("deleteConfirmed") boolean deleteConfirmed, RedirectAttributes redirectAttributes) {

        User user = this.runtimeUserService.getUser();
        if (!user.isAdmin(group)) {
            throw new AccessDeniedException("You are not an administrator for group " + group.getName());
        }

        ModelAndView modelAndView;

        if (deleteConfirmed) {

            this.accountService.removeGroupDataFromGroup(group.getIdentifier(), identifier);

            redirectAttributes.addFlashAttribute("successMessage", "Registration Field was removed from Group.");
            modelAndView = new ModelAndView("redirect:/ac/group/" + group.getName() + "/registrationField.html");

        } else {

            GroupData groupData = this.groupRepository.getGroupData(identifier);

            modelAndView = new ModelAndView("group/confirm-delete-group-data");
            modelAndView.addObject("groupData", groupData);
        }

        return modelAndView;
    }
}
