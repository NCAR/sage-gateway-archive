package sgf.gateway.web.controllers.cadis.publish;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.security.AuthorizationUtils;

@Controller
public class CadisDatasetWizardController {

    private final AuthorizationUtils authorizationUtils;

    public CadisDatasetWizardController(AuthorizationUtils authorizationUtils) {

        this.authorizationUtils = authorizationUtils;
    }

    /**
     * Show form to select enclosing container.  The dataset here will be the top of the hierarchy (Project)
     */
    @RequestMapping(value = "/dataset/{dataset}/createNewDataset1.html", method = RequestMethod.GET)
    public ModelAndView showSelectContainerForm(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("/cadis/publish/createNewDatasetForm1");

        return modelAndView;
    }

    /**
     * Show form to select dataset template. "theDataset" is going to start out being the root (project) dataset, but then
     * as the view recurses, the url will get a new "top" dataset as you go down the tree.  So just re-get the parent (root) each
     * time this is called.
     */
    @RequestMapping(value = "/dataset/{dataset}/createNewDataset2.html", method = RequestMethod.GET)
    public ModelAndView showSelectMetadataForm(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("/cadis/publish/createNewDatasetForm2");
        modelAndView.addObject("rootDataset", dataset.getRootParentDataset());

        return modelAndView;
    }
}
