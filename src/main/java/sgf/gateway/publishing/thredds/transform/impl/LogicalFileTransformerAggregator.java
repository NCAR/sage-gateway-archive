package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.publishing.thredds.transform.ThreddsLogicalFileTransformer;
import thredds.catalog.InvDataset;

import java.util.List;

public class LogicalFileTransformerAggregator implements ThreddsLogicalFileTransformer {

    private final List<ThreddsLogicalFileTransformer> transformers;

    public LogicalFileTransformerAggregator(final List<ThreddsLogicalFileTransformer> transformers) {

        this.transformers = transformers;
    }

    @Override
    public void transform(InvDataset invDataset, LogicalFile logicalFile) {

        for (ThreddsLogicalFileTransformer transformer : this.transformers) {

            transformer.transform(invDataset, logicalFile);
        }
    }
}
