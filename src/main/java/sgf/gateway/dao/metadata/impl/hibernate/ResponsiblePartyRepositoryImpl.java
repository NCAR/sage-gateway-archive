package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.ResponsiblePartyRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetImpl;
import sgf.gateway.model.metadata.citation.PrincipalInvestigatorWithData;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.service.metadata.ObjectNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResponsiblePartyRepositoryImpl extends AbstractRepositoryImpl<ResponsibleParty, Serializable> implements ResponsiblePartyRepository {

    @Override
    protected Class<ResponsibleParty> getEntityClass() {
        return ResponsibleParty.class;
    }

    @Override
    public ResponsibleParty get(UUID projectIdentity) {

        ResponsibleParty result = super.get(projectIdentity);

        if (null == result) {
            throw new ObjectNotFoundException(projectIdentity);
        }

        return result;
    }

    @Override
    public List<ResponsibleParty> getAll() {
        return super.getAllOrdered("individualName");
    }

    @Override
    public List<PrincipalInvestigatorWithData> findAllPIsWithData() {

        Query query = this.getSession().getNamedQuery("findAllPIsWithData").setResultTransformer(Transformers.aliasToBean(PrincipalInvestigatorWithData.class));

        List<PrincipalInvestigatorWithData> pisWithData = query.list();

        return pisWithData;
    }

    @Override
    public List<PrincipalInvestigatorWithData> findAllResponsiblePartiesOrdered() {

        Query query = this.getSession().getNamedQuery("findAllResponsiblePartiesOrdered").setResultTransformer(Transformers.aliasToBean(PrincipalInvestigatorWithData.class));

        List<PrincipalInvestigatorWithData> responsibleParties = query.list();

        return responsibleParties;
    }

    @Override
    public List<Dataset> findDataForPI(String contact) {

        Criteria criteria = this.getSession().createCriteria(DatasetImpl.class, "dataset");

        criteria = criteria.createCriteria("descriptiveMetadata", "descriptiveMetadata");

        criteria = criteria.createCriteria("responsibleParties", "responsibleParty");

        Collection<ResponsiblePartyRole> roles = new ArrayList<>();
        roles.add(ResponsiblePartyRole.principalInvestigator);
        roles.add(ResponsiblePartyRole.coPrincipalInvestigator);
        roles.add(ResponsiblePartyRole.collaboratingPrincipalInvestigator);

        criteria = criteria.add(Restrictions.in("responsibleParty.role", roles));
        criteria = criteria.add(Restrictions.eq("responsibleParty.individualName", contact));
        criteria = criteria.addOrder(Order.asc("dataset.title"));

        List<Dataset> datasets = criteria.list();
        return datasets;
    }
}