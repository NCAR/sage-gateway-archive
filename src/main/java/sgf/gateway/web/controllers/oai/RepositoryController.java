package sgf.gateway.web.controllers.oai;

import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.UUID;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DataCenterRepository;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.DataCenter;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.utils.SanitizeHTML;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = {"/oai/repository", "/oai/repository.htm"})
public class RepositoryController implements MessageSourceAware {

    private String errorView = "error";

    private final Gateway gateway;
    private final DatasetRepository datasetRepository;
    private final DataCenterRepository dataCenterRepository;
    private final int resultsPerRequest;
    private final String gatewayName;

    private MessageSource messageSource;

    public RepositoryController(DatasetRepository datasetRepository, DataCenterRepository dataCenterRepository, Gateway gateway, int resultSize, String gatewayName) {

        this.datasetRepository = datasetRepository;
        this.dataCenterRepository = dataCenterRepository;
        this.gateway = gateway;
        this.resultsPerRequest = resultSize;
        this.gatewayName = gatewayName;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

        PropertyEditor propertyEditor = new RemoveExtraEncodingPropertyEditorWrapper(new ResumptionTokenPropertyEditor());

        binder.registerCustomEditor(ResumptionToken.class, propertyEditor);
    }

    @ModelAttribute("repositoryUrl")
    public String getRepositoryUrl(HttpServletRequest request) {

        return request.getRequestURL().toString();
    }

    @ModelAttribute("gateway")
    public Gateway getGateway() {

        return this.gateway;
    }

    @ModelAttribute("responseDate")
    public Date getResponseDate() {

        return new Date();
    }

    @ModelAttribute("reversedHostName")
    public String getReversedHostName(HttpServletRequest request) {

        String reversedHostName = StringUtils.reverseDelimited(request.getServerName(), '.');

        return reversedHostName;
    }

    @RequestMapping(params = "verb=Identify")
    public ModelAndView identify(@ModelAttribute("request") OAIRequest oaiRequest) {

        ModelAndView result = new ModelAndView("identify");

        result.addObject("supportEmail", this.messageSource.getMessage("contactus.mailaddress", null, null));
        result.addObject("earliestTimestamp", this.datasetRepository.getEarliestDateUpdated());

        return result;
    }

    @RequestMapping(params = "verb=ListMetadataFormats")
    public ModelAndView listMetadataFormats(@ModelAttribute("request") OAIRequest oaiRequest) {

        ModelAndView result = new ModelAndView("listmetadataformats");

        return result;
    }

    @RequestMapping(params = "verb=ListSets")
    public ModelAndView listSets(@ModelAttribute("request") OAIRequest oaiRequest, HttpServletRequest request) {

        ModelMap model = new ModelMap();

        List<DataCenter> dataCenters = this.dataCenterRepository.getAll();

        model.addAttribute("dataCenters", dataCenters);

        return new ModelAndView("listsets", model);
    }

    @RequestMapping(params = "verb=ListIdentifiers")
    public ModelAndView listIdentifiers(@ModelAttribute("request") @Valid ListRecordsRequest listRecordsRequest, BindingResult bindingResult, HttpServletRequest request) throws ParseException {

        ModelAndView result = new ModelAndView();

        if (bindingResult.hasErrors()) {

            // Should this have an error code?
            return this.returnError(bindingResult.getAllErrors());

        } else {

            result.setViewName("listidentifiers");

            Collection<Dataset> datasets = loadDatasets(listRecordsRequest);

            if (datasets.isEmpty()) {
                result.setViewName(this.errorView);
                result.addObject("errorCode", "noRecordsMatch");
                result.addObject("errorMessage", "The combination of the values of the from, until, and set arguments results in an empty list.");
            }

            result.addObject("datasets", datasets);

            ResumptionToken resumptionToken = buildResumptionToken(listRecordsRequest, datasets);

            result.addObject("resumptionToken", this.encode(resumptionToken));
        }

        return result;
    }

    @RequestMapping(params = {"verb=ListIdentifiers", "resumptionToken"})
    public ModelAndView resumeListIdentifiers(@ModelAttribute("request") ResumeListRecordsRequest resumeListRecordsRequest, HttpServletRequest request) throws ParseException {

        ModelAndView result = new ModelAndView("listidentifiers");

        Collection<Dataset> datasets = loadDatasets(resumeListRecordsRequest);

        result.addObject("datasets", datasets);

        ResumptionToken resumptionToken = null;

        if (datasets.size() == this.resultsPerRequest) {
            resumptionToken = resumeListRecordsRequest.getResumptionToken();
            resumptionToken.incrementOffset(resultsPerRequest);
        }

        result.addObject("resumptionToken", this.encode(resumptionToken));

        return result;
    }

    @RequestMapping(params = "verb=GetRecord")
    public ModelAndView getRecord(@ModelAttribute("request") @Valid GetRecordRequest getRecordRequest, BindingResult bindingResult, HttpServletRequest request) throws ParseException {

        ModelAndView result = new ModelAndView();

        if (bindingResult.hasErrors()) {

            result.setViewName(this.errorView);
            List<ObjectError> errorList = bindingResult.getAllErrors();
            result.addObject("errorList", errorList);

        } else {

            result.setViewName("getrecord");

            Dataset dataset = null;

            try {

                UUID identifier = UUID.valueOf(getRecordRequest.getIdentifier());

                dataset = this.datasetRepository.get(identifier);

            } catch (NumberFormatException e) {

            } catch (ObjectNotFoundException e) {

            }

            if (null == dataset) {

                result.setViewName(this.errorView);
                result.addObject("errorCode", "idDoesNotExist ");
                result.addObject("errorMessage", "The value of the identifier argument is unknown or illegal in this repository.");
            }

            result.addObject("dataset", dataset);
            result.addObject("gatewayName", this.gatewayName);

        }
        return result;
    }

