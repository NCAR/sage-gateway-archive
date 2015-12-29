package sgf.gateway.web.controllers.search;

import com.kennardconsulting.core.net.UrlEncodedQueryString;

import java.net.URI;

public class ResultsPerPageOption {

    private int value;
    private URI searchURI;

    protected ResultsPerPageOption(String parameterName, int value, URI searchURI) {
        super();
        this.value = value;

        UrlEncodedQueryString queryString = UrlEncodedQueryString.parse(searchURI);

        queryString.remove("page");
        queryString.set(parameterName, value);

        this.searchURI = queryString.apply(searchURI);

    }

    public int getValue() {
        return value;
    }

    public URI getSearchURI() {
        return searchURI;
    }


}
