<?xml version="1.0" encoding="UTF-8"?>
<#escape x as x?xml>
<datasetList xmlns="http://sgf.ucar.edu/schemas/datasetList" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://sgf.ucar.edu/schemas/datasetList http://sgf.ucar.edu/schemas/datasetList/1.0.0/datasetList.xsd">
			
<#list collections as collection>
 	<dataset name="${collection.name}" state="${collection.currentDatasetVersion.publishedState.value?lower_case}" source_catalog_uri="${collection.currentDatasetVersion.authoritativeSourceURI!}" id="${collection.shortName}" />
</#list>

</datasetList>
</#escape>
