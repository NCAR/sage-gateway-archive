package sgf.gateway.model.metadata.descriptive;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

public class GCMDScienceKeywordTest {

    @Test
    public void testGCMDScienceKeywordEmptyString() {

        GCMDScienceKeyword scienceKeyword = new GCMDScienceKeyword("");

        assertThat(scienceKeyword.getTopic(), is(nullValue()));
        assertThat(scienceKeyword.getTerm(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel1(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel2(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel3(), is(nullValue()));
    }

    @Test
    public void testGCMDScienceKeywordTopic() {

        GCMDScienceKeyword scienceKeyword = new GCMDScienceKeyword("Cryosphere");

        assertThat(scienceKeyword.getTopic(), is(equalToIgnoringCase("Cryosphere")));
        assertThat(scienceKeyword.getTerm(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel1(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel2(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel3(), is(nullValue()));

    }

    @Test
    public void testGCMDScienceKeywordWithTopicTerm() {

        GCMDScienceKeyword scienceKeyword = new GCMDScienceKeyword("Cryosphere > Frozen Ground");

        assertThat(scienceKeyword.getTopic(), is(equalToIgnoringCase("Cryosphere")));
        assertThat(scienceKeyword.getTerm(), is(equalToIgnoringCase("Frozen Ground")));
        assertThat(scienceKeyword.getVariableLevel1(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel2(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel3(), is(nullValue()));

    }

    @Test
    public void testGCMDScienceKeywordWithTopicTerm1Variable() {

        GCMDScienceKeyword scienceKeyword = new GCMDScienceKeyword("Cryosphere > Frozen Ground > Active Layer");

        assertThat(scienceKeyword.getTopic(), is(equalToIgnoringCase("Cryosphere")));
        assertThat(scienceKeyword.getTerm(), is(equalToIgnoringCase("Frozen Ground")));
        assertThat(scienceKeyword.getVariableLevel1(), is(equalToIgnoringCase("Active Layer")));
        assertThat(scienceKeyword.getVariableLevel2(), is(nullValue()));
        assertThat(scienceKeyword.getVariableLevel3(), is(nullValue()));

    }

    @Test
    public void testGCMDScienceKeywordWithTopicTerm2Variables() {

        GCMDScienceKeyword scienceKeyword = new GCMDScienceKeyword("Cryosphere > Frozen Ground > Active Layer > Less Active Layer");

        assertThat(scienceKeyword.getTopic(), is(equalToIgnoringCase("Cryosphere")));
        assertThat(scienceKeyword.getTerm(), is(equalToIgnoringCase("Frozen Ground")));
        assertThat(scienceKeyword.getVariableLevel1(), is(equalToIgnoringCase("Active Layer")));
        assertThat(scienceKeyword.getVariableLevel2(), is(equalToIgnoringCase("Less Active Layer")));
        assertThat(scienceKeyword.getVariableLevel3(), is(nullValue()));
    }

    @Test
    public void testGCMDScienceKeywordWithTopicTerm3Variables() {

        GCMDScienceKeyword scienceKeyword = new GCMDScienceKeyword("Cryosphere > Frozen Ground > Active Layer > Less Active Layer > Really Inactive Layer");

        assertThat(scienceKeyword.getTopic(), is(equalToIgnoringCase("Cryosphere")));
        assertThat(scienceKeyword.getTerm(), is(equalToIgnoringCase("Frozen Ground")));
        assertThat(scienceKeyword.getVariableLevel1(), is(equalToIgnoringCase("Active Layer")));
        assertThat(scienceKeyword.getVariableLevel2(), is(equalToIgnoringCase("Less Active Layer")));
        assertThat(scienceKeyword.getVariableLevel3(), is(equalToIgnoringCase("Really Inactive Layer")));

    }

}
