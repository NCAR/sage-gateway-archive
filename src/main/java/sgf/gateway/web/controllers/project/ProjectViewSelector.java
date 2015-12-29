package sgf.gateway.web.controllers.project;

import org.safehaus.uuid.UUID;
import org.springframework.web.servlet.ModelAndView;

public interface ProjectViewSelector {
    ModelAndView selectProjectView(String shortName);

    ModelAndView selectProjectListingView();

    ModelAndView selectProjectByIdView(UUID projectId);
}
