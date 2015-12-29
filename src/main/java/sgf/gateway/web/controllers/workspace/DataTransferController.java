package sgf.gateway.web.controllers.workspace;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.dataaccess.FileAccessPointImpl;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.service.workspace.DataTransferService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class DataTransferController {

    private DataTransferService dataTransferService;
    private RuntimeUserService runtimeUserService;
    private DatasetRepository datasetRepository;
    private LogicalFileRepository logicalFileRespository;
    private EndpointStrategy srmEndPointStrategy;

    private String view;

    public DataTransferController(DataTransferService dataTransferService, RuntimeUserService runtimeUserService, DatasetRepository datasetRepository, LogicalFileRepository logicalFileRespository, EndpointStrategy srmEndPointStrategy) {

        super();

        this.dataTransferService = dataTransferService;
        this.runtimeUserService = runtimeUserService;
        this.datasetRepository = datasetRepository;
        this.logicalFileRespository = logicalFileRespository;
        this.srmEndPointStrategy = srmEndPointStrategy;
    }

    @RequestMapping(value = {"/download/dataTransfer.htm", "/download/dataTransfer.html"}, method = RequestMethod.POST)
    public ModelAndView handleRequest(@RequestParam(value = "datasetId", required = false) Collection<String> datasetShortNames,
                                      @RequestParam(value = "variableName", required = false) Collection<String> variableNames,
                                      @RequestParam(value = "standardVariableName", required = false) Collection<String> standardVariableNames,
                                      @RequestParam(value = "logicalFileId", required = false) Collection<UUID> logicalFileIds) throws Exception {

        User user = runtimeUserService.getUser();

        // added for "bulk dataset download" - ejn
        // check for datasetID, variableName, standardVariableName
        // and add filtered logical files to transfer request

        Collection<Dataset> datasets = new ArrayList<>();

        if (datasetShortNames != null) {

            for (String datasetShortName : datasetShortNames) {

                Dataset dataset = this.datasetRepository.getByShortName(datasetShortName);

                datasets.add(dataset);
            }
        }

        List<UUID> variableIDs = this.getVariableIdentities(variableNames, standardVariableNames);

        if (logicalFileIds == null) {
            logicalFileIds = new ArrayList<>();
        }

        for (Dataset dataset : datasets) {

            Collection<LogicalFile> logicalFiles = this.datasetRepository.getFilteredLogicalFiles(dataset, "", variableIDs);

            for (LogicalFile logicalFile : logicalFiles) {

                if (this.srmEndPointStrategy.endpointExists(logicalFile)) {

                    logicalFileIds.add(logicalFile.getIdentifier());
                }
            }
        }

        List<FileAccessPointImpl> transferItems = this.dataTransferService.getFileAccessPointList(logicalFileIds);
        this.dataTransferService.submit(user, transferItems);

        return new ModelAndView(this.getView());
    }

    protected List<UUID> getVariableIdentities(Collection<String> names, Collection<String> standardNames) {

        List<UUID> variableIdentityList = new ArrayList<>();

        if (names != null) {

            for (Variable variable : this.logicalFileRespository.findByNames(names)) {

                variableIdentityList.add(variable.getIdentifier());
            }
        }

        if (standardNames != null) {

            for (Variable variable : this.logicalFileRespository.findByStandardNames(standardNames)) {

                variableIdentityList.add(variable.getIdentifier());
            }
        }

        return variableIdentityList;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
