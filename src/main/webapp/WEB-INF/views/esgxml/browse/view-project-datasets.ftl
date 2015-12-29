<?xml version="1.0" encoding="UTF-8"?>
<#escape x as x?xml>
<projectDatasetList xmlns="http://sgf.ucar.edu/schemas/projectDatasetList" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://sgf.ucar.edu/schemas/projectDatasetList http://sgf.ucar.edu/schemas/projectDatasetList/1.0.0/projectDatasetList.xsd"
	name="${project.name}">
			
<#list project.associatedDatasets as dataset>
 	<dataset name="${dataset.title}" state="${dataset.currentDatasetVersion.publishedState.value?lower_case}" source_catalog_uri="${dataset.currentDatasetVersion.authoritativeSourceURI!}" id="${dataset.shortName}" />
</#list>

</projectDatasetList>
</#escape>
