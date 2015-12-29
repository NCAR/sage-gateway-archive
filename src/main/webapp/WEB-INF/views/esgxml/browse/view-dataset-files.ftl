<?xml version="1.0" encoding="UTF-8"?>
<#escape x as x?xml>
<dataset xmlns="http://sgf.ucar.edu/schemas/dataset" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://sgf.ucar.edu/schemas/dataset http://sgf.ucar.edu/schemas/dataset/1.0.0/dataset.xsd"
	name="${command.dataset.title}"
	state="${command.datasetVersion.publishedState.value?lower_case}"
	source_catalog_uri="${command.datasetVersion.authoritativeSourceURI!}"
	id="${command.dataset.shortName}"> 

<#--data_access_capability-->
<#list command.sortedDataAccessCapabilities as dac>
  <data_access_capability name="${dac.name}" type="${dac.type.name}" base_uri="${dac.baseURI}" /> 
</#list>
   
<#--files -->
  <files>
  
  <#list command.sortedLogicalFiles as file>
    <file name="${file.name}" id="${file.versionIdentifier}" size="${file.size?c}" >
	<#list file.checksums as checksum>
	  <checksum algorithm="${checksum.algorithm}" value="${checksum.checksum}" /> 
	</#list>
	<#list command.getSortedFileAccessPoints(file) as accessPoint>
	  <#-- Only display the access point if it's not a simple file reference -->
	  <#if accessPoint.accessURI.scheme != "file">
	  	<file_access_point data_access_capability="${accessPoint.accessURI.scheme}" uri="${command.getRelativeFileAccessPointURI(accessPoint)}" /> 
	  </#if>
	</#list>
    </file>
    
  </#list>
  </files>
 
</dataset>

</#escape>
