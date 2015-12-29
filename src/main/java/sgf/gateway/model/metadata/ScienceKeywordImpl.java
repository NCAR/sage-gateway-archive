package sgf.gateway.model.metadata;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;

import java.util.Collection;
import java.util.HashSet;

public class ScienceKeywordImpl extends AbstractPersistableEntity implements ScienceKeyword {

    private String category;

    private String topic;

    private String term;

    private String variableLevel1;

    private String variableLevel2;

    private String variableLevel3;

    private String variableLevel4;

    private String displayText;

    private Collection<DescriptiveMetadata> metadataReference = new HashSet<DescriptiveMetadata>();

    /* For Hibernate */
    public ScienceKeywordImpl() {
        super(false); // don't generate UUID
    }

    @Override
    public String getCategory() {

        return this.category;
    }

    @Override
    public String getTopic() {

        return this.topic;
    }

    @Override
    public String getTerm() {

        return this.term;
    }

    @Override
    public String getVariableLevel1() {

        return this.variableLevel1;
    }

    @Override
    public String getVariableLevel2() {

        return this.variableLevel2;
    }

    @Override
    public String getVariableLevel3() {

        return this.variableLevel3;
    }

    @Override
    public String getVariableLevel4() {

        return this.variableLevel4;
    }

    @Override
    public String getDisplayText() {

        return this.displayText;
    }

    @Override
    public void setCategory(String category) {

        this.category = category;
    }

    @Override
    public void setTopic(String topic) {

        this.topic = topic;
    }

    @Override
    public void setTerm(String term) {

        this.term = term;
    }

    @Override
    public void setVariableLevel1(String variableLevel1) {

        this.variableLevel1 = variableLevel1;
    }

    @Override
    public void setVariableLevel2(String variableLevel2) {

        this.variableLevel2 = variableLevel2;
    }

    @Override
    public void setVariableLevel3(String variableLevel3) {

        this.variableLevel3 = variableLevel3;
    }

    @Override
    public void setVariableLevel4(String variableLevel4) {

        this.variableLevel4 = variableLevel4;
    }

    @Override
    public void setDisplayText(String displayText) {

        this.displayText = displayText;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<DescriptiveMetadata> getMetadataReference() {

        return UnmodifiableCollection.decorate(this.metadataReference);
    }

    @Override
    public void setMetadataReference(Collection<DescriptiveMetadata> descriptiveMetadata) {

        this.metadataReference = descriptiveMetadata;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(this.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this)))
                .append("identifier", this.getIdentifier())
                .append("category", this.getCategory())
                .append("topic", this.getTopic())
                .append("term", this.getTerm())
                .append("variableLevel1", this.getVariableLevel1())
                .append("variableLevel2", this.getVariableLevel2())
                .append("variableLevel3", this.getVariableLevel3())
                .append("variableLevel4", this.getVariableLevel4())
                .toString();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof ScienceKeywordImpl)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        ScienceKeywordImpl other = (ScienceKeywordImpl) object;

        return new EqualsBuilder()
                .append(this.getCategory(), other.category)
                .append(this.getTopic(), other.topic)
                .append(this.getTerm(), other.term)
                .append(this.getVariableLevel1(), other.variableLevel1)
                .append(this.getVariableLevel2(), other.variableLevel2)
                .append(this.getVariableLevel3(), other.variableLevel3)
                .append(this.getVariableLevel4(), other.variableLevel4)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getCategory())
                .append(this.getTopic())
                .append(this.getTerm())
                .append(this.getVariableLevel1())
                .append(this.getVariableLevel2())
                .append(this.getVariableLevel3())
                .append(this.getVariableLevel4())
                .toHashCode();
    }

}
