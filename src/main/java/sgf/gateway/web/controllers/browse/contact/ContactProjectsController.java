package sgf.gateway.web.controllers.browse.contact;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.ResponsiblePartyRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.PrincipalInvestigatorWithData;
import sgf.gateway.service.metadata.ResponsiblePartyNotFoundException;

import java.util.List;

@Controller
public class ContactProjectsController {

    private ResponsiblePartyRepository responsiblePartyRepository;

    private ContactProjectsController(ResponsiblePartyRepository responsiblePartyRepository) {

        this.responsiblePartyRepository = responsiblePartyRepository;
    }

    // This is singular "contact" because "contacts" is used for Admin Contact edit list
    @RequestMapping(value = {"/contact.htm", "/contact.html"}, method = RequestMethod.GET)
    public ModelAndView contacts() throws Exception {

        // Create a list for the view which has fullname, lastname, firstname ordered by lname
        List<PrincipalInvestigatorWithData> pis = this.responsiblePartyRepository.findAllPIsWithData();

        ModelMap model = new ModelMap("pis", pis);

        return new ModelAndView("contact/contactlisting", model);
    }

    @RequestMapping(value = {"/contact/{contact}/project.htm", "/contact/{contact}/project.html"}, method = RequestMethod.GET)
    public ModelAndView projectsForContact(@PathVariable(value = "contact") String contact) throws Exception {

        ModelAndView modelAndView = new ModelAndView("cadis/browse/view-projects-for-contact");

        List<Dataset> datasetsForContact = null;

        datasetsForContact = this.responsiblePartyRepository.findDataForPI(contact);

        if (datasetsForContact.size() > 0) {

            modelAndView.addObject("datasets", datasetsForContact);
            modelAndView.addObject("contact", contact);
        } else {
            throw new ResponsiblePartyNotFoundException(contact);
        }

        return modelAndView;
    }
}

