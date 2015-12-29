package sgf.gateway.model.metadata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Simple class to store links and a descriptive name.
 */
public class BroadcastMessageImpl implements BroadcastMessage {

    private Long identifier;

    private String messageText;
    private String bannerColor;

    /*
     * For Hibernate.
     */
    protected BroadcastMessageImpl() {
    }

    /**
     * Primary constructor for the object.
     *
     * @param messageText the text which will be displayed in a banner on all pages
     */
    public BroadcastMessageImpl(final String messageText) {
        super();
        this.messageText = messageText;

    }

    /*
     * Alternate constructor specifying color
     */
    public BroadcastMessageImpl(String messageText, String bannerColor) {
        super();
        this.messageText = messageText;
        this.bannerColor = bannerColor;
    }

    public String getMessageText() {

        return this.messageText;
    }

    public void setMessageText(String text) {
        this.messageText = text;
    }

    public String getBannerColor() {
        return bannerColor;
    }

    public void setBannerColor(String bannerColor) {
        this.bannerColor = bannerColor;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(messageText);
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BroadcastMessageImpl other = (BroadcastMessageImpl) obj;
        return new EqualsBuilder().append(this.getMessageText(), other.getMessageText()).isEquals();
    }

}
