<#setting url_escaping_charset="UTF-8">
<#escape x as x?xml>
        <oai_dc:dc xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/" xmlns:dc="http://purl.org/dc/elements/1.1/" xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd">
          <dc:identifier>${dataset.identifier}</dc:identifier>
          <dc:title>${dataset.title}</dc:title>
          <dc:description>${dataset.description!}</dc:description>
          <dc:date>${dataset.dateUpdated?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</dc:date>
        </oai_dc:dc>
</#escape>