package sgf.gateway.web.controllers.topic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.ScienceKeyword;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ScienceKeywordTopicController {

    private final MetadataRepository metadataRepository;
    private final DatasetRepository datasetRepository;

    private ScienceKeywordTopicController(MetadataRepository metadataRepository, DatasetRepository datasetRepository) {

        this.metadataRepository = metadataRepository;
        this.datasetRepository = datasetRepository;
    }

    /**
     * Browse by scienceKeyword.topic (aka Discipline)
     */
    @RequestMapping(value = "/scienceKeywordTopic/{scienceKeyword}.html", method = RequestMethod.GET)
    public ModelAndView handleTopicRequest(@PathVariable(value = "scienceKeyword") final ScienceKeyword keyword) throws Exception {

        ModelAndView modelAndView = new ModelAndView("scienceKeywordTopic/topic");

        // Get datasets which have this DISCIPLINE (aka keyword.topic)
        //Collection<Dataset> datasets = keyword.getDatasets();
        Collection<Dataset> datasets = datasetRepository.findDatasetsByDiscipline(keyword);

        modelAndView.addObject("datasets", datasets);

        modelAndView.addObject("keyword", keyword);

        return modelAndView;
    }

    @RequestMapping(value = "/scienceKeywordTopic/topic.html", method = RequestMethod.GET)
    public ModelAndView getTopicList() throws Exception {

        // TODO: Use Set as these should be distinct
        List<ScienceKeyword> scienceKeywordTopics = this.metadataRepository.getScienceKeywordTopics();

        Map<String, Integer> topicAndCountMap = new TreeMap<String, Integer>();

        for (ScienceKeyword topic : scienceKeywordTopics) {

            Integer count = getTopicCount(topic);

            if (count != 0) {

                topicAndCountMap.put(topic.getDisplayText(), count);
            }
        }

        ModelMap model = new ModelMap("scienceKeywordTopics", topicAndCountMap);

        // TODO: Move this out of topic dir
        return new ModelAndView("scienceKeywordTopic/listTopics", model);
    }

    private Integer getTopicCount(ScienceKeyword keyword) {

        Integer count = this.datasetRepository.findDatasetCountByDiscipline(keyword);
        return count;
    }
}

