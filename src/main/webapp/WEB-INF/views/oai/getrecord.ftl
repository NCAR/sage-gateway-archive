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
  <request verb="GetRecord" metadataPrefix="${request.metadataPrefix?default("")}">${repositoryUrl}</request>

  <GetRecord>

    <record>
      <@oai.datasetHeader gateway dataset />
      <metadata>
        <#include "metadata/${request.metadataPrefix}.ftl" />
      </metadata>
    </record>

  </GetRecord>

</OAI-PMH>

</#escape>