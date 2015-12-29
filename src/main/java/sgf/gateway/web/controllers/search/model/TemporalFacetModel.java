package sgf.gateway.web.controllers.search.model;

import com.kennardconsulting.core.net.UrlEncodedQueryString;
import org.springframework.util.StringUtils;

import java.net.URI;

public class TemporalFacetModel {

    private final String startDate;
    private final String endDate;

    private URI searchPath;

    public TemporalFacetModel(final URI searchPath, final String startDate, final String endDate) {

        this.searchPath = searchPath;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getRemovalUrl() {

        UrlEncodedQueryString queryString = UrlEncodedQueryString.parse(this.searchPath);

        queryString.remove("startDate");
        queryString.remove("endDate");

        // Need a better way to do this!, since this reruns search all pagination information should be reset
        queryString.remove("page");

        String url = queryString.apply(this.searchPath).toString();

        return url;
    }

    public String getStartDate() {

        return startDate;
    }

    public boolean isStartDateEmpty() {

        boolean empty = !StringUtils.hasText(this.startDate);

        return empty;
    }

    public String getEndDate() {

        return endDate;
    }

    public boolean isEndDateEmpty() {

        boolean empty = !StringUtils.hasText(this.endDate);

        return empty;
    }
}
