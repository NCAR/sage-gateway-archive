package sgf.gateway.web.controllers.cadis.administration;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.metadata.ContainerType;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.User;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.security.SecurityControllersUtils;

import java.util.Set;

@Controller
public class CadisDatasetPermissionsController {

    private final GroupRepository groupRepository;
    private final RuntimeUserService runtimeUserService;
    private final DatasetRepository datasetRepository;
    private final MetadataService metadataService;

    public CadisDatasetPermissionsController(GroupRepository groupRepository, RuntimeUserService runtimeUserService, DatasetRepository datasetRepository, MetadataService metadataService) {

        this.groupRepository = groupRepository;
        this.runtimeUserService = runtimeUserService;
        this.datasetRepository = datasetRepository;
        this.metadataService = metadataService;
    }

    @RequestMapping(value = "/ac/root/datasetPermissions.html", method = RequestMethod.GET)
    public ModelAndView onGet() {

        ModelAndView modelAndView = new ModelAndView("/cadis/administration/dataset-permissions");

        return modelAndView;
    }

    @RequestMapping(value = "/ac/root/datasetPermissions.html", method = RequestMethod.POST)
    public ModelAndView onPost(@ModelAttribute("command") DatasetPermissionsCommand datasetPermissionsCommand)
            throws Exception {

        datasetPermissionsCommand.updateUserPermissions();

        Dataset dataset = datasetPermissionsCommand.getDataset();

        this.metadataService.storeDataset(dataset);

        ModelAndView modelAndView;

        if (dataset.getContainerType().equals(ContainerType.DATASET)) {
            modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + ".html");
        } else {
            modelAndView = new ModelAndView("redirect:/project/" + dataset.getShortName() + ".html");
        }

        return modelAndView;
    }

    @ModelAttribute("command")
    public DatasetPermissionsCommand formBackingObject(@RequestParam(value = "datasetId") UUID datasetId) throws Exception {

        // security check
        User admin = runtimeUserService.getUser();
        Group rootGroup = groupRepository.findGroupByName(Group.GROUP_ROOT);
        SecurityControllersUtils.checkAdminRole(admin, rootGroup);

        Dataset dataset = this.datasetRepository.get(datasetId);

        Group cadisGroup = this.groupRepository.findGroupByName("ACADIS");

        Set<User> users = cadisGroup.getUsers();

        DatasetPermissionsCommand datasetPermissionsCommand = new DatasetPermissionsCommand(dataset, users, rootGroup.getAdministrators());

        return datasetPermissionsCommand;
    }
}
