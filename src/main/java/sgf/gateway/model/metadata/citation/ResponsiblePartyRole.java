package sgf.gateway.model.metadata.citation;

import java.io.Serializable;

public enum ResponsiblePartyRole implements Serializable {

    author("Author", "Party that authored the resource"),

    principalInvestigator("Principal Investigator", "Key party responsible for gathering information and conducting research"),

    collaboratingPrincipalInvestigator("Collaborating Principal Investigator", "A PI collaboration on the project from another institution and not directly funded by the project award"),

    coPrincipalInvestigator("CO Principal Investigator", "Another project PI, who is not the Lead PI"),

    resourceProvider("Resource Provider", "Party that supplies the resource"),

    custodian("Custodian", "Party that accepts accountability and responsibility for the data and ensures appropriate care and maintenance of the resource"),

    owner("Owner", "Party that owns the resource"),

    user("User", "Party that uses the resource"),

    distributor("Distributor", "Party that distributes the resource"),

    originator("Originator", "Party that created the resource"),

    pointOfContact("Point of Contact", "Party that can be contacted for acquiring knowledge about or acquisition of the resource"),

    processor("Processor", "Party that has processed the data in a manner such that the resource has been modified"),

    publisher("Publisher", "Party that published the resource");

    private String roleName;
    private String roleDescription;

    private ResponsiblePartyRole(String roleName, String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleDescription() {
        return this.roleDescription;
    }
}
