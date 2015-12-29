package sgf.gateway.model.metadata.builder.impl;

import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.TimeFrequency;
import sgf.gateway.model.metadata.builder.TimeFrequencyBuilder;
import sgf.gateway.model.metadata.factory.TimeFrequencyFactory;

public class TimeFrequencyBuilderImpl implements TimeFrequencyBuilder {

    private DatasetRepository datasetRepository;
    private TimeFrequencyFactory timeFrequencyFactory;
    private boolean allowCreate = true;

    public TimeFrequencyBuilderImpl(DatasetRepository datasetRepository, TimeFrequencyFactory timeFrequencyFactory, boolean allowCreate) {

        this.datasetRepository = datasetRepository;
        this.timeFrequencyFactory = timeFrequencyFactory;
        this.allowCreate = allowCreate;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.builder.TimeFrequencyBuilder#build(java.lang.String)
     */
    public synchronized TimeFrequency build(String timeFrequencyName) {

        String trimmedTimeFrequencyName = timeFrequencyName.trim();

        TimeFrequency timeFrequency = find(trimmedTimeFrequencyName);

        if (allowCreate && (null == timeFrequency)) {

            timeFrequency = create(trimmedTimeFrequencyName);
        }

        return timeFrequency;
    }

    protected TimeFrequency find(String timeFrequency) {

        TimeFrequency result;

        result = this.datasetRepository.findTimeFrequency(timeFrequency);

        return result;
    }

    protected TimeFrequency create(String timeFrequency) {

        TimeFrequency result = this.timeFrequencyFactory.create(timeFrequency, null);

        return result;
    }

}
