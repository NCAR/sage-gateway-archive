package sgf.gateway.web.controllers.browse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.web.controllers.RequestAttributeConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultProjectViewHandler extends ParameterizableViewController implements ProjectViewHandler {

    public boolean supports(Project project) {
        return true;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(getViewName());
    }

    public ModelAndView handleProjectView(Project project) {
        return new ModelAndView(getViewName(), new ModelMap(RequestAttributeConstants.PROJECT, project));
    }
}
