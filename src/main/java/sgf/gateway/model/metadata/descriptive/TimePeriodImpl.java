package sgf.gateway.model.metadata.descriptive;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import sgf.gateway.audit.Auditable;

import java.io.Serializable;
import java.util.Date;

@Audited
public class TimePeriodImpl implements TimePeriod, Auditable {

    private Serializable identifier;

    @NotAudited
    private DescriptiveMetadata parent;

    private Date begin;
    private Date end;

    private TimePeriodImpl() {
        super();
    }

    public TimePeriodImpl(DescriptiveMetadata parent) {
        super();
        this.parent = parent;
    }

    @Override
    public Date getBegin() {
        return begin;
    }

    @Override
    public void setBegin(Date begin) {
        this.begin = begin;
    }

    @Override
    public Date getEnd() {
        return end;
    }

    @Override
    public void setEnd(Date end) {
        this.end = end;
    }

}
