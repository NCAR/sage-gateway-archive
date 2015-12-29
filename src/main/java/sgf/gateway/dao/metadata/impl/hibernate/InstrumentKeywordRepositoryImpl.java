package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.criterion.Restrictions;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.InstrumentKeywordRepository;
import sgf.gateway.model.metadata.activities.observing.InstrumentKeyword;

import java.io.Serializable;
import java.util.List;

public class InstrumentKeywordRepositoryImpl extends AbstractRepositoryImpl<InstrumentKeyword, Serializable> implements InstrumentKeywordRepository {

    @Override
    protected Class<InstrumentKeyword> getEntityClass() {
        return InstrumentKeyword.class;
    }

    public InstrumentKeyword findByShortName(String shortName) {
        return super.findUniqueByCriteria(Restrictions.eq("shortName", shortName).ignoreCase());
    }

    @Override
    public List<InstrumentKeyword> getAll() {

        return super.getAllOrdered("displayText");
    }

}
