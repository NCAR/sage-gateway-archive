package sgf.gateway.web.controllers.project;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.CadisProjectService;
import sgf.gateway.utils.propertyeditors.SimpleFormatedDatePropertyEditor;
import sgf.gateway.web.ProjectURITemplate;
import sgf.gateway.web.controllers.project.command.ProjectCommand;
import sgf.gateway.web.controllers.project.command.ProjectCommandTypeWrapper;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class ProjectEditorController {

    private String formView = "project/projectForm";

    private CadisProjectService projectService;
    private ProjectURITemplate projectURITemplate;

    private SimpleFormatedDatePropertyEditor simpleFormatedDatePropertyEditor;

    public ProjectEditorController(CadisProjectService projectService, SimpleFormatedDatePropertyEditor simpleFormatedDatePropertyEditor) {

        this.projectService = projectService;
        this.simpleFormatedDatePropertyEditor = simpleFormatedDatePropertyEditor;

        // FIXME - Inject this.
        this.projectURITemplate = new ProjectURITemplate("/project/{persistentIdentifier}.html");
    }

    @ModelAttribute("command")
    public ProjectCommand setupCommand() {

        //Create form backing object
        ProjectCommand command = new ProjectCommand();

        return command;
    }

    @RequestMapping(value = {"/project/form/create"}, method = RequestMethod.GET)
    public ModelAndView getNewForm() throws Exception {

        ModelAndView modelAndView = new ModelAndView(this.formView);

        modelAndView.addObject("postUri", "/project.html");
        modelAndView.addObject("pageTitle", "Create Project");
        modelAndView.addObject("buttonText", "Create Project");

        return modelAndView;
    }

    @RequestMapping(value = {"/project.html"}, method = RequestMethod.POST)
    public ModelAndView postNewProject(@ModelAttribute("command") @Valid ProjectCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(this.formView);

            modelAndView.addObject("postUri", "/project.html");
            modelAndView.addObject("pageTitle", "Create Project");
            modelAndView.addObject("buttonText", "Create Project");

        } else {

            ProjectCommandTypeWrapper commandWrapper = new ProjectCommandTypeWrapper(command);

            Dataset dataset = this.projectService.save(commandWrapper);

            redirectAttributes.addFlashAttribute("successMessage", "Project created.");

            modelAndView = new ModelAndView("redirect:/project/" + dataset.getShortName() + ".html");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/project/{dataset}/form/edit", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable("dataset") Dataset dataset) {

        ProjectCommand command = new ProjectCommand(dataset);

        ModelAndView modelAndView = new ModelAndView(this.formView, "command", command);

        modelAndView.addObject("postUri", "/project/" + dataset.getShortName() + ".html");
        modelAndView.addObject("pageTitle", "Edit Project: " + dataset.getTitle());
        modelAndView.addObject("buttonText", "Save Project");

        return modelAndView;

    }

    @RequestMapping(value = "/project/{dataset}", method = RequestMethod.POST)
    public ModelAndView updateProject(@PathVariable("dataset") Dataset dataset, @ModelAttribute("command") @Valid ProjectCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(this.formView, "command", command);
            modelAndView.addObject("postUri", "/project/" + dataset.getShortName() + ".html");
            modelAndView.addObject("pageTitle", "Edit Project: " + dataset.getTitle());
            modelAndView.addObject("buttonText", "Save Project");

        } else {

            ProjectCommandTypeWrapper commandWrapper = new ProjectCommandTypeWrapper(command);

            Dataset result = this.projectService.update(dataset.getIdentifier(), commandWrapper);

            redirectAttributes.addFlashAttribute("successMessage", "Project updated.");

            modelAndView = new ModelAndView(getSuccessView(result));
        }

        return modelAndView;

    }

    protected String getSuccessView(Dataset dataset) {

        return "redirect:" + this.projectURITemplate.expand(dataset).toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, this.simpleFormatedDatePropertyEditor);
    }

    public void setFormView(String formView) {

        this.formView = formView;
    }
}
