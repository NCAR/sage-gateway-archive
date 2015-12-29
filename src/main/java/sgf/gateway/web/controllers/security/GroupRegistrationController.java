package sgf.gateway.web.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.*;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.RuntimeUserService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


@Controller
public class GroupRegistrationController {

    private final RuntimeUserService runtimeUserService;
    private final AccountService accountService;

    public GroupRegistrationController(RuntimeUserService runtimeUserService, AccountService accountService) {

        this.runtimeUserService = runtimeUserService;
        this.accountService = accountService;
    }

    @ModelAttribute("command")
    public GroupRegistrationCommand setupCommand(@RequestParam("groupName") Group group) {

        User user = this.runtimeUserService.getUser();

        GroupRegistrationCommand command = new GroupRegistrationCommand(user, group);

        return command;
    }

    @ModelAttribute("group")
    public Group getGroupForModel(@RequestParam("groupName") Group group) {

        return group;
    }

    @ModelAttribute("groupDataSet")
    public Set<GroupData> getGroupDataForModel(@RequestParam("groupName") Group group) {

        Map<GroupData, Boolean> groupDataTreeMap = new TreeMap<>(new GroupDataTypeComparator());

        groupDataTreeMap.putAll(group.getGroupData());

        Set<GroupData> groupDataSet = groupDataTreeMap.keySet();

        return groupDataSet;
    }


    @RequestMapping(value = "/ac/user/secure/groupRegistrationForm", method = RequestMethod.GET)
    public ModelAndView getGroupRegistrationForm(@RequestParam("groupName") Group group) {

        ModelAndView modelAndView;

        User user = this.runtimeUserService.getUser();

        Status status = user.getStatusForGroup(group);

        if (status.equals(Status.UNKNOWN)) {

            modelAndView = new ModelAndView("/ac/user/groupRegistrationForm");

        } else {

            modelAndView = new ModelAndView("/ac/user/groupRegistrationAlreadyApplied");
            modelAndView.addObject("status", status);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/ac/user/secure/groupRegistrationForm", method = RequestMethod.POST)
    public ModelAndView submitGroupRegistrationForm(@RequestParam("groupName") Group group, @RequestParam(value = "redirect", required = false) String redirect, @ModelAttribute("command") @Valid GroupRegistrationCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView("/ac/user/groupRegistrationForm");

        } else {

            this.accountService.registerUserInGroup(command);

            if (StringUtils.hasText(redirect)) {

                modelAndView = new ModelAndView("redirect:" + redirect);

                this.addSuccessMessage(group, redirectAttributes);

            } else {

                modelAndView = new ModelAndView("redirect:/account/user/index.html");

                this.addSuccessMessage(group, redirectAttributes);
            }
        }

        return modelAndView;
    }

    public void addSuccessMessage(Group group, RedirectAttributes redirectAttributes) {

        if (group.isAutomaticApproval()) {

            redirectAttributes.addFlashAttribute("successMessage", "You have successfully registered and have immediate access to the requested Group.");

        } else {

            redirectAttributes.addFlashAttribute("successMessage", "You have successfully requested access, but your request requires authorization by a Group Administrator.  You will be notified by email when your request is accepted or rejected.");
        }
    }
}
