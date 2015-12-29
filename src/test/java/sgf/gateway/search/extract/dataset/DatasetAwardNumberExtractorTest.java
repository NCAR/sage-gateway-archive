package sgf.gateway.search.extract.dataset;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Award;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatasetAwardNumberExtractorTest {

    private Dataset mockDataset;
    DatasetAwardNumberExtractor extractor;

    @Before
    public void setup() {

        this.mockDataset = mock(Dataset.class);
        this.extractor = new DatasetAwardNumberExtractor();
    }

    @Test
    public void getAwardNumbersFromDataset() {

        List<Award> awardList = this.createAwardList("123", "456", "789");
        when(this.mockDataset.getAwards()).thenReturn(awardList);

        List<String> awardNumbers = (List<String>) this.extractor.getValue(this.mockDataset);

        assertThat(awardNumbers, hasItems("123", "456", "789"));
    }

    private List<Award> createAwardList(String... awards) {

        List<Award> awardList = new ArrayList<Award>();

        for (String string : awards) {
            awardList.add(this.createMockAward(string));
        }

        return awardList;
    }

    private Award createMockAward(String awardNumber) {

        Award mockAward = mock(Award.class);
        when(mockAward.getAwardNumber()).thenReturn(awardNumber);

        return mockAward;
    }

    @Test
    public void datasetAwardListIsEmpty() {

        List<Award> awardList = this.createAwardList();
        when(this.mockDataset.getAwards()).thenReturn(awardList);

        List<String> awardNumbers = (List<String>) this.extractor.getValue(this.mockDataset);

        assertThat(awardNumbers, is(empty()));
    }

    @Test
    public void getAwardsFromDatasetParents() {

        Dataset mockDatasetParent1 = this.createMockDatasetWithMockAwardNumbers("456");
        when(this.mockDataset.getParent()).thenReturn(mockDatasetParent1);

        Dataset mockDatasetParent2 = this.createMockDatasetWithMockAwardNumbers("123");
        when(mockDatasetParent1.getParent()).thenReturn(mockDatasetParent2);

        List<String> awardNumbers = (List<String>) this.extractor.getValue(this.mockDataset);

        assertThat(awardNumbers, hasItems("456", "123"));
    }

    private Dataset createMockDatasetWithMockAwardNumbers(String... awards) {

        Dataset dataset = mock(Dataset.class);
        List<Award> awardList = this.createAwardList(awards);
        when(dataset.getAwards()).thenReturn(awardList);

        return dataset;
    }
}
