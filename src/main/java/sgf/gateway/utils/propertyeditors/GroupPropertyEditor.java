package sgf.gateway.utils.propertyeditors;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.service.metadata.ObjectNotFoundException;

import java.beans.PropertyEditorSupport;


public class GroupPropertyEditor extends PropertyEditorSupport {

    private final GroupRepository groupRepository;

    public GroupPropertyEditor(GroupRepository groupRepository) {

        this.groupRepository = groupRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasText(text)) {

            Group group = this.groupRepository.findGroupByName(text);

            if (group == null) {

                throw new ObjectNotFoundException(text);
            }

            setValue(group);

        } else {

            setValue(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        Group value = (Group) super.getValue();

        String result = "";

        if (value != null) {

            result = value.getName();
        }

        return result;
    }
}
