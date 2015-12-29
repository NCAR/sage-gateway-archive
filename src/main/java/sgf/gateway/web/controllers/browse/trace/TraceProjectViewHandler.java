package sgf.gateway.web.controllers.browse.trace;

import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.web.controllers.browse.DefaultProjectViewHandler;

public class TraceProjectViewHandler extends DefaultProjectViewHandler {

    public boolean supports(Project project) {

        boolean value = false;

        if (project.getName().toLowerCase().equals("trace")) {

            value = true;
        }

        return value;
    }

    @Override
    public ModelAndView handleProjectView(Project project) {

        ModelAndView modelAndView = new ModelAndView(this.getViewName());

        modelAndView.addObject("project", project);

        modelAndView.addObject("traceDatasetName", "ucar.cgd.ccsm3.trace");

        return modelAndView;
    }
}
