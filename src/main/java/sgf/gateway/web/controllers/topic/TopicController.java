package sgf.gateway.web.controllers.topic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Topic;

import java.util.Collection;

@Controller
public class TopicController {

    private String view;
    private TopicListStrategy topicListStrategy;

    private TopicController(String view, TopicListStrategy topicListStrategy) {

        this.view = view;
        this.topicListStrategy = topicListStrategy;
    }

    @RequestMapping(value = "/topic/{topic}", method = RequestMethod.GET)
    public ModelAndView handleRequest(@PathVariable(value = "topic") Topic topic) throws Exception {

        ModelAndView modelAndView = new ModelAndView(this.view);

        Collection<Dataset> datasets = topic.getDatasets();
        modelAndView.addObject("datasets", datasets);

        modelAndView.addObject("topic", topic);

        return modelAndView;
    }

    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    public ModelAndView topics() throws Exception {

        ModelMap model = new ModelMap("topics", this.topicListStrategy.getTopics());

        return new ModelAndView("topic/topiclisting", model);
    }
}

