package sgf.gateway.web.controllers.browse;

import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.activities.project.Project;

public interface ProjectViewHandler {

    boolean supports(Project project);

    ModelAndView handleProjectView(Project project);
}
