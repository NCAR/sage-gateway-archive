<?xml version="1.0" encoding="UTF-8"?>
<esg:ESG xmlns:esg="http://www.earthsystemgrid.org/"
         schemaLocation="http://vets.development.ucar.edu/schema/remoteMetadata.xsd">

    <esg:dataset name="${dataset.title}" state="${dataset.publishedState.value?lower_case}"
                 source_catalog_uri="${dataset.currentDatasetVersion.authoritativeSourceURI}" id="${dataset.shortName}">
    <#list childDatasets as childDataset>
        <esg:dataset name="${childDataset.title}" state="${childDataset.publishedState.value?lower_case}"
                     source_catalog_uri="${childDataset.currentDatasetVersion.authoritativeSourceURI}"
                     id="${childDataset.shortName}"/>
    </#list>
    </esg:dataset>

</esg:ESG>
