<#ftl encoding='UTF-8'>
<#setting time_zone="GMT">
<?xml version="1.0" encoding="UTF-8"?>
<OAI-PMH xmlns="http://www.openarchives.org/OAI/2.0/"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/
         http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd">

  <responseDate>${request.responseDate?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</responseDate>
  <request verb="ListMetadataFormats">${repositoryUrl}</request>

  <ListMetadataFormats>

   <metadataFormat>
     <metadataPrefix>oai_dc</metadataPrefix>
     <schema>http://www.openarchives.org/OAI/2.0/oai_dc.xsd</schema>
     <metadataNamespace>http://www.openarchives.org/OAI/2.0/oai_dc/</metadataNamespace>
   </metadataFormat>

   <metadataFormat>
     <metadataPrefix>dif</metadataPrefix>
     <schema>http://gcmd.gsfc.nasa.gov/Aboutus/xml/dif/dif_v9.4.xsd</schema>
     <metadataNamespace>http://gcmd.gsfc.nasa.gov/Aboutus/xml/dif/</metadataNamespace>
   </metadataFormat>

   <metadataFormat>
     <metadataPrefix>ISO19139</metadataPrefix>
     <schema>http://www.isotc211.org/2005/gmd/gmd.xsd</schema>
     <metadataNamespace>http://www.isotc211.org/2005/gmd</metadataNamespace>
   </metadataFormat>

  </ListMetadataFormats>

</OAI-PMH>
