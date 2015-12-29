package sgf.gateway.web.controllers.publish;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.metadata.ActivityRepository;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.PublishedState;
import sgf.gateway.model.metadata.activities.Activity;
import sgf.gateway.model.metadata.activities.ActivityType;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.model.security.*;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.web.controllers.RequestParameterConstants;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Controller to edit the metadata of an existing dataset.
 */
@Controller
public class EditDatasetMetadataController {

    private static final String FORM_VIEW = "/publish/editDatasetMetadata";
    private static final String COMMAND_NAME = "command";
    private static final Log LOG = LogFactory.getLog(EditDatasetMetadataController.class);
    private static final String DATASET_ID = RequestParameterConstants.DATASET_ID_PARAMETER_VALUE;

    private final DatasetRepository datasetRepository;
    private final ActivityRepository activityRepository;
    private final GroupRepository groupRepository;
    private final ProjectRepository projectRepository;
    private final MetadataService metadataService;
    private final AuthorizationUtils authorizationUtils;

    public EditDatasetMetadataController(final DatasetRepository datasetRepository, final ActivityRepository activityRepository, final GroupRepository groupRepository,
                                         final ProjectRepository projectRepository, final MetadataService metadataService, final AuthorizationUtils authorizationUtils) {

        this.datasetRepository = datasetRepository;
        this.activityRepository = activityRepository;
        this.groupRepository = groupRepository;
        this.projectRepository = projectRepository;
        this.metadataService = metadataService;
        this.authorizationUtils = authorizationUtils;
    }

    /**
     * Overridden method to load the dataset by identifier BEFORE the GET/POST request handlers are invoked (i.e. before
     * rendering the view, and immediately after the form is submitted, but before binding).
     */
    @ModelAttribute(COMMAND_NAME)
    public EditDatasetMetadataCommand formBackingObject(@RequestParam(DATASET_ID) String datasetId) {

        EditDatasetMetadataCommand command = new EditDatasetMetadataCommand();

        command.setIdentifier(datasetId);

        return command;
    }

    /**
     * Method invoked before form is rendered.
     *
     * @return
     */
    @RequestMapping(value = "/publish/editDefaultDatasetMetadata.htm", method = RequestMethod.GET)
    public String setupForm(@ModelAttribute(COMMAND_NAME) EditDatasetMetadataCommand command) {

        // list all available groups
        Set<Group> groups = new TreeSet<Group>(new GroupComparator());
        groups.add(this.groupRepository.findGroupByName(Group.GROUP_DEFAULT));
        groups.add(this.groupRepository.findGroupByName(Group.GROUP_GUEST));
        groups.add(this.groupRepository.findGroupByName(Group.GROUP_ROOT));
        groups.add(this.groupRepository.findGroupByName(Group.GROUP_NOBODY));
        groups.addAll(this.groupRepository.getAll());
        command.setAllGroups(groups);

        UUID datasetId = UUID.valueOf(command.getIdentifier());

        // load dataset from database
        if (LOG.isDebugEnabled()) {
            LOG.debug("formBackingObject(): loading dataset id=" + datasetId);
        }

        Dataset dataset = datasetRepository.get(datasetId);

        Assert.notNull(dataset, "Cannot load dataset with id=" + datasetId);
        // command.setDataset(dataset);

        // check authorization
        this.authorizationUtils.authorizeForWrite(dataset);

        // retrieve current groups authorized to read, write this dataset
        command.setReadGroups(this.getDatasetGroupsForOperation(dataset, Operation.READ));
        command.setWriteGroups(this.getDatasetGroupsForOperation(dataset, Operation.WRITE));

        // list all available Projects
        command.setAllProjects(this.projectRepository.getAll());

        // retrieve project directly associated to this dataset (i.e. NOT inherited)
        Project project = (Project) dataset.getActivities().get(ActivityType.PROJECT);

        if (project != null) {
            command.setProjectId(project.getIdentifier().toString());
        }

        command.setShortName(dataset.getShortName());
        command.setTitle(dataset.getTitle());
        command.setDescription(dataset.getDescription());
        command.setDoi(dataset.getDOI());
        command.setDatasetState(dataset.getPublishedState());

        return FORM_VIEW;
    }

    //TODO: This wasn't mapped with annotation.  Check/fix validation.
    @RequestMapping(value = "/publish/editDefaultDatasetMetadata", method = RequestMethod.POST)
    public ModelAndView onSubmit(@ModelAttribute("command") EditDatasetMetadataCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // TODO: Service.update(id)
        UUID datasetId = UUID.valueOf(command.getIdentifier());

        Dataset dataset = datasetRepository.get(datasetId);

        // access control
        this.setDatasetGroupsForOperation(dataset, Operation.READ, command.getReadGroups());
        this.setDatasetGroupsForOperation(dataset, Operation.WRITE, command.getWriteGroups());

        // project
        String projectId = command.getProjectId();
        if (StringUtils.hasText(projectId)) {

            Activity activity = this.activityRepository.get(new UUID(projectId));
            Assert.notNull(activity, "Cannot find activity with id=" + projectId);
            // associate (possibly replace) the project for this Dataset
            dataset.associateActivity(activity);

        } else {

            Project project = (Project) dataset.getActivities().get(ActivityType.PROJECT);
            if (project != null) {
                // remove the project for this dataset
                dataset.removeActivity(project);
            }
        }

        // call Service to set values
        this.setDatasetState(dataset, command.getDatasetState());
        dataset.setTitle(command.getTitle());
        dataset.setDescription(command.getDescription());
        dataset.setDOI(command.getDoi());

        // save dataset
        metadataService.storeDataset(dataset);

        ModelAndView modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + ".html");

        return modelAndView;
    }

    /**
     * Utility method to extract the names of the Groups that are authorized to perform the given Operation on the given
     * Dataset. Note that this method is NOT recursive (i.e. it does NOT look up the Dataset hierarchy).
     *
     * @return
     */
    private String[] getDatasetGroupsForOperation(Dataset dataset, Operation operation) {

        Set<String> groupNames = new HashSet<>();
        for (Permission permission : dataset.getPermissions()) {
            if (permission.getOperation().equals(operation) && (permission.getPrincipal() instanceof Group)) {
                groupNames.add(permission.getPrincipal().getName());
            }
        }
        // insert empty group if none found
        if (groupNames.size() == 0) {
            groupNames.add("");
        }
        return groupNames.toArray(new String[0]);
    }

    /**
     * Utility method to reset the Groups that are authorized to perform the given Operation on the given Dataset.
     *
     * @param dataset
     * @param operation
     * @param groupNames
     */
    private void setDatasetGroupsForOperation(Dataset dataset, Operation operation, String[] groupNames) {

        Set<Principal> groups = new HashSet<>();
        for (String groupName : groupNames) {
            if (StringUtils.hasText(groupName)) {
                groups.add(this.groupRepository.findGroupByName(groupName));
            }
        }
        dataset.setPrincipalsForOperation(groups, operation);
    }

    private void setDatasetState(Dataset dataset, PublishedState datasetState) {

        // If the form did not reset the state (e.g., stays PRE_PUBLISHED)
        if (null != datasetState) {
            if (datasetState.equals(PublishedState.PUBLISHED)) {
                dataset.getCurrentDatasetVersion().publish();
            } else if (datasetState.equals(PublishedState.RETRACTED)) {
                dataset.getCurrentDatasetVersion().retract();
            }
        }
    }

}
