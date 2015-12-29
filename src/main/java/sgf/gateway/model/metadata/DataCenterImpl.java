package sgf.gateway.model.metadata;

import org.safehaus.uuid.UUID;

public class DataCenterImpl implements DataCenter {

    private UUID identifier;
    private String shortName;
    private String longName;
    private String url;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private Integer version;  /* Hibernate version. */

    /**
     * No argument constructor for Hibernate.
     */
    public DataCenterImpl() {

        super();
    }

    @Override
    public UUID getIdentifier() {
        return identifier;
    }

    @Override
    public String getShortName() {

        return this.shortName;
    }

    @Override
    public String getLongName() {

        return this.longName;
    }

    @Override
    public String getURL() {

        return this.url;
    }

    @Override
    public String getFirstName() {

        return this.firstName;
    }

    @Override
    public String getLastName() {

        return this.lastName;
    }

    @Override
    public String getAddress1() {

        return this.address1;
    }

    @Override
    public String getAddress2() {

        return this.address2;
    }

    @Override
    public String getAddress3() {

        return this.address3;
    }

    @Override
    public String getCity() {

        return this.city;
    }

    @Override
    public String getState() {

        return this.state;
    }

    @Override
    public String getPostalCode() {

        return this.postalCode;
    }

    @Override
    public String getCountry() {

        return this.country;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName() + " id=" + getIdentifier() + " name=" + getShortName());
        return sb.toString();
    }

}
