<?xml version="1.0" encoding="UTF-8"?>
<#escape x as x?xml>
<datasetVersions xmlns="http://sgf.ucar.edu/schemas/datasetVersions" 
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
        xsi:schemaLocation="http://sgf.ucar.edu/schemas/datasetVersions http://sgf.ucar.edu/schemas/datasetVersions/1.0.0/datasetVersions.xsd"
        name="${dataset.title}"
        id="${dataset.shortName}" >

<#list datasetVersions as version>
	<version id="${version.versionIdentifier}" date="${dateFormat.format(version.dateCreated)}" comment="${version.comment!}" label="${version.label!}" state="${version.publishedState.value?lower_case}" sourceCatalog="${version.authoritativeSourceURI!}" />
</#list>

</datasetVersions>
</#escape>
