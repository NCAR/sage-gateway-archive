package sgf.gateway.search.provider.solr.query.configurer;

import org.springframework.util.StringUtils;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Operation;
import sgf.gateway.search.core.TemporalFilter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SolrQueryTemporalFilterBuilder extends SolrQueryFilterBuilderAbstract {

    private final String startDateFieldName;
    private final String endDateFieldName;

    public SolrQueryTemporalFilterBuilder(String startDateFieldName, String endDateFieldName) {
        this.startDateFieldName = startDateFieldName;
        this.endDateFieldName = endDateFieldName;
    }

    @Override
    public String build(Criteria criteria) {

        String filter = null;

        if (null != criteria.getTemporalFilter()) {
            filter = buildTemporalFilter(criteria.getTemporalFilter());
        }

        return filter;
    }

    private String buildTemporalFilter(TemporalFilter temporalFilter) {

        String filter;

        if (this.isBoundedByStartAndEndDate(temporalFilter)) {

            filter = this.buildTemporalFilterBoundedByStartAndEndDate(temporalFilter);

        } else {

            if (this.isBoundedByStartDate(temporalFilter)) {

                filter = this.buildTemporalFilterBoundedByStartDateOnly(temporalFilter);

            } else {

                filter = this.buildTemporalFilterBoundedByEndDateOnly(temporalFilter);

            }
        }

        return filter;
    }

    private Boolean isBoundedByStartAndEndDate(TemporalFilter temporalFilter) {
        return this.isBoundedByStartDate(temporalFilter) && this.isBoundedByEndDate(temporalFilter);
    }

    private Boolean isBoundedByStartDate(TemporalFilter temporalFilter) {
        return StringUtils.hasText(temporalFilter.getStartDate());
    }

    private Boolean isBoundedByEndDate(TemporalFilter temporalFilter) {
        return StringUtils.hasText(temporalFilter.getEndDate());
    }

    private String buildTemporalFilterBoundedByStartAndEndDate(TemporalFilter temporalFilter) {

        String upperBoundOnStartDate = this.transformFormat(temporalFilter.getEndDate(), temporalFilter.getDateFormat());
        String lowerBoundOnEndDate = this.transformFormat(temporalFilter.getStartDate(), temporalFilter.getDateFormat());

        String filter = this.formulateStartDateConstraint(upperBoundOnStartDate) + " " + Operation.AND + " " + this.formulateEndDateConstraint(lowerBoundOnEndDate);

        return filter;
    }

    private String buildTemporalFilterBoundedByStartDateOnly(TemporalFilter temporalFilter) {

        String lowerBoundOnEndDate = this.transformFormat(temporalFilter.getStartDate(), temporalFilter.getDateFormat());

        String filter = this.formulateEndDateConstraint(lowerBoundOnEndDate);

        return filter;
    }

    private String buildTemporalFilterBoundedByEndDateOnly(TemporalFilter temporalFilter) {

        String upperBoundOnStartDate = this.transformFormat(temporalFilter.getEndDate(), temporalFilter.getDateFormat());

        String filter = this.formulateStartDateConstraint(upperBoundOnStartDate);

        return filter;
    }

    private String formulateStartDateConstraint(String upperBoundOnStartDate) {
        // note, curly backets are exclusive
        return this.startDateFieldName + ":{* TO " + upperBoundOnStartDate + "}";
    }

    private String formulateEndDateConstraint(String lowerBoundOnEndDate) {
        // note, curly backets are exclusive
        return this.endDateFieldName + ":{" + lowerBoundOnEndDate + " TO *}";
    }

    private String transformFormat(String date, DateFormat dateFormat) {

        String transformedDate;

        // use full ISO-8601 date-time in GMT for values
        DateFormat queryDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            Date searchParameterDate = dateFormat.parse(date);
            transformedDate = queryDateFormat.format(searchParameterDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return transformedDate;
    }
}
