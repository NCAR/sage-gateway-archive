package sgf.gateway.web.controllers.cadis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.ScienceKeyword;

import java.util.List;

@Controller
public class HomePageController {

    private final MetadataRepository metadataRepository;

    public HomePageController(MetadataRepository metadataRepository) {

        this.metadataRepository = metadataRepository;
    }

    @RequestMapping(value = {"/home.htm", "/home.html"}, method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {

        ModelAndView modelAndView = new ModelAndView("cadis/home");

        List<ScienceKeyword> topics = this.metadataRepository.getUsedScienceKeywordTopics();
        modelAndView.addObject("topics", topics);

        return modelAndView;
    }

}
