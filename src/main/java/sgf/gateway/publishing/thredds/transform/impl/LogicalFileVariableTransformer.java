package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.StandardNameType;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.metadata.inventory.builder.VariableBuilder;
import sgf.gateway.model.metadata.inventory.factory.StandardNameFactory;
import sgf.gateway.publishing.thredds.transform.ThreddsLogicalFileTransformer;
import thredds.catalog.InvDataset;
import thredds.catalog.ThreddsMetadata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicalFileVariableTransformer implements ThreddsLogicalFileTransformer {

    private DatasetRepository datasetRepository;
    private VariableBuilder variableBuilder;
    private StandardNameFactory standardNameFactory;

    public LogicalFileVariableTransformer(DatasetRepository datasetRepository, VariableBuilder variableBuilder, StandardNameFactory standardNameFactory) {

        this.datasetRepository = datasetRepository;
        this.standardNameFactory = standardNameFactory;
        this.variableBuilder = variableBuilder;
    }

    @Override
    public void transform(InvDataset invDatasetFile, LogicalFile logicalFile) {

        Set<Variable> removableVariables = new HashSet<Variable>(logicalFile.getVariables());

        List<ThreddsMetadata.Variables> invVariables = invDatasetFile.getVariables();

        for (ThreddsMetadata.Variables variables : invVariables) {

            for (ThreddsMetadata.Variable invVariable : variables.getVariableList()) {

                Variable variable = this.findOrCreateVariable(logicalFile, invVariable);

                logicalFile.addVariable(variable);

                removableVariables.remove(variable);
            }
        }

        for (Variable variable : removableVariables) {

            logicalFile.removeVariable(variable);
        }
    }

    private Variable findOrCreateVariable(LogicalFile logicalFile, ThreddsMetadata.Variable threddsVariable) {

        // lookup existing variable in all dataset versions for this version's dataset
        Variable variable = variableBuilder.build(logicalFile, threddsVariable.getName(), threddsVariable.getDescription(), threddsVariable.getUnits());

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
