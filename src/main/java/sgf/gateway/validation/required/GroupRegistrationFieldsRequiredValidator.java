package sgf.gateway.validation.required;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.GroupData;
import sgf.gateway.web.controllers.security.GroupRegistrationCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Set;

public class GroupRegistrationFieldsRequiredValidator implements ConstraintValidator<GroupRegistrationFieldsRequired, Object> {

    private final GroupRepository groupRepository;

    public GroupRegistrationFieldsRequiredValidator(final GroupRepository groupRepository) {

        this.groupRepository = groupRepository;
    }

    @Override
    public void initialize(GroupRegistrationFieldsRequired arg0) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;

        GroupRegistrationCommand command = (GroupRegistrationCommand) object;

        Group group = this.groupRepository.getGroup(command.getGroupIdentifier());

        Map<GroupData, Boolean> groupDataMap = group.getGroupData();

        Set<GroupData> groupDataKeys = groupDataMap.keySet();

        for (GroupData groupData : groupDataKeys) {

            Boolean isRequired = groupDataMap.get(groupData);

            if (isRequired) {

                String value = command.getGroupData().get(groupData.getIdentifier());

                if (!StringUtils.hasText(value)) {

                    valid = false;

                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate(groupData.getName() + " is required.").addConstraintViolation();
                }
            }
        }

        return valid;
    }

}
