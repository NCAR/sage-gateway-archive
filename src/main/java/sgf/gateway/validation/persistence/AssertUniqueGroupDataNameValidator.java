package sgf.gateway.validation.persistence;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.GroupData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class AssertUniqueGroupDataNameValidator implements ConstraintValidator<AssertUniqueGroupDataName, Object> {

    private final GroupRepository groupRepository;

    private String identifierField;
    private String nameField;

    public AssertUniqueGroupDataNameValidator(GroupRepository groupRepository) {

        this.groupRepository = groupRepository;
    }

    @Override
    public void initialize(AssertUniqueGroupDataName assertUniqueGroupDataName) {

        this.identifierField = assertUniqueGroupDataName.identifierField();
        this.nameField = assertUniqueGroupDataName.nameField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        try {

            String groupDataName = BeanUtils.getProperty(object, this.nameField);

            GroupData groupData = this.groupRepository.findGroupDataByName(groupDataName);

            if (groupData == null) {

                valid = true;

            } else {

                String identifierAsString = BeanUtils.getProperty(object, this.identifierField);

                if (StringUtils.isNotBlank(identifierAsString)) {

                    UUID identifier = UUID.valueOf(identifierAsString);

                    if (identifier.equals(groupData.getIdentifier())) {

                        valid = true;
                    }
                }
            }
        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);

        } catch (InvocationTargetException e) {

            throw new RuntimeException(e);

        } catch (NoSuchMethodException e) {

            throw new RuntimeException(e);
        }

        return valid;
    }
}
