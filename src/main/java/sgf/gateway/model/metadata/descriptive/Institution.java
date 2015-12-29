package sgf.gateway.model.metadata.descriptive;

import org.hibernate.envers.Audited;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.AbstractPersistableEntity;

@Audited
public class Institution extends AbstractPersistableEntity {

    private String name;

    public Institution(String name) {
        super(true);
        this.name = name;
    }

    public Institution(UUID identifier, Integer version, String name) {
        super(identifier, version);
        this.setName(name);
    }

    protected Institution() {
        // required by Hibernate
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
