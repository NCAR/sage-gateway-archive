package sgf.gateway.model.metadata;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUIDGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DatasetImplTest {

    Dataset parentDataset;
    Dataset datasetChild1;
    Dataset datasetChild2;
    Dataset datasetChild3;

    @Before
    public void setUp() {

        parentDataset = new DatasetImpl(UUIDGenerator.getInstance().generateRandomBasedUUID(), null, "P1", "P1", "test");

        datasetChild1 = new DatasetImpl(UUIDGenerator.getInstance().generateRandomBasedUUID(), null, "C1", "C1", "test");

        datasetChild2 = new DatasetImpl(UUIDGenerator.getInstance().generateRandomBasedUUID(), null, "C2", "C2", "test");

        datasetChild3 = new DatasetImpl(UUIDGenerator.getInstance().generateRandomBasedUUID(), null, "C3", "C3", "test");
    }


    @Test
    public void removeChildDatasetTest() {

        parentDataset.addChildDataset(datasetChild1);
        parentDataset.addChildDataset(datasetChild2);

        assertThat(2, is(parentDataset.getDirectlyNestedDatasets().size()));

        parentDataset.removeChildDataset(datasetChild1);

        assertThat(1, is(parentDataset.getDirectlyNestedDatasets().size()));

        assertThat(datasetChild2, is(parentDataset.getDirectlyNestedDatasets().iterator().next()));
    }


    @Test
    public void getDescendantDatasetsTest() {

        datasetChild2.addChildDataset(datasetChild3);
        parentDataset.addChildDataset(datasetChild1);
        parentDataset.addChildDataset(datasetChild2);

        assertThat(2, is(parentDataset.getDirectlyNestedDatasets().size()));
        assertThat(1, is(datasetChild2.getDirectlyNestedDatasets().size()));
        assertThat(0, is(datasetChild1.getDirectlyNestedDatasets().size()));

    }

}
