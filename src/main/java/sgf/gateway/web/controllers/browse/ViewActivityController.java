package sgf.gateway.web.controllers.browse;

import org.safehaus.uuid.UUID;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.ActivityRepository;
import sgf.gateway.model.metadata.activities.Activity;
import sgf.gateway.service.metadata.ObjectNotFoundException;

@Deprecated
@org.springframework.stereotype.Controller
public class ViewActivityController {

    private final ActivityRepository activityRepository;

    public ViewActivityController(ActivityRepository activityRepository) {

        this.activityRepository = activityRepository;
    }

    /**
     * Warning, this class cannot be changed to a annotation based controller.  The url for this controller /browse/viewActivity.htm is also shared by the cadis/ViewActivityController.
     * We will need to figure out what we're doing with activities before changing this controller to be annotation based.
     */
    @RequestMapping(value = {"/browse/viewActivity.htm", "/browse/viewActivity.html"}, method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(value = "activityId") UUID activityId) throws Exception {

        Activity activity = activityRepository.get(activityId);

        if (activity == null) {

            throw new ObjectNotFoundException(activityId);
        }

        ModelMap modelMap = new ModelMap();

        modelMap.addAttribute("activity", activity);

        return new ModelAndView("browse/view-activity", modelMap);
    }

}
