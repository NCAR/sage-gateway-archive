package sgf.gateway.model.metadata.builder;

import sgf.gateway.model.metadata.DataProductType;

public interface DataProductTypeBuilder {

    DataProductType build(String name);

    DataProductType build(String name, String description);

}
