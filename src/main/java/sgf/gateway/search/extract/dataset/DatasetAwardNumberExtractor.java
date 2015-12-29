package sgf.gateway.search.extract.dataset;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Award;

import java.util.ArrayList;
import java.util.List;

public class DatasetAwardNumberExtractor extends DatasetAbstractFieldExtractor {

    @Override
    protected Object getValue(Dataset dataset) {

        return getAwards(dataset);
    }

    private List<String> getAwards(Dataset dataset) {

        List<String> awardList = new ArrayList<String>();

        for (Award award : dataset.getAwards()) {
            awardList.add(award.getAwardNumber());
        }

        if (dataset.getParent() != null) {

            awardList.addAll(this.getAwards(dataset.getParent()));
        }

        return awardList;
    }
}
