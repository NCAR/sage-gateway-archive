package sgf.gateway.web.tags;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.service.security.impl.acegi.DatasetAccessDeniedException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AuthorizationUtilsAuthorizeWriteTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private Dataset dataset;

    public int doStartTag() throws JspException {

        ServletRequest request = pageContext.getRequest();
        WebApplicationContext webApplicationContext = RequestContextUtils.getWebApplicationContext(request, pageContext.getServletContext());

        AuthorizationUtils authorizationUtils = (AuthorizationUtils) webApplicationContext.getBean("authorizationUtils");

        int result = SKIP_BODY;

        try {

            authorizationUtils.authorizeForWrite(this.dataset);
            result = EVAL_BODY_INCLUDE;

        } catch (DatasetAccessDeniedException e) {

        }

        return result;
    }

    public void setDataset(Dataset dataset) {

        this.dataset = dataset;
    }
}
