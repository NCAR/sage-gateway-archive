package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.ThreddsDescriptiveMetadataTransformer;
import thredds.catalog.InvDataset;
import ucar.nc2.units.DateRange;
import ucar.nc2.units.DateType;

import java.util.Date;

public class DatasetTimeCoverageTransformer implements ThreddsDescriptiveMetadataTransformer {


    @Override
    public void transform(InvDataset invDataset, DescriptiveMetadata descriptiveMetadata) {

        DateRange invDateRange = invDataset.getTimeCoverage();

        if (invDateRange != null) {

            Date startDate = getStart(invDateRange);
            Date endDate = getEnd(invDateRange);

            descriptiveMetadata.setTimePeriod(startDate, endDate);
        }
    }

    protected Date getEnd(DateRange timeCoverage) {

        Date result;
        DateType end = null;

        if (timeCoverage != null) {

            if (timeCoverage.useEnd()) {
                end = timeCoverage.getEnd();

            } else if (timeCoverage.useStart() && timeCoverage.useDuration()) {
                end = timeCoverage.getStart().add(timeCoverage.getDuration());
            }
        }

        if ((null == end) || end.isBlank()) {
            result = null;

        } else {
            result = end.getDate();
        }

        return result;
    }

    protected Date getStart(DateRange timeCoverage) {

        Date result;
        DateType start = null;

        if (timeCoverage.useStart()) {
            start = timeCoverage.getStart();

        } else if (timeCoverage.useEnd() && timeCoverage.useDuration()) {
            start = timeCoverage.getEnd().subtract(timeCoverage.getDuration());
        }

        if ((null == start) || start.isBlank()) {
            result = null;

        } else {
            result = start.getDate();
        }

        return result;
    }

}
