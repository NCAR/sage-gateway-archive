package sgf.gateway.web.controllers.publish;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.ActivityRepository;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.Activity;
import sgf.gateway.model.metadata.factory.DatasetFactory;
import sgf.gateway.model.metadata.factory.SoftwarePropertiesFactory;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.security.AccountService;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.utils.spring.Constants;
import sgf.gateway.web.controllers.RequestParameterConstants;

import javax.validation.Valid;
import java.util.Date;

/**
 * Controller to create a new dataset, optionally with a given parent, or a given associated project.
 */
@Controller()
public class CreateNewDatasetController {

    private static final String FORM_VIEW = "/publish/createDataset";
    private static final String COMMAND_NAME = "command";

    private static final String DATASET_ID = RequestParameterConstants.DATASET_ID_PARAMETER_VALUE;
    private static final String ACTIVITY_ID = RequestParameterConstants.ACTIVITY_ID_PARAMETER_VALUE;

    private static final Log LOG = LogFactory.getLog(CreateNewDatasetController.class);

    private final DatasetFactory datasetFactory;
    private final SoftwarePropertiesFactory softwarePropertiesFactory;
    private final DatasetRepository datasetRepository;
    private final ActivityRepository activityRepository;
    private final MetadataService metadataService;
    private final AuthorizationUtils authorizationUtils;
    private final RuntimeUserService runtimeUserService;
    private final AccountService accountService;

    public CreateNewDatasetController(DatasetFactory datasetFactory, SoftwarePropertiesFactory softwarePropertiesFactory, DatasetRepository datasetRepository,
                                      ActivityRepository activityRepository, MetadataService metadataService, AccountService accountService,
                                      AuthorizationUtils authorizationUtils, RuntimeUserService runtimeUserService) {

        this.datasetFactory = datasetFactory;
        this.softwarePropertiesFactory = softwarePropertiesFactory;
        this.datasetRepository = datasetRepository;
        this.activityRepository = activityRepository;
        this.metadataService = metadataService;
        this.authorizationUtils = authorizationUtils;
        this.runtimeUserService = runtimeUserService;
        this.accountService = accountService;

    }

    /**
     * Method to load the parent dataset and/or the associated activity, BEFORE the GET/POST request handlers are
     * invoked (i.e. before the view is rendered, and immediately after the form is submitted). This method also
     * suggests some default property values for the dataset to be created. Note: in this method, the HTTP parameter
     * "datasetId" references the parent dataset.
     */
    @ModelAttribute(COMMAND_NAME)
    public CreateNewDatasetCommand populateModel(@RequestParam(value = DATASET_ID, required = false) String parentDatasetId,
                                                 @RequestParam(value = ACTIVITY_ID, required = false) String activityId) {

        CreateNewDatasetCommand command = new CreateNewDatasetCommand();

        // load parent dataset, if found
        if (StringUtils.hasText(parentDatasetId)) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Loading parent dataset id=" + parentDatasetId);
            }

            // load parent from database
            Dataset parent = this.datasetRepository.get(new UUID(parentDatasetId));
            Assert.notNull(parent, "Cannot load enclosing dataset with id=" + parentDatasetId);
            command.setParentDataset(parent);

            // check authorization
            this.authorizationUtils.authorizeForWrite(parent);

            // Deprecate: Going to try not defaulting the title or name - just leave blank.
            // set default values for dataset properties
            //long order = parent.getNestedDatasetCount();
            //command.setDatasetTitle(parent.getTitle() + " child collection #" + order);
            //command.setDatasetShortName(parent.getShortName() + "." + order);

            // top-level dataset
        } else {

            // check authorization
            User user = runtimeUserService.getUser();
            if (!user.isRoot()) {
                throw new RuntimeException("Only Gateway administrators can create top-level datasets.");
            }

        }

        // load associated activity, if found
        if (StringUtils.hasText(activityId)) {

            Activity activity = this.activityRepository.get(new UUID(activityId));
            Assert.notNull(activity, "Cannot find activity with id=" + activityId);
            command.setActivity(activity);
        }

        return command;
    }

    /**
     * Method invoked before form is rendered.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/publish/createDefaultDataset.html", method = RequestMethod.GET)
    public String setupForm(ModelMap model) {
        return FORM_VIEW;
    }

    /**
     * Method invoked after form is submitted, executes validation.
     */
    @RequestMapping(value = "/publish/createDefaultDataset.html", method = RequestMethod.POST)
    public ModelAndView onSubmit(@ModelAttribute(COMMAND_NAME) @Valid CreateNewDatasetCommand data, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return new ModelAndView(FORM_VIEW);
        } else {
            return onSuccesfullSubmit(data);
        }
    }

    /**
     * Method invoked on form submission after successful validation.
     *
     * @param data
     * @return
     */
    public ModelAndView onSuccesfullSubmit(CreateNewDatasetCommand data) {

        String[] datasetTypes = data.getDatasetTypes();

        // create dataset with given parent (other dataset or gateway)
        Dataset parent = data.getParentDataset();

        User user = runtimeUserService.getUser();

        Dataset dataset = datasetFactory.createDataset(data.getDatasetTitle(), data.getDatasetShortName(), parent, user,
                Constants.TEMP_VERSION, null, Constants.TEMP_COMMENT, new Date(System.currentTimeMillis()),
                null, false, data.getAuthoritativeIdentifier());

        // assign Root permissions to top-level dataset
        if (dataset.isTopLevelDataset()) {
            Group rootGroup = accountService.getGroup(Group.GROUP_ROOT);
            dataset.addPermission(rootGroup, Operation.READ);
            dataset.addPermission(rootGroup, Operation.WRITE);
        }

        // associate extra properties
        for (String datasetType : datasetTypes) {

            if ("Software".equals(datasetType) || "ModelSourceCode".equals(datasetType)) {
                dataset.createSoftwareProperties(softwarePropertiesFactory);
            }
        }

        // optionally associate given Activity
        Activity activity = data.getActivity();
        if (activity != null) {
            dataset.associateActivity(activity);
        }

        // Set DOI
        String doiString = data.getDoi();
        dataset.setDOI(doiString);

        // store new dataset
        metadataService.storeDataset(dataset);

        ModelAndView modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + ".html");

        return modelAndView;
    }

}
