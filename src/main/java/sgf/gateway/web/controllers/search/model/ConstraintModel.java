package sgf.gateway.web.controllers.search.model;

import com.kennardconsulting.core.net.UrlEncodedQueryString;
import sgf.gateway.search.api.Constraint;
import sgf.gateway.search.api.Facet;

import java.net.URI;
import java.util.List;

public class ConstraintModel {

    private URI searchPath;
    private Facet facet;
    private Constraint constraint;

    public ConstraintModel(URI searchPath, Facet facet, Constraint constraint) {

        this.searchPath = searchPath;
        this.facet = facet;
        this.constraint = constraint;
    }

    public String getName() {
        return this.constraint.getName();
    }

    public Long getCount() {
        return this.constraint.getCount();
    }

    public String getRemovalUrl() {

        UrlEncodedQueryString queryString = UrlEncodedQueryString.parse(this.searchPath);

        List<String> values = queryString.getValues(this.facet.getName());

        if (null != values) {
            values.remove(this.constraint.getName());
        }

        // Need a better way to do this!, since this reruns search all pagination information should be reset
        queryString.remove("page");

        String url = queryString.apply(this.searchPath).toString();

        return url;
    }

    public String getAddUrl() {

        UrlEncodedQueryString queryString = UrlEncodedQueryString.parse(this.searchPath);

        queryString.append(this.facet.getName(), this.constraint.getName());

        // Need a better way to do this!, since this reruns search all pagination information should be reset
        queryString.remove("page");

        String url = queryString.apply(this.searchPath).toString();

        return url;
    }
}
