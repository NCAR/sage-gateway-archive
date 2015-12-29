package sgf.gateway.publishing.thredds.transform.impl;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;
import sgf.gateway.model.metadata.builder.TopicBuilder;
import sgf.gateway.publishing.thredds.transform.ThreddsDatasetTransformer;
import thredds.catalog.InvDataset;

import java.util.HashMap;

public class DatasetRealmTransformer implements ThreddsDatasetTransformer {

    private TopicBuilder topicBuilder;
    private HashMap<String, String> propertyMap;

    public DatasetRealmTransformer(TopicBuilder topicBuilder) {

        this.topicBuilder = topicBuilder;

        propertyMap = new HashMap<String, String>();
        initPropertyMap();
    }

    protected void initPropertyMap() {

        propertyMap.put("atm", "atmos");
        propertyMap.put("atmosphere", "atmos");
        propertyMap.put("lnd", "land");
        propertyMap.put("ocn", "ocean");
    }

    @Override
    public void transform(InvDataset invDataset, Dataset dataset) {

        Topic realm = findRealm(invDataset);

        if (null != realm) {
            dataset.addTopic(realm);
        }
    }


    Topic findRealm(InvDataset invDataset) {

        Topic result = null;

        String topicString = invDataset.findProperty("realm");

        String trimmedName;

        if (null != topicString) {

            trimmedName = topicString.trim();
            trimmedName = mapProperty(trimmedName);
            result = this.topicBuilder.build(trimmedName, Taxonomy.REALM);
        }

        return result;

    }

    protected String mapProperty(String value) {

        String result = propertyMap.get(value.toLowerCase());

        if (null == result) {

            result = value;
        }

        return result;
    }

}
