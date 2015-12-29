package sgf.gateway.model.metadata.inventory.builder.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.StandardName;
import sgf.gateway.model.metadata.inventory.Unit;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.metadata.inventory.builder.UnitBuilder;
import sgf.gateway.model.metadata.inventory.builder.VariableBuilder;
import sgf.gateway.model.metadata.inventory.factory.VariableFactory;

import java.util.Set;

public class VariableBuilderImpl implements VariableBuilder {

    private UnitBuilder unitBuilder;
    private VariableFactory variableFactory;

    public VariableBuilderImpl(VariableFactory variableFactory) {

        this.variableFactory = variableFactory;
    }

    public void setUnitBuilder(UnitBuilder unitBuilder) {

        this.unitBuilder = unitBuilder;
    }

    @Override
    public Variable build(DatasetVersion datasetVersion, String name, String description, String units) {

        // lookup existing variable in all dataset versions for this version's dataset
        Variable variable = null;

        // FIXME: this is likely oldest datasetversion to newest ordering. Reversing order may provide a faster hit on variable

        for (DatasetVersion siblingDatasetVersion : datasetVersion.getDataset().getDatasetVersions()) {

            Variable datasetVersionVariable = siblingDatasetVersion.findVariable(name);

            if (datasetVersionVariable != null) {

                variable = datasetVersionVariable;

                break;
            }
        }

        if ((variable == null) || unitsNotEqual(variable, units)) {

            // NOTE: We should be using thredds semantics here, namely, if variable is inherited, reuse
            // Note however that Thredds API does not specify if variable is inherited or not
            variable = this.variableFactory.create(datasetVersion, name, (Set<StandardName>) null);

            Unit unit = this.unitBuilder.build(units);

            if (unit != null) {

                variable.setUnits(unit);
            }

            // set description
            if (StringUtils.hasText(description)) {

                description = description.trim();

                variable.setDescription(description);
            }
        }

        return variable;
    }

    @Override
    public Variable build(LogicalFile logicalFile, String name, String description, String units) {

        Variable variable = null;

        for (DatasetVersion datasetVersion : logicalFile.getDataset().getDatasetVersions()) {

            Variable datasetVersionVariable = datasetVersion.findVariable(name);

            if (datasetVersionVariable != null) {

                variable = datasetVersionVariable;

                break;
            }
        }

        if (null == variable) {

            variable = logicalFile.findVariable(name);
        }

        if ((variable == null) || unitsNotEqual(variable, units)) {

            // NOTE: We should be using thredds semantics here, namely, if variable is inherited, reuse
            // Note however that Thredds API does not specify if variable is inherited or not.
            // FIXME: access DSV directly from LF?

            variable = this.variableFactory.create(logicalFile.getDataset().getCurrentDatasetVersion(), name, (Set<StandardName>) null);

            Unit unit = this.unitBuilder.build(units);

            if (null != unit) {

                variable.setUnits(unit);
            }

            // set description
            if (null != description) {

                description = description.trim();

                if (!"".equals(description)) {

                    variable.setDescription(description);
                }
            }
        }

        return variable;
    }

    private boolean unitsNotEqual(Variable variable, String symbol) {

        boolean unitsEqual;

        if ((symbol != null) && !"".equals(symbol)) {

            symbol = symbol.trim();
        }

        Unit existing = variable.getUnits();

        if (existing != null) {

            unitsEqual = existing.getSymbol().equalsIgnoreCase(symbol);
        } else if ((existing == null) && (symbol != null) && "1".equals(symbol)) {

            // NOTE: Units of "1" are considered NULL Units!!
            // TODO: Review this, should we be creating Units of "1"?
            unitsEqual = true;

        } else if (symbol != null) {

            unitsEqual = false; // existing is null, will always be false;
        } else {

            unitsEqual = true; // both null
        }

        return !unitsEqual;
    }
}
