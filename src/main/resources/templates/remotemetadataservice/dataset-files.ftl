<?xml version="1.0" encoding="UTF-8"?>
<#escape x as x?xml>
<esg:ESG xmlns:esg="http://www.earthsystemgrid.org/"
         schemaLocation="http://vets.development.ucar.edu/schema/remoteMetadata.xsd">

    <esg:dataset name="${dataset.title}" state="${dataset.publishedState.value?lower_case}"
                 source_catalog_uri="${dataset.currentDatasetVersion.authoritativeSourceURI}" id="${dataset.shortName}">
        <#if logicalFiles??>
            <esg:files>
                <#list logicalFiles as logicalFile>
                    <esg:file name="${logicalFile.name}" id="${logicalFile.versionIdentifier}"
                              size="${logicalFile.size?c}"/>
                </#list>
            </esg:files>
        </#if>
    </esg:dataset>
</esg:ESG>
</#escape>
