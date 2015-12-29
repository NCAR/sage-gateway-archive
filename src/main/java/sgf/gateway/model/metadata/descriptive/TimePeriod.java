package sgf.gateway.model.metadata.descriptive;

import java.util.Date;

public interface TimePeriod {

    Date getBegin();

    void setBegin(Date begin);

    Date getEnd();

    void setEnd(Date end);

}