package sgf.gateway.web.controllers.project;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.service.metadata.ProjectService;
import sgf.gateway.web.controllers.project.command.ProjectCommandESG;

import javax.validation.Valid;

@Controller
public class EsgProjectEditorController {

    private String formView = "publish/createProject";

    private ProjectService projectService;

    public EsgProjectEditorController(ProjectService projectService) {

        this.projectService = projectService;
    }

    @ModelAttribute("command")
    public ProjectCommandESG setupEsgCommand() {

        return new ProjectCommandESG();
    }

    @RequestMapping(value = {"/createProject.html"}, method = RequestMethod.GET)
    public ModelAndView getNewESGProjectForm() throws Exception {

        return new ModelAndView(this.formView);
    }

    @RequestMapping(value = {"/createProject.html"}, method = RequestMethod.POST)
    public ModelAndView postNewESGProject(@ModelAttribute("command") @Valid ProjectCommandESG command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(this.formView);

        } else {

            Project result = this.projectService.save(command);

            redirectAttributes.addFlashAttribute("successMessage", "Project created.");

            modelAndView = new ModelAndView("redirect:/project/" + result.getPersistentIdentifier() + ".html");
        }

        return modelAndView;
    }
}
