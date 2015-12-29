package sgf.gateway.search.extract.dataset;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class DatasetResponsiblePartiesFieldExtractor extends DatasetAbstractFieldExtractor {

    public DatasetResponsiblePartiesFieldExtractor() {
        super();
    }

    @Override
    protected Object getValue(Dataset dataset) {

        Collection<String> value = new ArrayList<String>();

        for (ResponsibleParty responsibleParty : this.getResponsibleParties(dataset)) {
            this.addResponsiblePartyNames(value, responsibleParty);
        }

        return value;
    }

    private Collection<ResponsibleParty> getResponsibleParties(Dataset dataset) {

        Collection<ResponsibleParty> parties = new HashSet<ResponsibleParty>();

        parties.addAll(dataset.getDescriptiveMetadata().getResponsibleParties());

        if (dataset.getParent() != null) {
            parties.addAll(this.getResponsibleParties(dataset.getParent()));
        }

        return parties;
    }

    private void addResponsiblePartyNames(Collection<String> value, ResponsibleParty responsibleParty) {
        this.addIfPopulated(value, responsibleParty.getIndividualName());
        this.addIfPopulated(value, responsibleParty.getOrganizationName());
        this.addIfPopulated(value, responsibleParty.getPositionName());
    }

    private void addIfPopulated(Collection<String> value, String name) {
        if (this.isPopulated(name)) {
            value.add(name);
        }
    }

    private Boolean isPopulated(String value) {
        return StringUtils.hasText(value);
    }
}
