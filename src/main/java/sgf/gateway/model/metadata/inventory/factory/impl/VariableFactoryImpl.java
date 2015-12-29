package sgf.gateway.model.metadata.inventory.factory.impl;

import sgf.gateway.dao.IdentifierGenerator;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.metadata.inventory.VariableImpl;
import sgf.gateway.model.metadata.inventory.factory.VariableFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class VariableFactoryImpl implements VariableFactory {

    private IdentifierGenerator identifierGenerator;

    /**
     * @param identifierGenerator
     */
    public VariableFactoryImpl(IdentifierGenerator identifierGenerator) {
        super();
        this.identifierGenerator = identifierGenerator;
    }

    /**
     * {@inheritDoc}
     */
    public Variable create(DatasetVersion datasetVersion, String name, StandardName standardName) {

        Set<StandardName> standardNames = new HashSet<StandardName>();

        if (null != standardName) {

            standardNames.add(standardName);
        }

        return this.create(datasetVersion, name, standardNames);
    }

    /**
     * {@inheritDoc}
     */
    public Variable create(DatasetVersion datasetVersion, String name, Set<StandardName> standardNames) {

        Serializable identifier = this.identifierGenerator.generateNewIdentifier();

        VariableImpl variableImpl = new VariableImpl(identifier, datasetVersion, name, standardNames);

        return variableImpl;
    }

}
