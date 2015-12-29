package sgf.gateway.web.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.service.feedback.FeedbackService;

import javax.validation.Valid;

@Controller
public class FeedbackController {

    private static final String FEEDBACK_SUBMITTED = "redirect:/home.html";
    private static final String FEEDBACK_FORM = "/feedbackForm";

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {

        this.feedbackService = feedbackService;
    }

    @ModelAttribute("command")
    public FeedbackCommand setupCommand() {

        FeedbackCommand feedbackCommand = new FeedbackCommand();

        return feedbackCommand;
    }

    @RequestMapping(value = "/feedback.html", method = RequestMethod.GET)
    public ModelAndView getFeedbackForm() {

        ModelAndView modelAndView = new ModelAndView(FEEDBACK_FORM);

        return modelAndView;
    }

    @RequestMapping(value = "/feedback.html", method = RequestMethod.POST)
    public ModelAndView postFeedbackForm(@ModelAttribute("command") @Valid FeedbackCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(FEEDBACK_FORM);

        } else {

            // Phone is a honeypot field to stop spambots.
            if (StringUtils.isBlank(command.getPhone())) {

                this.feedbackService.handleFeedback(command);

                redirectAttributes.addFlashAttribute("successMessage", "Thank you for giving us your feedback!  It is appreciated.  If you gave us your email address we will send you a personalized reply in 2 to 3 business days.");
            }

            modelAndView = new ModelAndView(FEEDBACK_SUBMITTED);
        }

        return modelAndView;
    }
}
