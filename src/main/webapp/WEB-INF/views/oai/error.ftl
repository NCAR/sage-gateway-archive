<#ftl encoding='UTF-8'>
<#setting time_zone="GMT">
<#escape x as x?xml>
<?xml version="1.0" encoding="UTF-8"?>
<OAI-PMH xmlns="http://www.openarchives.org/OAI/2.0/" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/
         http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd">

  <responseDate>${responseDate?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</responseDate>
  <request>${repositoryUrl}</request>

  <#if errorCode??>
     <error code="${errorCode?default("")}">
        ${errorMessage?default("")}
     </error>
  </#if>
  <#if errorList??>
    <#list errorList as failure>
        <error code="${failure.code}">
          ${failure.defaultMessage}
        </error>
     </#list>
  </#if>

</OAI-PMH>
</#escape>