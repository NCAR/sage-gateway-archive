package sgf.gateway.web.controllers.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetImpl;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.Operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class AccessDeniedController extends AbstractController {

    protected static final Log LOG = LogFactory.getLog(AccessDeniedController.class);

    private String view;
    private DatasetRepository datasetRepository;

    protected AccessDeniedController(DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    public void setView(String view) {

        this.view = view;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UUID datasetId = UUID.valueOf(request.getParameter("datasetId"));
        Dataset dataset = this.datasetRepository.get(datasetId);

        String operationName = request.getParameter("operation");
        Operation operation = Operation.getOperationByName(operationName);

        Set<Group> groups = ((DatasetImpl) dataset).getGroupsForOperation(operation);

        ModelMap model = new ModelMap("groups", groups);
        model.addAttribute("dataset", dataset);

        return new ModelAndView(view, model);
    }
}
