package sgf.gateway.web.controllers.browse;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.utils.FileSizeUtils;

import javax.validation.Valid;
import java.util.*;

@Controller
public class DownloadDatasetFilesController {

    private final MetadataService metadataService;

    private final String viewName;

    private final AuthorizationUtils authorizationUtils;

    private EndpointStrategy httpEndpointStrategy;
    private EndpointStrategy gridftpEndpointStrategy;
    private EndpointStrategy srmEndpointStrategy;

    public DownloadDatasetFilesController(MetadataService metadataService, AuthorizationUtils authorizationUtils, String viewName,
                                          EndpointStrategy httpEndpointStrategy, EndpointStrategy gridftpEndpointStrategy, EndpointStrategy srmEndpointStrategy) {

        this.metadataService = metadataService;
        this.authorizationUtils = authorizationUtils;
        this.viewName = viewName;
        this.httpEndpointStrategy = httpEndpointStrategy;
        this.gridftpEndpointStrategy = gridftpEndpointStrategy;
        this.srmEndpointStrategy = srmEndpointStrategy;
    }

    @ModelAttribute("command")
    public DownloadDatasetFilesCommand setupCommand(@RequestParam(value = "datasetId") UUID datasetId) {

        return new DownloadDatasetFilesCommand(datasetId);
    }

    @RequestMapping(value = "/browse/downloadLogicalFiles.html", method = RequestMethod.POST)
    public ModelAndView handleRequest(@ModelAttribute("command") @Valid DownloadDatasetFilesCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        ModelAndView modelAndView;

        UUID[] logicalFileIdentities = command.getLogicalFileId();

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.command", bindingResult);
            modelAndView = new ModelAndView("redirect:/browse/viewCollectionFilesInitial.html?datasetId=" + command.getDatasetId());

        } else {
            List<UUID> logicalFileIdentityList = new ArrayList<>();

            Collections.addAll(logicalFileIdentityList, logicalFileIdentities);

            List<LogicalFile> logicalFileList = this.metadataService.findLogicalFileById(logicalFileIdentityList);

            List<LogicalFile> wgetList = new ArrayList<>();
            List<LogicalFile> massStoreList = new ArrayList<>();
            List<LogicalFile> dmlList = new ArrayList<>();

            long wgetByteSize = 0;
            long massStoreByteSize = 0;
            long dmlByteSize = 0;

            Set<Dataset> datasetSet = new HashSet<>();

            for (LogicalFile logicalFile : logicalFileList) {

                datasetSet.add(logicalFile.getDataset());
            }

            for (Dataset dataset : datasetSet) {

                this.authorizationUtils.authorizeForRead(dataset);
            }

            for (LogicalFile logicalFile : logicalFileList) {

                if (this.httpEndpointStrategy.endpointExists(logicalFile)) {

                    wgetByteSize += logicalFile.getSize();
                    wgetList.add(logicalFile);
                }

                if (this.gridftpEndpointStrategy.endpointExists(logicalFile)) {

                    dmlByteSize += logicalFile.getSize();
                    dmlList.add(logicalFile);
                }

                if (this.srmEndpointStrategy.endpointExists(logicalFile)) {

                    massStoreByteSize += logicalFile.getSize();
                    massStoreList.add(logicalFile);
                }
            }

            modelAndView = new ModelAndView(this.viewName);

            modelAndView.addObject("wgetList", wgetList);
            modelAndView.addObject("wgetListSize", wgetList.size());
            modelAndView.addObject("wgetDisplaySize", FileSizeUtils.getSize(wgetByteSize));
            modelAndView.addObject("wgetDisplayUnit", FileSizeUtils.getUnit(wgetByteSize));
            modelAndView.addObject("massStoreList", massStoreList);
            modelAndView.addObject("massStoreListSize", massStoreList.size());
            modelAndView.addObject("massStoreDisplaySize", FileSizeUtils.getSize(massStoreByteSize));
            modelAndView.addObject("massStoreDisplayUnit", FileSizeUtils.getUnit(massStoreByteSize));
            modelAndView.addObject("dmlList", dmlList);
            modelAndView.addObject("dmlListSize", dmlList.size());
            modelAndView.addObject("dmlDisplaySize", FileSizeUtils.getSize(dmlByteSize));
            modelAndView.addObject("dmlDisplayUnit", FileSizeUtils.getUnit(dmlByteSize));

        }

        return modelAndView;
    }
}
