<#ftl encoding='UTF-8'>
<#setting time_zone="GMT">
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<?xml version="1.0" encoding="UTF-8"?>
<OAI-PMH xmlns="http://www.openarchives.org/OAI/2.0/" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/
         http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd">

  <responseDate>${request.responseDate?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</responseDate>
  <request verb="Identify">${repositoryUrl}</request>

  <Identify>
    <repositoryName>${gateway.name} Open Archive Initiative Repository</repositoryName>
    <baseURL>${repositoryUrl}</baseURL>
    <protocolVersion>2.0</protocolVersion>
    <adminEmail>${supportEmail}</adminEmail>
    <earliestDatestamp>${earliestTimestamp?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</earliestDatestamp>
    <deletedRecord>no</deletedRecord>
    <granularity>YYYY-MM-DDThh:mm:ssZ</granularity>
 </Identify>

</OAI-PMH>
