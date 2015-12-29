package sgf.gateway.publishing.thredds.transform.impl;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.publishing.thredds.transform.ThreddsLogicalFileTransformer;
import thredds.catalog.InvDataset;

public class LogicalFileChecksumTransformer implements ThreddsLogicalFileTransformer {

    @Override
    public void transform(InvDataset invDatasetFile, LogicalFile logicalFile) {

        logicalFile.removeAllChecksums();

        String checksumAlgorithm = this.getChecksumAlgorithm(invDatasetFile);

        String checksumValue = this.getChecksumValue(invDatasetFile);

        if (StringUtils.hasText(checksumAlgorithm) && StringUtils.hasText(checksumValue)) {

            logicalFile.addChecksum(checksumAlgorithm, checksumValue);
        }
    }

    protected String getChecksumAlgorithm(InvDataset invDatasetFile) {

        String checksumType = null;

        if (invDatasetFile.getProperties() != null) {

            checksumType = invDatasetFile.findProperty("checksum_type");
        }

        return checksumType;
    }

    protected String getChecksumValue(InvDataset invDatasetFile) {

        String checksumValue = null;

        if (invDatasetFile.getProperties() != null) {

            checksumValue = invDatasetFile.findProperty("checksum");
        }

        return checksumValue;
    }
}
