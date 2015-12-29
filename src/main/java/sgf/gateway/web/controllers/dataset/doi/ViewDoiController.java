package sgf.gateway.web.controllers.dataset.doi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.Dataset;

@Controller
public class ViewDoiController {

    @RequestMapping(value = "/dataset/{dataset}/doi.html", method = RequestMethod.GET)
    public ModelAndView viewDoi(@PathVariable(value = "dataset") Dataset dataset) {

        ModelAndView modelAndView = new ModelAndView("/dataset/doi/viewDoi");

        return modelAndView;
    }
}
