package sgf.gateway.web.controllers.download;

import org.apache.commons.collections.ListUtils;
import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.utils.FileSizeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class DownloadDatasetsController {

    private final RuntimeUserService runtimeUserService;

    private final DatasetRepository datasetRepository;

    private final LogicalFileRepository logicalFileRespository;

    private final AuthorizationService authorizationService;

    private final EndpointStrategy httpEndpointStrategy;
    private final EndpointStrategy gridftpEndpointStrategy;
    private final EndpointStrategy srmEndpointStrategy;

    public DownloadDatasetsController(RuntimeUserService runtimeUserService, DatasetRepository datasetRepository,
                                      LogicalFileRepository logicalFileRespository, AuthorizationService authorizationService,
                                      EndpointStrategy httpEndpointStrategy, EndpointStrategy gridftpEndpointStrategy, EndpointStrategy srmEndpointStrategy) {

        this.runtimeUserService = runtimeUserService;
        this.datasetRepository = datasetRepository;
        this.logicalFileRespository = logicalFileRespository;
        this.authorizationService = authorizationService;
        this.httpEndpointStrategy = httpEndpointStrategy;
        this.gridftpEndpointStrategy = gridftpEndpointStrategy;
        this.srmEndpointStrategy = srmEndpointStrategy;
    }

    @ModelAttribute
    public FileFilterDefinition createFileFilter() {

        return new FileFilterDefinition();
    }

    @RequestMapping(value = "/download/datasets", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView datasetDownload(@ModelAttribute FileFilterDefinition fileFilterDefinition) {

        ModelAndView modelAndView = new ModelAndView("/download/downloadDatasets");

        List<Dataset> datasets = new ArrayList<>();

        for (String datasetId : fileFilterDefinition.getDatasetId()) {

            Dataset dataset = this.datasetRepository.getByShortName(datasetId);

            datasets.add(dataset);
        }

        List<Dataset> authorizedDatasets = this.authorizeDatasets(datasets);

        List<Dataset> unauthorizedDatasets = ListUtils.removeAll(datasets, authorizedDatasets);


        List<UUID> variableIDs = this.getVariableIdentities(fileFilterDefinition.getVariableName(), fileFilterDefinition.getStandardVariableName());

        Collection<LogicalFile> logicalFiles = new ArrayList<>();

        for (Dataset dataset : authorizedDatasets) {

            logicalFiles.addAll(this.datasetRepository.getFilteredLogicalFiles(dataset, "", variableIDs));
        }

        Collection<LogicalFile> httpLogicalFiles = this.getLogicalFilesWithHttpEndpoints(logicalFiles);

        boolean certificateRequired = isCertificateRequiredForDownload(httpLogicalFiles);

        FileTransferModel httpFileTransferModel = new FileTransferModel(httpLogicalFiles);

        FileTransferModel dmlFileTransferModel = new FileTransferModel(this.getLogicalFilesWithGridFTPEndpoints(logicalFiles));

        FileTransferModel srmFileTransferModel = new FileTransferModel(this.getLogicalFilesWithSRMEndpoints(logicalFiles));

        modelAndView.addObject("variableNames", fileFilterDefinition.getVariableName());
        modelAndView.addObject("standardVariableNames", fileFilterDefinition.getStandardVariableName());

        List<String> selectedVariableNames = new ArrayList<>();
        selectedVariableNames.addAll(fileFilterDefinition.getVariableName());
        selectedVariableNames.addAll(fileFilterDefinition.getStandardVariableName());

        modelAndView.addObject("selectedVariableNames", selectedVariableNames);

        modelAndView.addObject("unauthorizedDatasets", unauthorizedDatasets);
        modelAndView.addObject("datasets", authorizedDatasets);
        modelAndView.addObject("certificateRequired", certificateRequired);
        modelAndView.addObject("totalFileCount", logicalFiles.size());
        modelAndView.addObject("httpFileTransferModel", httpFileTransferModel);
        modelAndView.addObject("dmlFileTransferModel", dmlFileTransferModel);
        modelAndView.addObject("srmFileTransferModel", srmFileTransferModel);

        return modelAndView;
    }

    protected List<Dataset> authorizeDatasets(List<Dataset> datasetsToAuthorize) {

        User user = this.runtimeUserService.getUser();

        List<Dataset> authorizedDatasets = new ArrayList<>();

        for (Dataset dataset : datasetsToAuthorize) {

            boolean authorized = this.authorizationService.authorize(user, dataset, Operation.READ);

            if (authorized) {
                authorizedDatasets.add(dataset);
            }
        }

        return authorizedDatasets;
    }

    protected List<UUID> getVariableIdentities(List<String> names, List<String> standardNames) {

        List<UUID> variableIdentityList = new ArrayList<>();

        variableIdentityList.addAll(this.getVariablesByName(names));

        variableIdentityList.addAll(this.getVariablesByStandardName(standardNames));

        return variableIdentityList;
    }

    protected List<UUID> getVariablesByName(List<String> names) {

        List<UUID> variableIdentityList = new ArrayList<>();

        if (!names.isEmpty()) {

            Collection<Variable> variables = this.logicalFileRespository.findByNames(names);

            for (Variable variable : variables) {

                variableIdentityList.add(variable.getIdentifier());
            }
        }

        return variableIdentityList;
    }

    protected List<UUID> getVariablesByStandardName(List<String> standardNames) {

        List<UUID> variableIdentityList = new ArrayList<>();

        if (!standardNames.isEmpty()) {

            Collection<Variable> variables = this.logicalFileRespository.findByStandardNames(standardNames);

            for (Variable variable : variables) {

                variableIdentityList.add(variable.getIdentifier());
            }
        }

        return variableIdentityList;
    }

    public boolean isCertificateRequiredForDownload(Collection<LogicalFile> logicalFiles) {

        boolean required = false;

        for (LogicalFile logicalFile : logicalFiles) {

            if (logicalFile.isReadRestricted()) {

                required = true;
                break;
            }
        }

        return required;
    }

    public Collection<LogicalFile> getLogicalFilesWithHttpEndpoints(Collection<LogicalFile> logicalFiles) {

        Collection<LogicalFile> httpLogicalFiles = new ArrayList<>();

        for (LogicalFile logicalFile : logicalFiles) {

            boolean exists = this.httpEndpointStrategy.endpointExists(logicalFile);

            if (exists) {

                httpLogicalFiles.add(logicalFile);
            }
        }

        return httpLogicalFiles;
    }

    public Collection<LogicalFile> getLogicalFilesWithGridFTPEndpoints(Collection<LogicalFile> logicalFiles) {

        Collection<LogicalFile> gridftpLogicalFiles = new ArrayList<>();

        for (LogicalFile logicalFile : logicalFiles) {

            boolean exists = this.gridftpEndpointStrategy.endpointExists(logicalFile);

            if (exists) {

                gridftpLogicalFiles.add(logicalFile);
            }
        }

        return gridftpLogicalFiles;
    }

    public Collection<LogicalFile> getLogicalFilesWithSRMEndpoints(Collection<LogicalFile> logicalFiles) {

        Collection<LogicalFile> srmLogicalFiles = new ArrayList<>();

        for (LogicalFile logicalFile : logicalFiles) {

            boolean exists = this.srmEndpointStrategy.endpointExists(logicalFile);

            if (exists) {

                srmLogicalFiles.add(logicalFile);
            }
        }

        return srmLogicalFiles;
    }

    public class FileTransferModel {

        private Collection<LogicalFile> files;

        private long fileSizeinBytes = 0;

        public FileTransferModel(Collection<LogicalFile> files) {

            this.files = files;

            for (LogicalFile logicalFile : this.files) {

                fileSizeinBytes = fileSizeinBytes + logicalFile.getSize();
            }
        }

        public int getFileCount() {

            return files.size();
        }

        public String getFileSize() {

            return FileSizeUtils.getSize(this.fileSizeinBytes);
        }

        public String getFileSizeUnit() {

            return FileSizeUtils.getUnit(this.fileSizeinBytes);
        }
    }
}
