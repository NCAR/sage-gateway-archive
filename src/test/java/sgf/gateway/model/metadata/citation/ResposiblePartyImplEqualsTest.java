package sgf.gateway.model.metadata.citation;

import org.junit.Test;
import sgf.gateway.model.ModelEqualsMethodTest;

public class ResposiblePartyImplEqualsTest extends ModelEqualsMethodTest {

    public Object createObjectUnderTest() {

        return new ResponsiblePartyImpl();
    }

    @Test
    public void testIndividualName() {

        testEqualContractForAttribute("individualName", "thisIndividualName", "otherIndividualName");
    }

    @Test
    public void testOrganizationName() {

        testEqualContractForAttribute("organizationName", "thisOrganizationName", "otherOrganizationName");
    }

    @Test
    public void testPositionName() {

        testEqualContractForAttribute("positionName", "thisPositionName", "otherPositionName");
    }

    @Test
    public void testEmail() {

        testEqualContractForAttribute("email", "thisEmail", "otherEmail");
    }

    @Test
    public void testRole() {

        testEqualContractForAttribute("role", ResponsiblePartyRole.author, ResponsiblePartyRole.principalInvestigator);
    }
}
