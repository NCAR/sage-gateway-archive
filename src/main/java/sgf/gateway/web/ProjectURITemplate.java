package sgf.gateway.web;

import org.springframework.web.util.UriTemplate;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Project;

import java.net.URI;

public class ProjectURITemplate {

    private UriTemplate template;

    public ProjectURITemplate(String uriTemplate) {

        super();
        this.template = new UriTemplate(uriTemplate);

    }

    public URI expand(final Dataset dataset) {

        URI result = this.template.expand(dataset.getShortName());

        return result;

    }

    public URI expand(final Project project) {

        URI result = this.template.expand(project.getPersistentIdentifier());

        return result;

    }
}
