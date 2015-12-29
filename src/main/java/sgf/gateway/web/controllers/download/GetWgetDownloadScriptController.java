package sgf.gateway.web.controllers.download;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.User;
import sgf.gateway.script.core.impl.FileDownloadScriptModel;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.security.RuntimeUserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class GetWgetDownloadScriptController {

    private static final String DOWNLOAD_FILENAME = "wget-download.sh";

    private final Gateway gateway;
    private final MetadataService metadataService;
    private final EndpointStrategy endpointStrategy;
    private final RuntimeUserService runtimeUserService;
    private final WgetScriptViewSelector viewSelector;

    public GetWgetDownloadScriptController(Gateway gateway, MetadataService metadataService, EndpointStrategy endpointStrategy, RuntimeUserService runtimeUserService,
                                           WgetScriptViewSelector viewSelector) {

        this.gateway = gateway;
        this.metadataService = metadataService;
        this.endpointStrategy = endpointStrategy;
        this.runtimeUserService = runtimeUserService;
        this.viewSelector = viewSelector;
    }

    @RequestMapping("/download/generateWGetScript.wget")
    public ModelAndView handleWgetRequest(@RequestParam(value = "logicalFileId") UUID[] logicalFileIds, HttpServletResponse response) throws Exception {

        Collection<LogicalFile> logicalFiles = this.getLogicalFiles(logicalFileIds);

        ModelAndView modelAndView = this.createAndLoadModelAndView(logicalFiles);

        this.prepResponseForScriptAttachment(response, DOWNLOAD_FILENAME);

        return modelAndView;
    }

    private Collection<LogicalFile> getLogicalFiles(UUID[] logicalFileIds) throws Exception {

        List<LogicalFile> logicalFileList = this.metadataService.findLogicalFileById(Arrays.asList(logicalFileIds));

        return logicalFileList;
    }

    private ModelAndView createAndLoadModelAndView(Collection<LogicalFile> logicalFiles) throws Exception {

        String view = this.viewSelector.select(logicalFiles);
        FileDownloadScriptModel downloadScriptModel = this.createFileDownloadScriptModel(logicalFiles);

        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("gateway", this.gateway);
        modelAndView.addObject("downloadScriptModel", downloadScriptModel);

        return modelAndView;
    }

    private FileDownloadScriptModel createFileDownloadScriptModel(Collection<LogicalFile> logicalFiles) throws Exception {

        User user = runtimeUserService.getUser();
        FileDownloadScriptModel downloadScriptModel = new FileDownloadScriptModel(this.gateway, user, logicalFiles, this.endpointStrategy);

        return downloadScriptModel;
    }

    private void prepResponseForScriptAttachment(HttpServletResponse response, String fileName) {
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, private");
    }
}
