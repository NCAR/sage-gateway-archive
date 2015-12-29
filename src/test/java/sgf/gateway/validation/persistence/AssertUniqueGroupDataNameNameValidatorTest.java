package sgf.gateway.validation.persistence;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.GroupData;
import sgf.gateway.web.controllers.group.command.RegistrationFieldCommand;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssertUniqueGroupDataNameNameValidatorTest {

    private AssertUniqueGroupDataNameValidator validator;
    private GroupRepository mockGroupRepository;
    private RegistrationFieldCommand mockCommand;
    private GroupData mockGroupData;

    @Before
    public void setup() {

        this.mockGroupData = mock(GroupData.class);

        this.mockGroupRepository = mock(GroupRepository.class);

        this.validator = new AssertUniqueGroupDataNameValidator(this.mockGroupRepository);

        AssertUniqueGroupDataName mockAssertUniqueGroupDataName = mock(AssertUniqueGroupDataName.class);
        when(mockAssertUniqueGroupDataName.identifierField()).thenReturn("identifier");
        when(mockAssertUniqueGroupDataName.nameField()).thenReturn("name");

        this.validator.initialize(mockAssertUniqueGroupDataName);

        this.mockCommand = mock(RegistrationFieldCommand.class);
        when(mockCommand.getIdentifier()).thenReturn("a1dd18f0-4286-11e4-916c-0800200c9a66");
        when(mockCommand.getName()).thenReturn("group_data_type_name");
    }

    @Test
    public void assertTrueIfNoGroupDataFoundByName() {

        boolean valid = validator.isValid(this.mockCommand, null);

        assertThat(valid, is(equalTo(true)));
    }

    @Test
    public void assertTrueIfGroupDataFoundByNameAndUUIDsMatch() {

        when(this.mockGroupData.getIdentifier()).thenReturn(UUID.valueOf("a1dd18f0-4286-11e4-916c-0800200c9a66"));
        when(this.mockGroupRepository.findGroupDataByName("group_data_type_name")).thenReturn(this.mockGroupData);

        boolean valid = validator.isValid(this.mockCommand, null);

        assertThat(valid, is(equalTo(true)));
    }

    @Test
    public void assertFalseIfGroupDataFoundByName() {

        when(this.mockGroupRepository.findGroupDataByName("group_data_type_name")).thenReturn(this.mockGroupData);

        boolean valid = validator.isValid(this.mockCommand, null);

        assertThat(valid, is(equalTo(false)));
    }

    @Test
    public void assertFalseIfGroupDataFoundByNameAndUUIDsDoNotMatch() {

        when(this.mockGroupData.getIdentifier()).thenReturn(UUID.valueOf("bcf978a0-428f-11e4-916c-0800200c9a66"));
        when(this.mockGroupRepository.findGroupDataByName("group_data_type_name")).thenReturn(this.mockGroupData);

        boolean valid = validator.isValid(this.mockCommand, null);

        assertThat(valid, is(equalTo(false)));
    }
}
