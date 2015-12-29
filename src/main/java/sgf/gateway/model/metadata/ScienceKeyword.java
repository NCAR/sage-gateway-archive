package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;

import java.util.Collection;

public interface ScienceKeyword {

    UUID getIdentifier();

    String getCategory();

    String getTopic();

    String getTerm();

    String getVariableLevel1();

    String getVariableLevel2();

    String getVariableLevel3();

    String getVariableLevel4();

    String getDisplayText();

    void setCategory(String category);

    void setTopic(String topic);

    void setTerm(String term);

    void setVariableLevel1(String variableLevel1);

    void setVariableLevel2(String variableLevel2);

    void setVariableLevel3(String variableLevel3);

    void setVariableLevel4(String variableLevel4);

    void setDisplayText(String displayText);

    Collection<DescriptiveMetadata> getMetadataReference();

    void setMetadataReference(Collection<DescriptiveMetadata> descriptiveMetadata);
}
