package sgf.gateway.service.xml.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.modeling.ExperimentImpl;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.xml.api.RemoteMetadataService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@inheritDoc}
 */

public class RemoteMetadataServiceImpl implements RemoteMetadataService {

    /**
     * The underlying {@link MetadataService}, used to retrieve the domain model objects from the database.
     */
    private MetadataRepository metadataRepository;

    private DatasetRepository datasetRepository;

    private Configuration configuration;

    public RemoteMetadataServiceImpl(MetadataRepository metadataRepository, DatasetRepository datasetRepository, Configuration configuration) {

        this.metadataRepository = metadataRepository;
        this.datasetRepository = datasetRepository;
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */
    public String getDatasetHierarchy(String identifier) throws Exception {

        Dataset dataset = this.getDataset(identifier);

        Template template = configuration.getTemplate("dataset-hierarchy.ftl");

        Map<String, Object> templateMap = new HashMap<>();

        templateMap.put("dataset", dataset);

        Collection<Dataset> childDatasets = dataset.getDirectlyNestedDatasets();

        templateMap.put("childDatasets", childDatasets);

        String xml = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);

        return xml;
    }

    List<Dataset> getDatasetParentList(List<Dataset> parentList, Dataset dataset) {

        parentList.add(dataset);

        Dataset parentDataset = dataset.getParent();

        if (parentDataset != null) {

            this.getDatasetParentList(parentList, parentDataset);
        }

        return parentList;
    }

    /**
     * {@inheritDoc}
     */
    public String getDatasetMetadata(String identifier) throws Exception {

        Dataset dataset = this.getDataset(identifier);

        Template template = configuration.getTemplate("dataset-metadata.ftl");

        Map<String, Object> templateMap = new HashMap<>();

        templateMap.put("dataset", dataset);

        String description = dataset.getDescription();

        if (StringUtils.hasText(description)) {

            templateMap.put("description", description);
        }

        Collection<DataFormat> dataFormats = dataset.getDataFormats();

        if (!dataFormats.isEmpty()) {

            templateMap.put("dataFormats", dataFormats);
        }

        String xml = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);

        return xml;
    }

    /**
     * {@inheritDoc}
     */
    public String getDatasetFiles(String identifier) throws Exception {

        Dataset dataset = this.getDataset(identifier);

        Template template = configuration.getTemplate("dataset-files.ftl");

        Map<String, Object> templateMap = new HashMap<>();

        templateMap.put("dataset", dataset);

        Collection<LogicalFile> logicalFiles = dataset.getCurrentDatasetVersion().getLogicalFiles();

        if (!logicalFiles.isEmpty()) {

            templateMap.put("logicalFiles", logicalFiles);
        }

        String xml = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);

        return xml;
    }

    /**
     * Utility method to retrieve a Dataset from the database by its identifier.
     *
     * @param identifier
     * @return
     */
    private Dataset getDataset(String identifier) throws Exception {

        Dataset dataset = this.datasetRepository.getByShortName(identifier);

        if (dataset == null) {

            throw new Exception("Dataset with identifier=" + identifier + " not found");
        }

        return dataset;
    }

    /**
     * {@inheritDoc}
     */
    public String listExperiments() throws Exception {

        // retrieve all experiments
        List<ExperimentImpl> experiments = metadataRepository.findExperiments();

        Template template = configuration.getTemplate("list-experiments.ftl");

        Map<String, Object> templateMap = new HashMap<>();

        if (!experiments.isEmpty()) {

            templateMap.put("experiments", experiments);
        }

        String xml = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateMap);

        return xml;
    }

}
