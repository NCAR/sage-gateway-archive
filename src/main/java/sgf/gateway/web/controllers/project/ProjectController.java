package sgf.gateway.web.controllers.project;

import org.safehaus.uuid.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.model.metadata.Dataset;

@Controller
public class ProjectController {

    private final ProjectViewSelector projectViewSelector;

    public ProjectController(ProjectViewSelector projectViewSelector) {
        this.projectViewSelector = projectViewSelector;
    }

    @RequestMapping(value = "/project/{dataset}/dataset.atom", method = RequestMethod.GET)
    public ModelAndView projectDatasetFeed(@PathVariable(value = "dataset") Dataset dataset) {

        ModelAndView modelAndView = new ModelAndView("view-project-dataset-feed");

        modelAndView.addObject("projectDataset", dataset);

        return modelAndView;
    }

    @RequestMapping(value = {"/project/{project_name}/datasets.atom", "/project/{project_name}/dataset/feed", "/projects/{project_name}/datasets/feed"}, method = RequestMethod.GET)
    public ModelAndView redirectProjectDatasetFeed(@PathVariable(value = "project_name") String projectName) {

        RedirectView redirectView = new RedirectView("/project/" + projectName + "/dataset.atom");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return new ModelAndView(redirectView);
    }

    @RequestMapping(value = "/project.html", method = RequestMethod.GET)
    public ModelAndView getProjects() {
        return this.projectViewSelector.selectProjectListingView();
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ModelAndView redirectProjects() {

        RedirectView redirectView = new RedirectView("/project.html");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        ModelAndView modelAndView = new ModelAndView(redirectView);

        return modelAndView;
    }

    @RequestMapping(value = "/project/{project}.html", method = RequestMethod.GET)
    public ModelAndView getProject(@PathVariable(value = "project") String shortName) {
        return this.projectViewSelector.selectProjectView(shortName);
    }

    @RequestMapping(value = "/project/{project}.html", method = RequestMethod.GET, params = "ref")
    public ModelAndView getRefProject(@PathVariable(value = "project") String shortName) {

        RedirectView redirectView = new RedirectView("/project/" + shortName + ".html");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return new ModelAndView(redirectView);
    }

    @RequestMapping(value = "/projects/{shortName}", method = RequestMethod.GET)
    public ModelAndView redirectProject(@PathVariable(value = "shortName") String shortName) {

        RedirectView redirectView = new RedirectView("/project/" + shortName + ".html", true);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        ModelAndView modelAndView = new ModelAndView(redirectView);

        return modelAndView;
    }

    @RequestMapping(value = "/project/id/{projectId}", method = RequestMethod.GET)
    public ModelAndView getProjectById(@PathVariable(value = "projectId") UUID projectId) {
        return this.projectViewSelector.selectProjectByIdView(projectId);
    }
}
