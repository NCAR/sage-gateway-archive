<?xml version="1.0" encoding="UTF-8"?>
<esg:ESG xmlns:esg="http://www.earthsystemgrid.org/"
         schemaLocation="http://vets.development.ucar.edu/schema/remoteMetadata.xsd">

    <esg:dataset name="${dataset.title}" state="${dataset.publishedState.value?lower_case}"
                 source_catalog_uri="${dataset.currentDatasetVersion.authoritativeSourceURI}" id="${dataset.shortName}">
    <#if description??>
        <esg:description>${description}</esg:description>
    </#if>
    <#if dataFormats??>
        <#list dataFormats as dataFormat>
            <esg:data_format>${dataFormat.name}</esg:data_format>
        </#list>
    </#if>
    </esg:dataset>
</esg:ESG>

