package sgf.gateway.model.metadata.factory;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.Taxonomy;
import sgf.gateway.model.metadata.Topic;

public interface TopicFactory {

    Topic create(String name, Taxonomy type);

    Topic create(String name, Taxonomy type, Dataset dataset);
}
