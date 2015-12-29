<#ftl encoding='UTF-8'>
<#setting time_zone="GMT">
<#import "oai-macros.ftl" as oai>
<#escape x as x?xml>
<?xml version="1.0" encoding="UTF-8"?>
<OAI-PMH xmlns="http://www.openarchives.org/OAI/2.0/" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/
         http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd">

  <responseDate>${request.responseDate?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</responseDate>
  <@oai.listRequestElement request repositoryUrl />

  <ListIdentifiers>

    <#list datasets as dataset>
    <@oai.datasetHeader gateway dataset />
    </#list>

    <resumptionToken><#if resumptionToken??>${resumptionToken}</#if></resumptionToken>
  </ListIdentifiers>

</OAI-PMH>

</#escape>
