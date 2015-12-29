package sgf.gateway.utils.propertyeditors;

import org.safehaus.uuid.UUID;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.TopicRepository;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.service.metadata.TopicNotFoundException;

import java.beans.PropertyEditorSupport;

public class TopicPropertyEditor extends PropertyEditorSupport {

    private final TopicRepository topicRepository;

    public TopicPropertyEditor(TopicRepository topicRepository) {

        this.topicRepository = topicRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasText(text)) {

            UUID uuid = UUID.valueOf(text);

            Topic topic = this.topicRepository.get(uuid);

            if (topic == null) {
                throw new TopicNotFoundException(text);
            }

            setValue(topic);

        } else {

            setValue(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        Topic value = (Topic) super.getValue();

        String result = "";

        if (value != null) {

            result = value.getIdentifier().toString();
        }

        return result;
    }
}
