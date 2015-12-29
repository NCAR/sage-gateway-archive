package sgf.gateway.web.controllers.webstart;

import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.xrds.XrdsServiceEndpoint;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.discovery.yadis.YadisResult;
import org.openid4java.util.HttpCache;
import org.openid4java.util.HttpFetcherFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyProxyLogonWebStartController extends AbstractController {

    private String viewName;
    private RuntimeUserService runtimeUserService;

    public MyProxyLogonWebStartController(RuntimeUserService runtimeUserService) {

        this.runtimeUserService = runtimeUserService;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String codebase = getCodebase(request);

        response.setContentType("application/x-java-jnlp-file");

        response.addHeader("Content-Disposition", "Inline; filename=myproxy-logon.jnlp");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, private");

        Map<String, String> model = new HashMap<>();

        model.put("codebase", codebase);

        URI myProxyEndpoint = this.tryMyproxyEndPoint();

        model.put("host", myProxyEndpoint.getHost());
        model.put("port", Integer.toString(myProxyEndpoint.getPort()));
        model.put("user", this.runtimeUserService.getUser().getUserName());

        return new ModelAndView(viewName, model);
    }

    private String getCodebase(HttpServletRequest request) {

        StringBuilder codebase = new StringBuilder();

        codebase.append(!request.isSecure() ? "http://" : "https://");
        codebase.append(request.getServerName());

        if (request.getServerPort() != (!request.isSecure() ? 80 : 443)) {
            codebase.append(':');
            codebase.append(request.getServerPort());
        }

        codebase.append('/');

        return codebase.toString();
    }

    protected URI tryMyproxyEndPoint() {

        User user = this.runtimeUserService.getUser();

        String openid = user.getOpenid();

        URI uri;

        try {

            uri = this.getMyproxyEndPoint(openid);

        } catch (Exception e) {

            throw new RuntimeException("Unable to retrieve yadis document for openid: " + openid, e);
        }

        return uri;
    }

    protected URI getMyproxyEndPoint(String openid) throws DiscoveryException {

        YadisResolver resolver = new YadisResolver(new HttpFetcherFactory());

        Set<String> serviceTypes = new HashSet<>();

        serviceTypes.add("urn:esg:security:myproxy-service");

        YadisResult yadisResult = resolver.discover(openid, 10, new HttpCache(), serviceTypes);

        XrdsServiceEndpoint endpoint = (XrdsServiceEndpoint) yadisResult.getEndpoints().get(0);
        URI result = URI.create(endpoint.getUri());

        return result;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
