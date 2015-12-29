<#macro datasetHeader gateway dataset>
      <header <#if dataset.retracted || dataset.deleted>status="deleted"</#if> >
        <identifier>oai:${gateway.name}:${dataset.identifier}</identifier>
        <datestamp>${dataset.dateUpdated?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</datestamp>
        <setSpec>${dataset.dataCenter.identifier}</setSpec>
      </header>
</#macro>

<#macro listRequestElement request repositoryUrl>
  <request verb="${request.verb}" <#if request.from??>from="${request.from?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}}"</#if> <#if request.until??>until="${request.until?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}"</#if> <#if request.set??>set="${request.set}"</#if> metadataPrefix="${request.metadataPrefix}">
    ${repositoryUrl}
  </request>
</#macro>