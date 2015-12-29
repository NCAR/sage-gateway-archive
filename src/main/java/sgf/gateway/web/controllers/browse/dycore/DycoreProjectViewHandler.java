package sgf.gateway.web.controllers.browse.dycore;

import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.web.controllers.browse.DefaultProjectViewHandler;

public class DycoreProjectViewHandler extends DefaultProjectViewHandler {

    public boolean supports(Project project) {

        boolean value = false;

        if (project.getName().equals("DyCore")) {

            value = true;
        }

        return value;
    }
}
