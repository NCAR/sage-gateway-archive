package sgf.gateway.model.metadata.inventory.builder.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.inventory.Unit;
import sgf.gateway.model.metadata.inventory.builder.UnitBuilder;
import sgf.gateway.model.metadata.inventory.factory.UnitFactory;

/**
 * Builder class providing basic lookup/creation of Unit instances. Provides a mechanism to configure a UnitFactory derived from the "root" unitFactory set by
 * the constructor for use by unit lookup/creation.
 */
public class UnitBuilderImpl implements UnitBuilder {

    private DatasetRepository datasetRepository = null;
    private UnitFactory unitFactory = null;

    public UnitBuilderImpl(DatasetRepository datasetRepository, UnitFactory unitFactory) {

        this.datasetRepository = datasetRepository;
        this.unitFactory = unitFactory;
    }

    /**
     * {@inheritDoc}
     */
    public Unit build(String symbol) {

        Unit unit = null;

        // NOTE: Units of "1" are considered NULL Units!!
        // TODO: Review this, should we be creating Units of "1" ?

        if (StringUtils.hasText(symbol) && !"1".equals(symbol)) {

            String trimmedSymbol = symbol.trim();

            unit = this.datasetRepository.findUnit(trimmedSymbol);

            if (unit == null) {

                unit = unitFactory.create(trimmedSymbol);
            }
        }

        return unit;
    }
}
