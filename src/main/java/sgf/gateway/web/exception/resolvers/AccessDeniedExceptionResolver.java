package sgf.gateway.web.exception.resolvers;

import org.springframework.core.Ordered;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.service.security.impl.acegi.DatasetAccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessDeniedExceptionResolver implements HandlerExceptionResolver, Ordered {

    private int order;
    private RuntimeUserService runtimeUserService;

    public void setRuntimeUserService(RuntimeUserService runtimeUserService) {
        this.runtimeUserService = runtimeUserService;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Method overridden to pass through AccessDeniedExceptions, since these must be intercepted by the Spring Security ExceptionTranslationFilter to establish
     * the proper workflows.
     */
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {

        ModelAndView result = null;

        if (this.isAccessDeniedException(exception)) {

            if (this.runtimeUserService.isUserAuthenticated()) {

                DatasetAccessDeniedException datasetAccessDeniedException = (DatasetAccessDeniedException) exception;

                ModelMap model = new ModelMap();
                model.addAttribute("datasetId", datasetAccessDeniedException.getDataset().getIdentifier().toString());
                model.addAttribute("operation", datasetAccessDeniedException.getOperation().toString());

                View redirectView = new RedirectView("/ac/accessDenied.html", true);

                result = new ModelAndView(redirectView, model);
            }
        }

        return result;
    }

    boolean isAccessDeniedException(Exception exception) {

        boolean isAccessDeniedException = false;

        if (exception instanceof DatasetAccessDeniedException) {
            isAccessDeniedException = true;
        }

        return isAccessDeniedException;
    }
}
