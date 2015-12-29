package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.StandardNameType;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.metadata.inventory.builder.VariableBuilder;
import sgf.gateway.model.metadata.inventory.factory.StandardNameFactory;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetVersionTransformer;
import thredds.catalog.InvDataset;
import thredds.catalog.ThreddsMetadata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatasetVersionVariableTransformer implements ThreddsDatasetVersionTransformer {

    private DatasetRepository datasetRepository;
    private VariableBuilder variableBuilder;
    private StandardNameFactory standardNameFactory;

    public DatasetVersionVariableTransformer(DatasetRepository datasetRepository, VariableBuilder variableBuilder, StandardNameFactory standardNameFactory) {

        this.datasetRepository = datasetRepository;
        this.standardNameFactory = standardNameFactory;
        this.variableBuilder = variableBuilder;
    }

    @Override
    public void transform(InvDataset invDataset, DatasetVersion datasetVersion) {

        Set<Variable> removableVariables = new HashSet<Variable>(datasetVersion.getVariables());

        if (!invDataset.getVariables().isEmpty()) {

            List<ThreddsMetadata.Variables> invVariables = invDataset.getVariables();

            for (ThreddsMetadata.Variables variables : invVariables) {

                for (ThreddsMetadata.Variable invVariable : variables.getVariableList()) {

                    Variable variable = this.findOrCreateVariable(datasetVersion, invVariable);

                    datasetVersion.addVariable(variable);

                    removableVariables.remove(variable);
                }
            }
        }

        for (Variable variable : removableVariables) {

            datasetVersion.removeVariable(variable);
        }
    }

    private Variable findOrCreateVariable(DatasetVersion datasetVersion, ThreddsMetadata.Variable threddsVariable) {

        // lookup existing variable in all dataset versions for this version's dataset
        Variable variable = variableBuilder.build(datasetVersion, threddsVariable.getName(), threddsVariable.getDescription(), threddsVariable.getUnits());

        // lookup standard name and add to set
        if (variable != null) {

            StandardName standardName = findOrCreateStandardName(threddsVariable);

            if (standardName != null) {

                variable.addStandardName(standardName);
            }
        }

        return variable;
    }

    private StandardName findOrCreateStandardName(ThreddsMetadata.Variable threddsVariable) {

        StandardName standardName = null;

        String vocabularyName = threddsVariable.getVocabularyName();

        if (StringUtils.hasText(vocabularyName)) {

            vocabularyName = vocabularyName.trim();

            StandardNameType standardNameType = StandardNameType.CF;

            standardName = datasetRepository.findStandardName(vocabularyName, standardNameType);

            if (null == standardName) {

                standardName = this.standardNameFactory.create(vocabularyName, standardNameType);
            }
        }

        return standardName;
    }
}
