package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.model.metadata.activities.project.builder.ProjectBuilder;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

public class DatasetProjectTransformer implements ThreddsDatasetTransformer {

    public static final String THREDDS_PROPERTY_NAME = "project";

    private ProjectBuilder projectBuilder;

    public DatasetProjectTransformer(ProjectBuilder projectBuilder) {
        this.projectBuilder = projectBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        Project project = getDatasetProject(invDataset, dataset);

        if (project != null) {
            dataset.associateActivity(project);
        }
    }

    private Project getDatasetProject(InvDataset invDataset, Dataset dataset) {

        Project project = null;

        String projectString = invDataset.findProperty(THREDDS_PROPERTY_NAME); // ESG "project" property

        if (StringUtils.hasText(projectString)) {
            project = this.projectBuilder.build(projectString);
        }

        return project;
    }
}
