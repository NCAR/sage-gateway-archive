package sgf.gateway.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.dao.metadata.BroadcastMessageRepository;
import sgf.gateway.model.metadata.BroadcastMessage;
import sgf.gateway.service.metadata.BroadcastMessageService;

import javax.validation.Valid;

@Controller
public class BroadcastMessageController {

    // see tiles-definitions.xml
    private String view = "common/broadcast-message"; // to get banner on page

    private String formView = "administration/broadcastMessageForm";

    private BroadcastMessageRepository broadcastMessageRepository;
    private BroadcastMessageService broadcastMessageService;

    private BroadcastMessageController(BroadcastMessageRepository broadcastMessageRepository, BroadcastMessageService broadcastMessageService) {

        this.broadcastMessageRepository = broadcastMessageRepository;
        this.broadcastMessageService = broadcastMessageService;
    }

    /*
     * Show the message on every page (see tiles-definitions.xml and layout-template.jsp)
     */
    @RequestMapping(value = "/common/broadcast-message")
    public ModelAndView handleRequest() throws Exception {

        ModelAndView modelAndView = new ModelAndView(this.view);

        BroadcastMessage message;

        message = broadcastMessageRepository.getMaxBroadcastMessage();
        modelAndView.addObject("message", message);

        return modelAndView;
    }

    // Set up form backing object
    @ModelAttribute("command")
    public BroadcastMessageCommand setupCommand() {

        // Create form backing object
        BroadcastMessageCommand command = new BroadcastMessageCommand();

        return command;
    }

    // Reference data (use in jsp as ${currentMessage.messageText} )
    @ModelAttribute("currentMessage")
    BroadcastMessage getBroadcastMessage() {

        // See what's currently set
        return broadcastMessageRepository.getMaxBroadcastMessage();
    }

    /*
     * Show the form to enter new message.
     */
    @RequestMapping(value = "/administration/broadcastMessage.html", method = RequestMethod.GET)
    public ModelAndView setupForm() throws Exception {

        ModelAndView modelAndView = new ModelAndView(this.formView);

        return modelAndView;
    }

    /*
     * Show confirmation form
     */
    @RequestMapping(value = "/administration/broadcastMessageConfirmation.html", method = RequestMethod.GET)
    ModelAndView showConfirmationForm(@ModelAttribute("command") BroadcastMessageCommand command) {

        ModelAndView modelAndView = new ModelAndView("administration/broadcastMessageConfirmation");
        modelAndView.addObject("message", command.getMessageText());
        modelAndView.addObject("bannerColor", command.getBannerColor());

        return modelAndView;
    }

    /*
     * Submit the form.
     */
    @RequestMapping(value = "/administration/broadcastMessage.html", method = RequestMethod.POST)
    public ModelAndView processSubmit(@RequestParam("bannerConfirmed") boolean bannerConfirmed,
                                      @ModelAttribute("command") @Valid BroadcastMessageCommand command,
                                      BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        String resultView;

        if (bannerConfirmed) {

            // Create new BroadcastMessage
            broadcastMessageService.addMessage(command);

            resultView = "/administration/broadcastMessage.html";

            RedirectView redirectView = new RedirectView(resultView, true);
            modelAndView.setView(redirectView);
        } else {

            resultView = formView;

            modelAndView.setViewName(resultView);

        }

        return modelAndView;
    }

}