    @RequestMapping(params = "verb=ListRecords")
    public ModelAndView listRecords(@ModelAttribute("request") @Valid ListRecordsRequest listRecordsRequest, BindingResult bindingResult, HttpServletRequest request) throws ParseException {

        ModelAndView result = new ModelAndView();

        if (bindingResult.hasErrors()) {

            // error.ftl
            result.setViewName(this.errorView);

            List<ObjectError> errorList = bindingResult.getAllErrors();
            result.addObject("errorList", errorList);

        } else {

            Collection<Dataset> datasets = loadDatasets(listRecordsRequest);

            if (datasets.isEmpty()) {

                result.setViewName(this.errorView);
                result.addObject("errorCode", "noRecordsMatch");
                result.addObject("errorMessage", "The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list.");

            } else {

                result.setViewName("listrecords");

                result.addObject("gatewayName", this.gatewayName);

                Collection<Dataset> sanitizedDatasets = new ArrayList<Dataset>();

                for (Dataset dataset : datasets) {

                    String sanitizedDescription = SanitizeHTML.cleanHTML(dataset.getDescription());
                    dataset.setDescription(sanitizedDescription);
                    sanitizedDatasets.add(dataset);
                }

                result.addObject("datasets", sanitizedDatasets);

                ResumptionToken resumptionToken = buildResumptionToken(listRecordsRequest, sanitizedDatasets);

                result.addObject("resumptionToken", this.encode(resumptionToken));

            }
        }

        return result;
    }

    @RequestMapping(params = {"verb=ListRecords", "resumptionToken"})
    public ModelAndView resumeListRecords(@ModelAttribute("request") ResumeListRecordsRequest resumeListRecordsRequest, HttpServletRequest request) throws ParseException {

        ModelAndView result = new ModelAndView("listrecords");

        Collection<Dataset> datasets = loadDatasets(resumeListRecordsRequest);

        result.addObject("datasets", datasets);
        result.addObject("gatewayName", this.gatewayName);

        ResumptionToken resumptionToken = null;

        if (datasets.size() == this.resultsPerRequest) {
            resumptionToken = resumeListRecordsRequest.getResumptionToken();
            resumptionToken.incrementOffset(resultsPerRequest);
        }

        result.addObject("resumptionToken", this.encode(resumptionToken));

        return result;
    }

    @RequestMapping()
    public ModelAndView unrecognizedVerb(@ModelAttribute("request") OAIRequest oaiRequest, HttpServletRequest request) {

        ModelAndView result = returnError("badVerb", "Value of the verb argument is not a legal OAI-PMH verb, the verb argument is missing, or the verb argument is repeated.");

        return result;
    }

    protected ModelAndView returnError(String errorCode, String errorMessage) {

        ModelAndView result = new ModelAndView(this.errorView);

        result.addObject("errorCode", errorCode);
        result.addObject("errorMessage", errorMessage);

        return result;
    }

    protected ModelAndView returnError(List<ObjectError> errors) {

        ModelAndView result = new ModelAndView(this.errorView);

        // Should be setting the error code
        result.addObject("errorList", errors);

        return result;
    }

    protected String encode(ResumptionToken resumptionToken) {

        ResumptionTokenPropertyEditor resumptionTokenPropertyEditor = new ResumptionTokenPropertyEditor();
        resumptionTokenPropertyEditor.setValue(resumptionToken);

        String encodedToken = resumptionTokenPropertyEditor.getAsText();

        return encodedToken;
    }

    protected ResumptionToken buildResumptionToken(ListRecordsRequest listRecordsRequest, Collection<Dataset> datasets) {

        ResumptionToken resumptionToken = null;

        // If the result size equals request size go ahead and create resumption token.
        // If the size is < request size we are on an empty page or the last page and
        // don't need a token.
        if (datasets.size() == this.resultsPerRequest) {
            resumptionToken = new ResumptionToken(listRecordsRequest, resultsPerRequest);
        }

        return resumptionToken;
    }

    protected Collection<Dataset> loadDatasets(ListRequest listRequest) {

        Collection<Dataset> datasets;

        String setSpec = listRequest.getSet();
        String metadataPrefix = listRequest.getMetadataPrefix();

        if (metadataPrefix.equals("dif") || metadataPrefix.equals("ISO19139") || metadataPrefix.equals("oai_dc")) {

            datasets = this.datasetRepository.findByDateRangeForOai(setSpec, listRequest.getFrom(), listRequest.getUntil(), resultsPerRequest, listRequest.getOffset());

        } else {

            throw new UnknownMetadataPrefix("Unknown metadataPrefix: " + metadataPrefix);
        }

        return datasets;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exceptionHandler(Exception exception, HttpServletRequest request) {

        ModelAndView result = new ModelAndView(this.errorView);

        result.addObject("repositoryUrl", this.getRepositoryUrl(request));
        result.addObject("responseDate", this.getResponseDate());
        result.addObject("errorCode", exception.getClass().getName());
        result.addObject("errorMessage", exception.getMessage());

        return result;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

        this.messageSource = messageSource;
    }

    public class UnknownMetadataPrefix extends RuntimeException {

        public UnknownMetadataPrefix(String message) {
            super(message);
        }
    }

}
