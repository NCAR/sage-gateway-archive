package sgf.gateway.web.controllers.search;

import org.hibernate.validator.constraints.NotBlank;

public class AnalyticalSearchCommand {

    @NotBlank(message = "A query is required.")
    String queryText;

    public void setQueryText(String queryText) {

        this.queryText = queryText;
    }

    public String getQueryText() {
        return queryText;
    }
}
