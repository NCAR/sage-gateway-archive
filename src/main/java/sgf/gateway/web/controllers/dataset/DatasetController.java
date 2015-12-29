package sgf.gateway.web.controllers.dataset;

import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.search.service.MoreLikeThisService;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.utils.SanitizeHTML;
import sgf.gateway.web.controllers.dataset.command.DatasetVersionFilesCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class DatasetController {

    private final DatasetRepository datasetRepository;
    private final Gateway gateway;
    private final MoreLikeThisService moreLikeThisService;
    private final String ezidPublisher;

    private String datasetView;

    public DatasetController(DatasetRepository datasetRepository, Gateway gateway, MoreLikeThisService moreLikeThisService, String ezidPublisher) {
        this.datasetRepository = datasetRepository;
        this.gateway = gateway;
        this.moreLikeThisService = moreLikeThisService;
        this.ezidPublisher = ezidPublisher;
    }

    @RequestMapping(value = {"/dataset.html"}, method = RequestMethod.GET)
    public ModelAndView datasets() throws Exception {

        Collection<Dataset> topLevelDatasets = this.datasetRepository.getTopLevel();

        ModelAndView modelAndView = new ModelAndView("/browse/view-datasets");
        modelAndView.addObject("collections", topLevelDatasets);

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}.html"}, method = RequestMethod.HEAD)
    public void datasetHeadRequest(@PathVariable(value = "dataset") Dataset dataset, HttpServletResponse response) throws Exception {

        SimpleDateFormat rfc2822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

        Date dateModified = dataset.getDateUpdated();

        String formatedDate = rfc2822DateFormat.format(dateModified);

        response.setHeader("Last-Modified", formatedDate);
    }

    @RequestMapping(value = {"/dataset/{dataset}.html"}, method = RequestMethod.GET)
    public ModelAndView details(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        ModelAndView modelAndView = new ModelAndView(this.datasetView, "dataset", dataset);

        List<Dataset> parentList = datasetRepository.getParentList(dataset);

        modelAndView.addObject("parentList", parentList);

        Collection<DatasetVersion> datasetVersions = dataset.getDatasetVersions();

        List<DatasetVersion> datasetVersionList = new ArrayList<>(datasetVersions);

        Collections.sort(datasetVersionList, new DatasetVersionDateUpdatedComparator());

        modelAndView.addObject("datasetVersions", datasetVersionList);

        modelAndView.addObject("moreLikeThisList", this.moreLikeThisService.moreLikeThis(dataset));

        // Might be better to use ServletUriComponentsBuilder in this case rather than depend on the gateway instance.
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(this.gateway.getBaseSecureURL() + "/dataset/{identifier}/version.atom").build();

        URI feedUri = uriComponents.expand(dataset.getShortName()).encode().toUri();

        feedUri = feedUri.normalize();

        modelAndView.addObject("feedUri", feedUri);

        modelAndView.addObject("partiesByRole", this.createPartiesByRoleMap(dataset));

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}.html"}, method = RequestMethod.GET, params = "ref")
    public ModelAndView detailsWithRef(@PathVariable(value = "dataset") Dataset dataset, @RequestParam(value = "ref", required = true) String reference) {

        RedirectView redirectView = new RedirectView("/dataset/" + dataset.getShortName() + ".html", true);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return new ModelAndView(redirectView);
    }

    @RequestMapping(value = {"/dataset/{dataset}.dif", "/dataset/{dataset}.iso19139", "/dataset/{dataset}.txt"}, method = RequestMethod.GET)
    public ModelAndView detailsXML(@PathVariable(value = "dataset") Dataset dataset, HttpServletRequest request) throws Exception {

        ModelAndView modelAndView = this.details(dataset);

        String reversedHostName = StringUtils.reverseDelimited(request.getServerName(), '.');

        modelAndView.addObject("reversedHostName", reversedHostName);
        modelAndView.addObject("gateway", gateway);
        modelAndView.addObject("gatewayName", this.ezidPublisher);

        String sanitizedDescription = SanitizeHTML.cleanHTML(dataset.getDescription());

        modelAndView.addObject("sanitizedDatasetDescription", sanitizedDescription);

        modelAndView.setViewName("browse/view-dataset");

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/id/{datasetId}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable(value = "datasetId") UUID identifier) throws Exception {
        if (identifier == null) {
            throw new ObjectNotFoundException();
        }
        return getRedirectViewForDataset(identifier, ".html");
    }

    @RequestMapping(value = "/dataset/id/{datasetId}.html", method = RequestMethod.GET)
    public ModelAndView detailsAsHTML(@PathVariable(value = "datasetId") UUID identifier) throws Exception {
        if (identifier == null) {
            throw new ObjectNotFoundException();
        }
        return getRedirectViewForDataset(identifier, ".html");
    }

    @RequestMapping(value = "/dataset/id/{datasetId}.dif", method = RequestMethod.GET)
    public ModelAndView detailsAsDIF(@PathVariable(value = "datasetId") UUID identifier) throws Exception {
        if (identifier == null) {
            throw new ObjectNotFoundException();
        }
        return getRedirectViewForDataset(identifier, ".dif");
    }

    @RequestMapping(value = "/dataset/id/{datasetId}.iso19139", method = RequestMethod.GET)
    public ModelAndView detailsAsISO(@PathVariable(value = "datasetId") UUID identifier) throws Exception {
        if (identifier == null) {
            throw new ObjectNotFoundException();
        }
        return getRedirectViewForDataset(identifier, ".iso19139");
    }

    @RequestMapping(value = "/dataset/id/{datasetId}.txt", method = RequestMethod.GET)
    public ModelAndView detailsAsTXT(@PathVariable(value = "datasetId") UUID identifier) throws Exception {
        if (identifier == null) {
            throw new ObjectNotFoundException();
        }
        return getRedirectViewForDataset(identifier, ".txt");
    }

    private ModelAndView getRedirectViewForDataset(UUID identifier, String viewExtension) {
        Dataset dataset = this.datasetRepository.get(identifier);
        RedirectView redirectView = new RedirectView("/dataset/" + dataset.getShortName() + viewExtension, true);

        return new ModelAndView(redirectView);
    }

    @RequestMapping(value = {"/dataset/{dataset}/version/{datasetVersionIdentifier}/file.html"}, method = RequestMethod.GET)
    public ModelAndView datasetVersion(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "datasetVersionIdentifier") String datasetVersionIdentifier, HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = null;

        if (null != dataset) {

            DatasetVersion datasetVersion = this.getDatasetVersion(dataset, datasetVersionIdentifier);

            if (null != datasetVersion) {

                // TODO configure/inject this "command" object?
                DatasetVersionFilesCommand command = new DatasetVersionFilesCommand();
                command.setDatasetVersion(datasetVersion);

                modelAndView = new ModelAndView("/browse/view-dataset-files", "command", command);

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not resolve dataset version");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Could not resolve dataset");
        }

        return modelAndView;
    }

    private DatasetVersion getDatasetVersion(Dataset dataset, String datasetVersionIdentifier) {

        // TODO move this behavior to Dataset?
        DatasetVersion datasetVersion = null;

        Collection<DatasetVersion> datasetVersions = dataset.getDatasetVersions();

        for (DatasetVersion loop : datasetVersions) {

            if (loop.getVersionIdentifier().equals(datasetVersionIdentifier)) {
                datasetVersion = loop;
                break;
            }
        }

        return datasetVersion;
    }

    public void setDatasetView(String datasetView) {
        this.datasetView = datasetView;
    }

    private Map<String, List<String>> createPartiesByRoleMap(Dataset dataset) {

        Map<String, List<String>> partiesByRole = new LinkedHashMap<>();

        for (ResponsiblePartyRole role : ResponsiblePartyRole.values()) {
            partiesByRole.put(role.getRoleName(), this.createRoleEntryForMap(dataset, role));
        }

        return partiesByRole;
    }

    private List<String> createRoleEntryForMap(Dataset dataset, ResponsiblePartyRole role) {

        List<String> parties = new ArrayList<>();

        for (ResponsibleParty party : dataset.getDescriptiveMetadata().getResponsibleParties()) {
            if (party.getRole().equals(role)) {
                parties.add(party.getIndividualName());
            }
        }

        return parties;
    }

    @RequestMapping(value = "/dataset/{dataset}/hierarchy.html", method = RequestMethod.GET)
    public ModelAndView showDatasetHierarchy(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        ModelAndView modelAndView = new ModelAndView("/cadis/publish/datasetHierarchy");
        modelAndView.addObject("rootDataset", dataset.getRootParentDataset());

        return modelAndView;
    }
}
