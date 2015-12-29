package sgf.gateway.validation.persistence;

import org.apache.commons.beanutils.BeanUtils;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.Group;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;


public class AssertUniqueGroupNameValidator implements ConstraintValidator<AssertUniqueGroupName, Object> {

    protected final transient GroupRepository groupRepository;

    private String groupNameField;

    public AssertUniqueGroupNameValidator(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void initialize(AssertUniqueGroupName annotation) {

        this.groupNameField = annotation.groupNameField();

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = false;

        try {

            String groupName = BeanUtils.getProperty(object, this.groupNameField);

            Group group = groupRepository.findGroupByNameIgnoreCase(groupName);

            if (group == null) {

                valid = true;

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
