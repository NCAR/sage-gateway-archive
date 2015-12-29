package sgf.gateway.web.controllers.browse.cadis;

import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CADISProjectViewHandler {

    private final String datasetRepresentationView;

    public CADISProjectViewHandler(String datasetRepresentationView) {
        this.datasetRepresentationView = datasetRepresentationView;
    }

    public ModelAndView handleProjectView(Dataset dataset) {

        ModelAndView modelAndView = new ModelAndView(this.datasetRepresentationView);

        modelAndView.addObject("dataset", dataset);
        modelAndView.addObject("successorProject", dataset.getSuccessorProject());
        modelAndView.addObject("predecessorProject", dataset.getPredecessorProject());
        modelAndView.addObject("partiesByRole", this.createPartiesByRoleMap(dataset));

        return modelAndView;
    }

    private Map<String, List<String>> createPartiesByRoleMap(Dataset dataset) {

        Map<String, List<String>> partiesByRole = new LinkedHashMap<String, List<String>>();

        for (ResponsiblePartyRole role : ResponsiblePartyRole.values()) {
            partiesByRole.put(role.getRoleName(), this.createRoleEntryForMap(dataset, role));
        }

        return partiesByRole;
    }

    private List<String> createRoleEntryForMap(Dataset dataset, ResponsiblePartyRole role) {

        List<String> parties = new ArrayList<String>();

        for (ResponsibleParty party : dataset.getDescriptiveMetadata().getResponsibleParties()) {
            if (party.getRole().equals(role)) {
                parties.add(party.getIndividualName());
            }
        }

        return parties;
    }
}
