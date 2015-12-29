package sgf.gateway.dao.metadata;

import sgf.gateway.dao.Repository;
import sgf.gateway.model.metadata.activities.observing.InstrumentKeyword;

import java.io.Serializable;
import java.util.List;

public interface InstrumentKeywordRepository extends Repository<InstrumentKeyword, Serializable> {

    /**
     * Find InstrumentKeyword by name, ignoring case.
     */
    InstrumentKeyword findByShortName(String shortName);

    /**
     * Get all InstrumentKeywords, ordered by "shortName" property.
     */
    List<InstrumentKeyword> getAll();
}
