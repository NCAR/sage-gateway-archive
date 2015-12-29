package sgf.gateway.web.controllers.cadis.publish;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetComparator;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.security.RuntimeUserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Controller that returns a list of the top level (project) datasets a user is able to edit.
 */
@Controller
public class ListPermissableTopLevelDatasetsController {

    private MetadataService metadataService;
    private RuntimeUserService runtimeUserService;

    private static final String CREATE_VIEW = "cadis/publish/view-editable-toplevel-datasets";  //Projects

    public ListPermissableTopLevelDatasetsController(final MetadataService metadataService, final RuntimeUserService runtimeUserService) {
        this.metadataService = metadataService;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/user/publish/viewEditableProjects", method = RequestMethod.GET)
    public ModelAndView getDatasetList() throws Exception {

        ModelAndView modelAndView = new ModelAndView(CREATE_VIEW);

        User currentUser = this.runtimeUserService.getUser();

        List<Dataset> sortedWritables;

        sortedWritables = this.getWritableProjectDatasets(currentUser);

        modelAndView.addObject("writableDatasets", sortedWritables);
        modelAndView.addObject("user", currentUser);

        return modelAndView;
    }

    /**
     * Determine writable Top-level (project) datasets for current user.
     *
     * @param currentUser
     * @return List<Dataset>
     */
    private List<Dataset> getWritableProjectDatasets(User currentUser) {

        List<Dataset> sortedWritables = null;

        if (currentUser != null) {

            Collection<Dataset> writableDatasets = this.metadataService.findDatasetsByOperation(currentUser, Operation.WRITE);

            sortedWritables = new ArrayList<>();

            for (Dataset dataset : writableDatasets) {

                // Only add Project Datasets
                if (dataset.isTopLevelDataset()) {

                    sortedWritables.add(dataset);
                }
            }

            Collections.sort(sortedWritables, new DatasetComparator());
        }

        return sortedWritables;
    }

}
