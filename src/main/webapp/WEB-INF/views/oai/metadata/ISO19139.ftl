<#escape x as x?xml>

<gmd:MD_Metadata xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xmlns:gco="http://www.isotc211.org/2005/gco"
                 xmlns:gmd="http://www.isotc211.org/2005/gmd"
                 xmlns:gml="http://www.opengis.net/gml/3.2"
                 xmlns:gmx="http://www.isotc211.org/2005/gmx"
                 xmlns:xlink="http://www.w3.org/1999/xlink"
                 xsi:schemaLocation="http://www.isotc211.org/2005/gco http://www.isotc211.org/2005/gco/gco.xsd
                        http://www.isotc211.org/2005/gmd http://www.isotc211.org/2005/gmd/gmd.xsd
                        http://www.isotc211.org/2005/gmx http://www.isotc211.org/2005/gmx/gmx.xsd">

    <gmd:fileIdentifier>
        <gco:CharacterString>urn:x-wmo:md:${reversedHostName}::${dataset.identifier}</gco:CharacterString>
    </gmd:fileIdentifier>

    <gmd:language>
        <gco:CharacterString>eng</gco:CharacterString>
    </gmd:language>

    <gmd:characterSet>
        <gmd:MD_CharacterSetCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_CharacterSetCode" codeListValue="utf8" codeSpace="004">utf8</gmd:MD_CharacterSetCode>
    </gmd:characterSet>

    <#if dataset.parent?has_content>
    <gmd:parentIdentifier>
        <gco:CharacterString>urn:x-wmo:md:${reversedHostName}::${dataset.parent.identifier}</gco:CharacterString>
    </gmd:parentIdentifier>
    </#if>

    <gmd:hierarchyLevel>
        <gmd:MD_ScopeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_ScopeCode" codeListValue="dataset" codeSpace="005">dataset</gmd:MD_ScopeCode>
    </gmd:hierarchyLevel>

    <gmd:contact>
        <gmd:CI_ResponsibleParty>
            <gmd:organisationName>
                <gco:CharacterString>${gatewayName}</gco:CharacterString>
            </gmd:organisationName>
            <gmd:role>
                <gmd:CI_RoleCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_RoleCode" codeListValue="resourceProvider">resourceProvider</gmd:CI_RoleCode>
            </gmd:role>
        </gmd:CI_ResponsibleParty>
    </gmd:contact>

    <#if dataset.dateCreated?has_content>
    <gmd:dateStamp>
        <gco:DateTime>${dataset.dateCreated?string("yyyy-MM-dd'T'HH:mm:ss'Z'")}</gco:DateTime>
    </gmd:dateStamp>
    </#if>

    <gmd:metadataStandardName>
        <gco:CharacterString>ISO 19115 Geographic information - Metadata</gco:CharacterString>
    </gmd:metadataStandardName>

    <gmd:metadataStandardVersion>
        <gco:CharacterString>ISO 19115:2003(E)</gco:CharacterString>
    </gmd:metadataStandardVersion>

    <gmd:dataSetURI>
        <gco:CharacterString>${gateway.baseSecureURL}dataset/id/${dataset.identifier}.iso19139</gco:CharacterString>
    </gmd:dataSetURI>

    <gmd:identificationInfo>
        <gmd:MD_DataIdentification>
            <gmd:citation>
                <gmd:CI_Citation>
                    <gmd:title>
                        <gco:CharacterString>${dataset.title}</gco:CharacterString>
                    </gmd:title>
                    <gmd:date>
                        <gmd:CI_Date>
                            <gmd:date>
                                <gco:Date>${dataset.dateCreated?string("yyyy-MM-dd")}</gco:Date>
                            </gmd:date>
                            <gmd:dateType>
                                <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="creation">creation</gmd:CI_DateTypeCode>
                            </gmd:dateType>
                        </gmd:CI_Date>
                    </gmd:date>
                    <gmd:date>
                        <gmd:CI_Date>
                            <gmd:date>
                                <gco:Date>${dataset.dateUpdated?string("yyyy-MM-dd")}</gco:Date>
                            </gmd:date>
                            <gmd:dateType>
                                <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="modified">modified</gmd:CI_DateTypeCode>
                            </gmd:dateType>
                        </gmd:CI_Date>
                    </gmd:date>
                    <#if dataset.DOI?has_content>
                    <gmd:identifier>
                        <gmd:MD_Identifier>
                            <gmd:code>
                                <gmx:Anchor xlink:href="http://dx.doi.org/${dataset.DOI}" xlink:title="DOI" xlink:actuate="onRequest">${dataset.DOI}</gmx:Anchor>
                            </gmd:code>
                        </gmd:MD_Identifier>
                    </gmd:identifier>
                    </#if>
                    <#if dataset.descriptiveMetadata.responsibleParties?has_content>
                    <#list dataset.descriptiveMetadata.responsibleParties as responsibleParty>
                    <gmd:citedResponsibleParty>
                        <gmd:CI_ResponsibleParty>
                            <#if responsibleParty.individualName?has_content>
                            <gmd:individualName>
                                <gco:CharacterString>${responsibleParty.individualName}</gco:CharacterString>
                            </gmd:individualName>
                            </#if>
                            <#if responsibleParty.organizationName?has_content>
                            <gmd:organisationName>
                                <gco:CharacterString>${responsibleParty.organizationName}</gco:CharacterString>
                            </gmd:organisationName>
                            </#if>
                            <#if responsibleParty.email?has_content>
                            <gmd:contactInfo>
                                <gmd:CI_Contact>
                                    <gmd:address>
                                        <gmd:CI_Address>
                                            <gmd:electronicMailAddress>
                                                <gco:CharacterString>${responsibleParty.email}</gco:CharacterString>
                                            </gmd:electronicMailAddress>
                                        </gmd:CI_Address>
                                    </gmd:address>
                                </gmd:CI_Contact>
                            </gmd:contactInfo>
                            </#if>
                            <gmd:role>
                                <gmd:CI_RoleCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_RoleCode" codeListValue="${responsibleParty.role}">${responsibleParty.role}</gmd:CI_RoleCode>
                            </gmd:role>
                        </gmd:CI_ResponsibleParty>
                    </gmd:citedResponsibleParty>
                    </#list>
                    </#if>
                </gmd:CI_Citation>
            </gmd:citation>
            <gmd:abstract>
                <gco:CharacterString>${dataset.description!'N/A'}</gco:CharacterString>
            </gmd:abstract>
            <#if dataset.awards?has_content>
            <#list dataset.awards as award>
            <gmd:credit>
                <gco:CharacterString>NSF Award ${award.awardNumber}</gco:CharacterString>
            </gmd:credit>
            </#list>
            </#if>
            <#if dataset.descriptiveMetadata.datasetProgress?has_content>
            <#assign datasetProgress = dataset.descriptiveMetadata.datasetProgress />
            <gmd:status>
                <#if datasetProgress.name() == "COMPLETE" >
                <gmd:MD_ProgressCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_ProgressCode" codeListValue="completed" codeSpace="001">completed</gmd:MD_ProgressCode>
                <#elseif datasetProgress.name() == "IN_WORK" >
                <gmd:MD_ProgressCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_ProgressCode" codeListValue="onGoing" codeSpace="004">onGoing</gmd:MD_ProgressCode>
                <#elseif datasetProgress.name() == "PLANNED" >
                <gmd:MD_ProgressCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_ProgressCode" codeListValue="planned" codeSpace="005">planned</gmd:MD_ProgressCode>
                <#elseif datasetProgress.name() == "NONE" >
                <gmd:MD_ProgressCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_ProgressCode" codeListValue="underDevelopment" codeSpace="007">underDevelopment</gmd:MD_ProgressCode>
                </#if>
            </gmd:status>
            </#if>
            <#if dataset.descriptiveMetadata.pointOfContacts?has_content>
            <#list dataset.descriptiveMetadata.pointOfContacts as poc>
            <gmd:pointOfContact>
                <gmd:CI_ResponsibleParty>
                    <#if poc.individualName?has_content>
                    <gmd:individualName>
                        <gco:CharacterString>${poc.individualName}</gco:CharacterString>
                    </gmd:individualName>
                    </#if>
                    <#if poc.organizationName?has_content>
                    <gmd:organisationName>
                        <gco:CharacterString>${poc.organizationName}</gco:CharacterString>
                    </gmd:organisationName>
                    </#if>
                    <#if poc.email?has_content>
                    <gmd:contactInfo>
                        <gmd:CI_Contact>
                            <gmd:address>
                                <gmd:CI_Address>
                                    <gmd:electronicMailAddress>
                                        <gco:CharacterString>${poc.email}</gco:CharacterString>
                                    </gmd:electronicMailAddress>
                                </gmd:CI_Address>
                            </gmd:address>
                        </gmd:CI_Contact>
                    </gmd:contactInfo>
                    </#if>
                    <gmd:role>
                        <gmd:CI_RoleCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_RoleCode" codeListValue="${poc.role}">${poc.role}</gmd:CI_RoleCode>
                    </gmd:role>
                </gmd:CI_ResponsibleParty>
            </gmd:pointOfContact>
            </#list>
            </#if>
            <#if dataset.dataFormats?has_content>
            <#list dataset.dataFormats as dataFormat>
            <gmd:resourceFormat>
                <gmd:MD_Format>
                    <gmd:name>
                        <gco:CharacterString>${dataFormat.name}</gco:CharacterString>
                    </gmd:name>
                    <gmd:version gco:nilReason="unknown"/>
                </gmd:MD_Format>
            </gmd:resourceFormat>
            </#list>
            </#if>
            <#if dataset.descriptiveMetadata.scienceKeywords?has_content>
            <gmd:descriptiveKeywords>
                <gmd:MD_Keywords>
                    <#list dataset.descriptiveMetadata.scienceKeywords as scienceKeyword>
                    <gmd:keyword>
                        <gco:CharacterString>${scienceKeyword.category?upper_case} &gt; ${scienceKeyword.displayText?upper_case}</gco:CharacterString>
                    </gmd:keyword>
                    </#list>
                    <gmd:type>
                        <gmd:MD_KeywordTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_KeywordTypeCode" codeListValue="theme">theme</gmd:MD_KeywordTypeCode>
                    </gmd:type>
                    <gmd:thesaurusName>
                        <gmd:CI_Citation>
                            <gmd:title>
                                <gco:CharacterString>NASA/GCMD Earth Science Keywords</gco:CharacterString>
                            </gmd:title>
                            <gmd:alternateTitle>
                                <gco:CharacterString>Science and Services Keywords</gco:CharacterString>
                            </gmd:alternateTitle>
                            <gmd:date>
                                <gmd:CI_Date>
                                    <gmd:date>
                                        <gco:Date>2014-05-21</gco:Date>
                                    </gmd:date>
                                    <gmd:dateType>
                                        <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="revision">revision</gmd:CI_DateTypeCode>
                                    </gmd:dateType>
                                </gmd:CI_Date>
                            </gmd:date>
                            <gmd:collectiveTitle>
                                <gco:CharacterString>Olsen, L.M., G. Major, K. Shein, J. Scialdone, S. Ritz, T. Stevens, M. Morahan, A. Aleman, R. Vogel, S. Leicester, H. Weir, M. Meaux, S. Grebas, C.Solomon, M. Holland, T. Northcutt, R. A. Restrepo, R. Bilodeau, 2013. NASA/Global Change Master Directory (GCMD) Earth Science Keywords. Version 8.0.0.0.0</gco:CharacterString>
                            </gmd:collectiveTitle>
                        </gmd:CI_Citation>
                    </gmd:thesaurusName>
                </gmd:MD_Keywords>
            </gmd:descriptiveKeywords>
            </#if>
            <#if dataset.descriptiveMetadata.instrumentKeywords?has_content>
            <gmd:descriptiveKeywords>
                <gmd:MD_Keywords>
                    <#list dataset.descriptiveMetadata.instrumentKeywords as instrument>
                    <gmd:keyword>
                        <gco:CharacterString>${instrument.category?upper_case} &gt; ${instrument.displayText?upper_case}</gco:CharacterString>
                    </gmd:keyword>
                    </#list>
                    <gmd:type>
                        <gmd:MD_KeywordTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_KeywordTypeCode" codeListValue="theme">theme</gmd:MD_KeywordTypeCode>
                    </gmd:type>
                    <gmd:thesaurusName>
                        <gmd:CI_Citation>
                            <gmd:title>
                                <gco:CharacterString>NASA/GCMD Earth Science Keywords</gco:CharacterString>
                            </gmd:title>
                            <gmd:alternateTitle>
                                <gco:CharacterString>Instruments</gco:CharacterString>
                            </gmd:alternateTitle>
                            <gmd:date>
                                <gmd:CI_Date>
                                    <gmd:date>
                                        <gco:Date>2014-08-28</gco:Date>
                                    </gmd:date>
                                    <gmd:dateType>
                                        <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="revision">revision</gmd:CI_DateTypeCode>
                                    </gmd:dateType>
                                </gmd:CI_Date>
                            </gmd:date>
                            <gmd:collectiveTitle>
                                <gco:CharacterString>Olsen, L.M., G. Major, K. Shein, J. Scialdone, S. Ritz, T. Stevens, M. Morahan, A. Aleman, R. Vogel, S. Leicester, H. Weir, M. Meaux, S. Grebas, C.Solomon, M. Holland, T. Northcutt, R. A. Restrepo, R. Bilodeau, 2013. NASA/Global Change Master Directory (GCMD) Earth Science Keywords. Version 8.0.0.0.0</gco:CharacterString>
                            </gmd:collectiveTitle>
                        </gmd:CI_Citation>
                    </gmd:thesaurusName>
                </gmd:MD_Keywords>
            </gmd:descriptiveKeywords>
            </#if>
            <#if dataset.descriptiveMetadata.platformTypes?has_content>
            <gmd:descriptiveKeywords>
                <gmd:MD_Keywords>
                    <#list dataset.descriptiveMetadata.platformTypes as platform>
                    <gmd:keyword>
                        <gco:CharacterString>${platform.shortName?upper_case}</gco:CharacterString>
                    </gmd:keyword>
                    </#list>
                    <gmd:type>
                        <gmd:MD_KeywordTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_KeywordTypeCode" codeListValue="theme">theme</gmd:MD_KeywordTypeCode>
                    </gmd:type>
                    <gmd:thesaurusName>
                        <gmd:CI_Citation>
                            <gmd:title>
                                <gco:CharacterString>ACADIS Keywords</gco:CharacterString>
                            </gmd:title>
                            <gmd:alternateTitle>
                                <gco:CharacterString>Platforms</gco:CharacterString>
                            </gmd:alternateTitle>
                            <gmd:date>
                                <gmd:CI_Date>
                                    <gmd:date>
                                        <gco:Date>2014-10-07</gco:Date>
                                    </gmd:date>
                                    <gmd:dateType>
                                        <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="revision">revision</gmd:CI_DateTypeCode>
                                    </gmd:dateType>
                                </gmd:CI_Date>
                            </gmd:date>
                        </gmd:CI_Citation>
                    </gmd:thesaurusName>
                </gmd:MD_Keywords>
            </gmd:descriptiveKeywords>
            </#if>
            <#if dataset.projectGroup?has_content>
            <gmd:descriptiveKeywords>
                <gmd:MD_Keywords>
                    <gmd:keyword>
                        <gco:CharacterString>${dataset.projectGroup}</gco:CharacterString>
                    </gmd:keyword>
                </gmd:MD_Keywords>
            </gmd:descriptiveKeywords>
            </#if>
            <#if dataset.descriptiveMetadata.dataTypes?has_content>
            <gmd:descriptiveKeywords>
                <gmd:MD_Keywords>
                    <#list dataset.descriptiveMetadata.dataTypes as dataType>
                    <gmd:keyword>
                        <gco:CharacterString>${dataType.longName?upper_case}</gco:CharacterString>
                    </gmd:keyword>
                    </#list>
                    <gmd:type>
                        <gmd:MD_KeywordTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_KeywordTypeCode" codeListValue="theme">theme</gmd:MD_KeywordTypeCode>
                    </gmd:type>
                    <gmd:thesaurusName>
                        <gmd:CI_Citation>
                            <gmd:title>
                                <gco:CharacterString>ACADIS Keywords</gco:CharacterString>
                            </gmd:title>
                            <gmd:alternateTitle>
                                <gco:CharacterString>Spatial Data Type</gco:CharacterString>
                            </gmd:alternateTitle>
                            <gmd:date>
                                <gmd:CI_Date>
                                    <gmd:date>
                                        <gco:Date>2014-10-07</gco:Date>
                                    </gmd:date>
                                    <gmd:dateType>
                                        <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="revision">revision</gmd:CI_DateTypeCode>
                                    </gmd:dateType>
                                </gmd:CI_Date>
                            </gmd:date>
                        </gmd:CI_Citation>
                    </gmd:thesaurusName>
                </gmd:MD_Keywords>
            </gmd:descriptiveKeywords>
            </#if>
            <#if dataset.descriptiveMetadata.resolutionTypes?has_content>
            <gmd:descriptiveKeywords>
                <gmd:MD_Keywords>
                    <#list dataset.descriptiveMetadata.resolutionTypes as resolutionType>
                    <gmd:keyword>
                        <gco:CharacterString>${resolutionType.longName?upper_case}</gco:CharacterString>
                    </gmd:keyword>
                    </#list>
                    <gmd:type>
                        <gmd:MD_KeywordTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_KeywordTypeCode" codeListValue="theme">theme</gmd:MD_KeywordTypeCode>
                    </gmd:type>
                    <gmd:thesaurusName>
                        <gmd:CI_Citation>
                            <gmd:title>
                                <gco:CharacterString>NASA/GCMD Earth Science Keywords</gco:CharacterString>
                            </gmd:title>
                            <gmd:alternateTitle>
                                <gco:CharacterString>Horizontal Data Resolution</gco:CharacterString>
                            </gmd:alternateTitle>
                            <gmd:date>
                                <gmd:CI_Date>
                                    <gmd:date>
                                        <gco:Date>2014-05-21</gco:Date>
                                    </gmd:date>
                                    <gmd:dateType>
                                        <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="revision">revision</gmd:CI_DateTypeCode>
                                    </gmd:dateType>
                                </gmd:CI_Date>
                            </gmd:date>
                            <gmd:collectiveTitle>
                                <gco:CharacterString>Olsen, L.M., G. Major, K. Shein, J. Scialdone, S. Ritz, T. Stevens, M. Morahan, A. Aleman, R. Vogel, S. Leicester, H. Weir, M. Meaux, S. Grebas, C.Solomon, M. Holland, T. Northcutt, R. A. Restrepo, R. Bilodeau, 2013. NASA/Global Change Master Directory (GCMD) Earth Science Keywords. Version 8.0.0.0.0</gco:CharacterString>
                            </gmd:collectiveTitle>
                        </gmd:CI_Citation>
                    </gmd:thesaurusName>
                </gmd:MD_Keywords>
            </gmd:descriptiveKeywords>
            </#if>
            <#if dataset.descriptiveMetadata.timeFrequencies?has_content>
            <gmd:descriptiveKeywords>
                <gmd:MD_Keywords>
                    <#list dataset.descriptiveMetadata.timeFrequencies as timeFrequency>
                    <gmd:keyword>
                        <gco:CharacterString>${timeFrequency.name?upper_case}</gco:CharacterString>
                    </gmd:keyword>
                    </#list>
                    <gmd:type>
                        <gmd:MD_KeywordTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_KeywordTypeCode" codeListValue="theme">theme</gmd:MD_KeywordTypeCode>
                    </gmd:type>
                    <gmd:thesaurusName>
                        <gmd:CI_Citation>
                            <gmd:title>
                                <gco:CharacterString>NASA/GCMD Earth Science Keywords</gco:CharacterString>
                            </gmd:title>
                            <gmd:alternateTitle>
                                <gco:CharacterString>Temporal Data Resolution</gco:CharacterString>
                            </gmd:alternateTitle>
                            <gmd:date>
                                <gmd:CI_Date>
                                    <gmd:date>
                                        <gco:Date>2014-05-21</gco:Date>
                                    </gmd:date>
                                    <gmd:dateType>
                                        <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="revision">revision</gmd:CI_DateTypeCode>
                                    </gmd:dateType>
                                </gmd:CI_Date>
                            </gmd:date>
                            <gmd:collectiveTitle>
                                <gco:CharacterString>Olsen, L.M., G. Major, K. Shein, J. Scialdone, S. Ritz, T. Stevens, M. Morahan, A. Aleman, R. Vogel, S. Leicester, H. Weir, M. Meaux, S. Grebas, C.Solomon, M. Holland, T. Northcutt, R. A. Restrepo, R. Bilodeau, 2013. NASA/Global Change Master Directory (GCMD) Earth Science Keywords. Version 8.0.0.0.0</gco:CharacterString>
                            </gmd:collectiveTitle>
                        </gmd:CI_Citation>
                    </gmd:thesaurusName>
                </gmd:MD_Keywords>
            </gmd:descriptiveKeywords>
            </#if>
            <gmd:resourceConstraints>
                <gmd:MD_LegalConstraints>
                    <gmd:accessConstraints>
                        <gmd:MD_RestrictionCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_RestrictionCode" codeListValue="otherRestrictions" codeSpace="008">otherRestrictions</gmd:MD_RestrictionCode>
                    </gmd:accessConstraints>
                    <gmd:useConstraints>
                        <gmd:MD_RestrictionCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#MD_RestrictionCode" codeListValue="otherRestrictions" codeSpace="008">otherRestrictions</gmd:MD_RestrictionCode>
                    </gmd:useConstraints>
                    <gmd:otherConstraints>
                        <gco:CharacterString>Access Constraints: No Access Constraints. Use Constraints: No Use Constraints.</gco:CharacterString>
                    </gmd:otherConstraints>
                </gmd:MD_LegalConstraints>
            </gmd:resourceConstraints>
            <gmd:language>
                <gco:CharacterString>eng</gco:CharacterString>
            </gmd:language>
            <#if dataset.isoTopics?has_content>
            <gmd:topicCategory>
            <#list dataset.isoTopics as topic>
                <gmd:MD_TopicCategoryCode>${topic.name}</gmd:MD_TopicCategoryCode>
            </#list>
            </gmd:topicCategory>
            </#if>
            <#if dataset.descriptiveMetadata.geographicBoundingBox?has_content || dataset.descriptiveMetadata.timePeriod?has_content || dataset.descriptiveMetadata.locations?has_content>
            <gmd:extent>
                <gmd:EX_Extent>
                    <#if dataset.descriptiveMetadata.locations?has_content>
                    <#list dataset.descriptiveMetadata.locations as location>
                    <gmd:geographicElement>
                        <gmd:EX_GeographicDescription>
                            <gmd:geographicIdentifier>
                                <gmd:MD_Identifier>
                                    <gmd:authority>
                                        <gmd:CI_Citation>
                                            <gmd:title>
                                                <gco:CharacterString>NASA/GCMD Earth Science Keywords</gco:CharacterString>
                                            </gmd:title>
                                            <gmd:alternateTitle>
                                                <gco:CharacterString>Locations</gco:CharacterString>
                                            </gmd:alternateTitle>
                                            <gmd:date>
                                                <gmd:CI_Date>
                                                    <gmd:date>
                                                        <gco:Date>2014-08-28</gco:Date>
                                                    </gmd:date>
                                                    <gmd:dateType>
                                                        <gmd:CI_DateTypeCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_DateTypeCode" codeListValue="revision">revision</gmd:CI_DateTypeCode>
                                                    </gmd:dateType>
                                                </gmd:CI_Date>
                                            </gmd:date>
                                            <gmd:collectiveTitle>
                                                <gco:CharacterString>Olsen, L.M., G. Major, K. Shein, J. Scialdone, S. Ritz, T. Stevens, M. Morahan, A. Aleman, R. Vogel, S. Leicester, H. Weir, M. Meaux, S. Grebas, C.Solomon, M. Holland, T. Northcutt, R. A. Restrepo, R. Bilodeau, 2013. NASA/Global Change Master Directory (GCMD) Earth Science Keywords. Version 8.0.0.0.0</gco:CharacterString>
                                            </gmd:collectiveTitle>
                                        </gmd:CI_Citation>
                                    </gmd:authority>
                                    <gmd:code>
                                        <gco:CharacterString>${location.name?upper_case}</gco:CharacterString>
                                    </gmd:code>
                                </gmd:MD_Identifier>
                            </gmd:geographicIdentifier>
                        </gmd:EX_GeographicDescription>
                    </gmd:geographicElement>
                    </#list>
                    </#if>
                    <#if dataset.descriptiveMetadata.geographicBoundingBox?has_content>
                    <#assign geographicBoundingBox = dataset.descriptiveMetadata.geographicBoundingBox />
                    <gmd:geographicElement>
                        <gmd:EX_GeographicBoundingBox>
                            <gmd:westBoundLongitude>
                                <gco:Decimal>${geographicBoundingBox.westBoundLongitude}</gco:Decimal>
                            </gmd:westBoundLongitude>
                            <gmd:eastBoundLongitude>
                                <gco:Decimal>${geographicBoundingBox.eastBoundLongitude}</gco:Decimal>
                            </gmd:eastBoundLongitude>
                            <gmd:southBoundLatitude>
                                <gco:Decimal>${geographicBoundingBox.southBoundLatitude}</gco:Decimal>
                            </gmd:southBoundLatitude>
                            <gmd:northBoundLatitude>
                                <gco:Decimal>${geographicBoundingBox.northBoundLatitude}</gco:Decimal>
                            </gmd:northBoundLatitude>
                        </gmd:EX_GeographicBoundingBox>
                    </gmd:geographicElement>
                    </#if>
                    <#if dataset.descriptiveMetadata.timePeriod?has_content>
                    <#assign timePeriod = dataset.descriptiveMetadata.timePeriod />
                    <gmd:temporalElement>
                        <gmd:EX_TemporalExtent>
                            <gmd:extent>
                                <gml:TimePeriod gml:id="tp_${dataset.identifier}">
                                    <#if timePeriod.begin?has_content>
                                    <gml:beginPosition>${timePeriod.begin?string("yyyy-MM-dd'T'HH:mm:ss")}</gml:beginPosition>
                                    <#else>
                                    <gml:beginPosition indeterminatePosition="unknown"/>
                                    </#if>
                                    <#if timePeriod.end?has_content>
                                    <gml:endPosition>${timePeriod.end?string("yyyy-MM-dd'T'HH:mm:ss")}</gml:endPosition>
                                    <#else>
                                    <gml:endPosition indeterminatePosition="unknown"/>
                                    </#if>
                                </gml:TimePeriod>
                            </gmd:extent>
                        </gmd:EX_TemporalExtent>
                    </gmd:temporalElement>
                    </#if>
                </gmd:EX_Extent>
            </gmd:extent>
            </#if>
        </gmd:MD_DataIdentification>
    </gmd:identificationInfo>

    <gmd:distributionInfo>
        <gmd:MD_Distribution>
            <#if dataset.dataFormats?has_content>
            <#list dataset.dataFormats as dataFormat>
            <gmd:distributionFormat>
                <gmd:MD_Format>
                    <gmd:name>
                        <gco:CharacterString>${dataFormat.name}</gco:CharacterString>
                    </gmd:name>
                    <gmd:version gco:nilReason="unknown"/>
                </gmd:MD_Format>
            </gmd:distributionFormat>
            </#list>
            <#else>
            <gmd:distributionFormat>
                <gmd:MD_Format>
                    <gmd:name gco:nilReason="missing"/>
                    <gmd:version gco:nilReason="missing"/>
                </gmd:MD_Format>
            </gmd:distributionFormat>
            </#if>
            <gmd:distributor>
                <gmd:MD_Distributor>
                    <gmd:distributorContact>
                        <gmd:CI_ResponsibleParty>
                            <gmd:organisationName>
                                <gco:CharacterString>${gatewayName}</gco:CharacterString>
                            </gmd:organisationName>
                            <gmd:role>
                                <gmd:CI_RoleCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_RoleCode" codeListValue="resourceProvider">resourceProvider</gmd:CI_RoleCode>
                            </gmd:role>
                        </gmd:CI_ResponsibleParty>
                    </gmd:distributorContact>
                </gmd:MD_Distributor>
            </gmd:distributor>
            <gmd:transferOptions>
                <gmd:MD_DigitalTransferOptions>
                    <gmd:onLine>
                        <gmd:CI_OnlineResource>
                            <gmd:linkage>
                                <gmd:URL>${gateway.baseSecureURL}dataset/id/${dataset.identifier}.html</gmd:URL>
                            </gmd:linkage>
                            <gmd:protocol>
                                <gco:CharacterString>${gateway.baseSecureURL.scheme}</gco:CharacterString>
                            </gmd:protocol>
                            <gmd:applicationProfile>
                                <gco:CharacterString>browser</gco:CharacterString>
                            </gmd:applicationProfile>
                            <gmd:name>
                                <gco:CharacterString>${dataset.title}</gco:CharacterString>
                            </gmd:name>
                            <gmd:description>
                                <gco:CharacterString>Metadata Link</gco:CharacterString>
                            </gmd:description>
                            <gmd:function>
                                <gmd:CI_OnLineFunctionCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_OnLineFunctionCode" codeListValue="download">download</gmd:CI_OnLineFunctionCode>
                            </gmd:function>
                        </gmd:CI_OnlineResource>
                    </gmd:onLine>
                </gmd:MD_DigitalTransferOptions>
            </gmd:transferOptions>
            <#if dataset.descriptiveMetadata.relatedLinks?has_content>
            <gmd:transferOptions>
                <gmd:MD_DigitalTransferOptions>
                    <#list dataset.descriptiveMetadata.relatedLinks as relatedLink>
                    <gmd:onLine>
                        <gmd:CI_OnlineResource>
                            <gmd:linkage>
                                <gmd:URL>${relatedLink.uri}</gmd:URL>
                            </gmd:linkage>
                            <gmd:protocol>
                                <gco:CharacterString>${relatedLink.uri.scheme}</gco:CharacterString>
                            </gmd:protocol>
                            <gmd:applicationProfile>
                                <gco:CharacterString>browser</gco:CharacterString>
                            </gmd:applicationProfile>
                            <gmd:name>
                                <gco:CharacterString>${relatedLink.text}</gco:CharacterString>
                            </gmd:name>
                            <gmd:description>
                                <gco:CharacterString>Related Link</gco:CharacterString>
                            </gmd:description>
                            <gmd:function>
                                <gmd:CI_OnLineFunctionCode codeList="http://www.isotc211.org/2005/resources/Codelist/gmxCodelists.xml#CI_OnLineFunctionCode" codeListValue="information">information</gmd:CI_OnLineFunctionCode>
                            </gmd:function>
                        </gmd:CI_OnlineResource>
                    </gmd:onLine>
                    </#list>
                </gmd:MD_DigitalTransferOptions>
            </gmd:transferOptions>
            </#if>
        </gmd:MD_Distribution>
    </gmd:distributionInfo>

</gmd:MD_Metadata>

</#escape>
