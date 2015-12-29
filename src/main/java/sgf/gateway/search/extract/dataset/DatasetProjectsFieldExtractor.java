package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.ActivityType;
import sgf.gateway.model.metadata.activities.project.Project;

import java.util.ArrayList;
import java.util.Collection;

public class DatasetProjectsFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetProjectsFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        Project project = (Project) dataset.getActivity(ActivityType.PROJECT);

        if (null != project) {
            value.add(project.getName());
        }

        return value;
    }
}
