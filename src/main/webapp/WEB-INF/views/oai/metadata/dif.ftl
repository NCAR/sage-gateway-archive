<#setting url_escaping_charset="UTF-8">
<#escape x as x?xml>
        <DIF xmlns="http://gcmd.gsfc.nasa.gov/Aboutus/xml/dif/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://gcmd.gsfc.nasa.gov/Aboutus/xml/dif/ http://gcmd.gsfc.nasa.gov/Aboutus/xml/dif/dif_v9.8.4.xsd">
          <Entry_ID>${dataset.identifier}</Entry_ID>
          <Entry_Title>${dataset.title}</Entry_Title>

          <#list dataset.descriptiveMetadata.scienceKeywords as scienceKeyword>
          <Parameters>
            <Category>${scienceKeyword.category}</Category>
            <Topic>${scienceKeyword.topic}</Topic>
            <#if scienceKeyword.term??>
            <Term>${scienceKeyword.term}</Term>
            </#if>
            <#if scienceKeyword.variableLevel1??>
            <Variable_Level_1>${scienceKeyword.variableLevel1}</Variable_Level_1>
            </#if>
            <#if scienceKeyword.variableLevel2??>
            <Variable_Level_2>${scienceKeyword.variableLevel2}</Variable_Level_2>
            </#if>
            <#if scienceKeyword.variableLevel3??>
            <Variable_Level_3>${scienceKeyword.variableLevel3}</Variable_Level_3>
            </#if>
          </Parameters>
          </#list>

          <#if dataset.descriptiveMetadata.timePeriod??>
          <#assign timePeriod = dataset.descriptiveMetadata.timePeriod />
          <Temporal_Coverage>
            <#if timePeriod.begin??>
            <Start_Date>${timePeriod.begin?string("yyyy-MM-dd")}</Start_Date>
            </#if>
            <#if timePeriod.end??>
            <Stop_Date>${timePeriod.end?string("yyyy-MM-dd")}</Stop_Date>
            </#if>
          </Temporal_Coverage>
          </#if>

          <#if dataset.descriptiveMetadata.geographicBoundingBox??>
          <#assign geographicBoundingBox = dataset.descriptiveMetadata.geographicBoundingBox />
          <Spatial_Coverage>
            <Southernmost_Latitude>${geographicBoundingBox.southBoundLatitude}</Southernmost_Latitude>
            <Northernmost_Latitude>${geographicBoundingBox.northBoundLatitude}</Northernmost_Latitude>
            <Westernmost_Longitude>${geographicBoundingBox.westBoundLongitude}</Westernmost_Longitude>
            <Easternmost_Longitude>${geographicBoundingBox.eastBoundLongitude}</Easternmost_Longitude>
          </Spatial_Coverage>
          </#if>

          <Data_Center>
            <Data_Center_Name>
              <Short_Name>${dataset.dataCenter.shortName}</Short_Name>
              <Long_Name>${dataset.dataCenter.longName}</Long_Name>
            </Data_Center_Name>
            <Data_Center_URL>${dataset.dataCenter.URL}</Data_Center_URL>
            <Personnel>
              <Role>DATA CENTER CONTACT</Role>
              <#if dataset.dataCenter.firstName??>
              <First_Name>${dataset.dataCenter.firstName}</First_Name>
              </#if>
              <Last_Name>${dataset.dataCenter.lastName}</Last_Name>
              <Contact_Address>
                <Address>${dataset.dataCenter.address1}</Address>
                <#if dataset.dataCenter.address2??>
                <Address>${dataset.dataCenter.address2}</Address>
                </#if>
                <#if dataset.dataCenter.address3??>
                <Address>${dataset.dataCenter.address3}</Address>
                </#if>
                <City>${dataset.dataCenter.city}</City>
                <Province_or_State>${dataset.dataCenter.state}</Province_or_State>
                <Postal_Code>${dataset.dataCenter.postalCode}</Postal_Code>
                <Country>${dataset.dataCenter.country}</Country>
              </Contact_Address>
            </Personnel>
          </Data_Center>
          <Summary>
            <Abstract>${dataset.description!'N/A'}</Abstract>
          </Summary>
          <Related_URL>
            <URL_Content_Type>
              <Type>GET DATA</Type>
            </URL_Content_Type>
            <URL>${gateway.baseURL}dataset/id/${dataset.identifier?url}.html</URL>
            <Description>Data Center top-level access page for this resource</Description>
          </Related_URL>

          <Metadata_Name>ACADIS IDN DIF</Metadata_Name>
          <Metadata_Version>9.8.4</Metadata_Version>
          <Last_DIF_Revision_Date>${dataset.dateUpdated?string("yyyy-MM-dd")}</Last_DIF_Revision_Date>
        </DIF>
</#escape>