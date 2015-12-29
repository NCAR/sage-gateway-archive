package sgf.gateway.web.controllers.dataset.responsibleParty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class ResponsiblePartyController {

    @RequestMapping(value = {"/dataset/{dataset}/responsibleParty.html", "/project/{dataset}/responsibleParty.html"}, method = RequestMethod.GET)
    public ModelAndView getResponsibleParty(@PathVariable(value = "dataset") Dataset dataset, HttpServletRequest request) {

        String viewPathOption = getViewPathFromRequestUrl(request); //"project" or "dataset"

        String viewName = "/dataset/responsibleParty/viewResponsibleParties";

        if (viewPathOption.equals("project")) {
            viewName = "/project/responsibleParty/viewResponsibleParties";
        }

        Collection<ResponsibleParty> responsibleParties = dataset.getDescriptiveMetadata().getResponsibleParties();

        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("responsibleParties", responsibleParties);

        return modelAndView;
    }

    // To avoid duplicating the method for the "project" vs "dataset" path, determine path so decide which url to follow next
    private String getViewPathFromRequestUrl(HttpServletRequest request) {

        String mappingValue = request.getServletPath();

        // "dataset" or "project"
        String[] projectOrDatasetUrlPathway = mappingValue.split("/");

        return projectOrDatasetUrlPathway[1];
    }

}
