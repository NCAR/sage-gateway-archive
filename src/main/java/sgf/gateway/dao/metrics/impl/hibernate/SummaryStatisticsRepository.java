package sgf.gateway.dao.metrics.impl.hibernate;

import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.model.metrics.SummaryStatistics;

public class SummaryStatisticsRepository extends AbstractRepositoryImpl {

    public SummaryStatistics getLatestStatistics() {

        SummaryStatistics latestStatistics = (SummaryStatistics) super.findUniqueByCriteria();

        return latestStatistics;
    }

    @Override
    protected Class getEntityClass() {
        return SummaryStatistics.class;
    }
}
