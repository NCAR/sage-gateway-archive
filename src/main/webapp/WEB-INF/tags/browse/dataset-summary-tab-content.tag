<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="false" type="sgf.gateway.model.metadata.Dataset" %>

<c:set var="piContacts">${gateway:filterPIContacts(dataset)}</c:set>
<c:set var="piString">
    <c:if test="${! empty piContacts}">
        <c:forEach var="piContact" items="${gateway:filterPIContacts(dataset)}">
            ${piContact.individualName}<c:if test="${fn:length(piContact.email) > 0}">, ${piContact.email}</c:if>
        </c:forEach>
    </c:if>
</c:set>
<browse:descriptive-row fieldName="PI(s)" fieldValue="${piString}" showEmpty="false" />

<c:set var="description">${gateway:formatReturnToBreak(dataset.description)}</c:set>
<browse:descriptive-row fieldName="Description" fieldValue="${description}" showEmpty="false" />
<browse:descriptive-row fieldName="Identifier" fieldValue="${dataset.shortName}" showEmpty="false" />

<c:if test="${not empty fn:trim(dataset.DOI)}">
    <c:set var="doi_permanent_link"><a href="http://dx.doi.org/${dataset.DOI}">Permanent Link</a></c:set>
    <c:set var="ezid_citation_link"><a href="http://ezid.cdlib.org/id/${dataset.DOI}">Citation</a></c:set>

    <browse:descriptive-row fieldName="DOI" fieldValue="${dataset.DOI} ${doi_permanent_link}, ${ezid_citation_link}" showEmpty="false" />
</c:if>

<browse:descriptive-row-date fieldName="Date Created" fieldValue="${dataset.dateCreated}" showEmpty="false" />

<browse:descriptive-row-date fieldName="Date Last Updated" fieldValue="${dataset.dateUpdated}" showEmpty="false" />

<c:if test="${dataset.brokered}">
    <c:url var="redirectURL" value="/redirect.html" >
        <c:param name="link" value="${dataset.currentDatasetVersion.authoritativeSourceURI}" />
    </c:url>
    <c:set var="source"><a href="${redirectURL}">${dataset.currentDatasetVersion.authoritativeSourceURI.host}</a></c:set>
    <browse:descriptive-row fieldName="Authoritative Source" fieldValue="${source}" showEmpty="false" />
</c:if>

<c:if test="${dataset.publishedState!='PUBLISHED'}">
    <browse:descriptive-row fieldName="State" fieldValue="${dataset.publishedState.name}" showEmpty="false" />
</c:if>

<c:set var="metadataContacts">${gateway:filterMetadataContacts(dataset)}</c:set>
<c:set var="metadataString">
    <c:if test="${! empty metadataContacts}">
        <c:forEach var="metadataContact" items="${gateway:filterMetadataContacts(dataset)}">
            <c:choose><c:when test="${metadataContact.firstName == metadataContact.lastName}">${metadataContact.firstName}</c:when><c:otherwise>${metadataContact.name}</c:otherwise></c:choose><c:if test="${fn:length(metadataContact.email) > 0}">, ${metadataContact.email}</c:if>
        </c:forEach>
    </c:if>
</c:set>
<browse:descriptive-row fieldName="Metadata Contact(s)" fieldValue="${metadataString}" showEmpty="false" />

<c:set var="disciplineString">
    <c:forEach var="topic" items="${dataset.inheritedTopics}">
        <c:if test="${topic.type == 'CADIS_DISCIPLINE'}">
            <c:url value="/topic/${topic.identifier}.html" var="topicUrl"></c:url>
            <a href="${topicUrl}" >${topic.name}</a><br>
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Topic" fieldValue="${disciplineString}" showEmpty="false" />

<c:set var="gcmdString">
    <c:forEach var="topic" items="${dataset.inheritedTopics}">
        <c:if test="${topic.type == 'GCMD'}">
            <c:url value="/topic/${topic.identifier}.html" var="topicUrl"></c:url>
            <a href="${topicUrl}" >${topic.name}</a><br>
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="GCMD Science Keyword(s)" fieldValue="${gcmdString}" showEmpty="false" />

<c:set var="isoString">
    <c:forEach var="topic" items="${dataset.inheritedTopics}">
        <c:if test="${topic.type == 'ISO'}">
            <c:url value="/topic/${topic.identifier}.html" var="topicUrl"></c:url>
            <a href="${topicUrl}" >${topic.name}</a><br>
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="ISO Topic(s)" fieldValue="${isoString}" showEmpty="false" />

<c:set var="cdpString">
    <c:forEach var="topic" items="${dataset.inheritedTopics}">
        <c:if test="${topic.type == 'DEFAULT'}">
            <c:url value="/topic/${topic.identifier}.html" var="topicUrl"></c:url>
            <a href="${topicUrl}" >${topic.name}</a><br>
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Topic" fieldValue="${cdpString}" showEmpty="false" />

<c:set var="realmString">
    <c:forEach var="topic" items="${dataset.inheritedTopics}">
        <c:if test="${topic.type == 'REALM'}">
            <c:url value="/topic/${topic.identifier}.html" var="topicUrl"></c:url>
            <a href="${topicUrl}" >${topic.name}</a><br>
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Topic" fieldValue="${realmString}" showEmpty="false" />

<c:set var="dataFormatString">
    <c:forEach var="format" items="${dataset.dataFormats}">
        ${format.name}
        <c:if test="${fn:length(format.description) > 0}">
            (${format.description})
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Data Format" fieldValue="${dataFormatString}" showEmpty="false" />

<c:set var="relatedUrlsString">
    <c:forEach var="url" items="${dataset.descriptiveMetadata.relatedLinks}">
        <a href='<c:url value="${url.uri}" />' target="new">${url.text}</a><br>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Related Links" fieldValue="${relatedUrlsString}" showEmpty="false" />

<%--This section formerly in geodataset-details-tab-content.tag --%>

<browse:descriptive-row fieldName="Physical Domain" fieldValue="${dataset.descriptiveMetadata.physicalDomain.name}" showEmpty="false" />

<c:set var="timeFrequencyString">
    <c:forEach items="${dataset.descriptiveMetadata.timeFrequencies}" var="timeFrequency">
        ${timeFrequency.name}
        <c:if test="${fn:length(timeFrequency.description) > 0}">
            - ${timeFrequency.description}
        </c:if>
        <br />
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Time Frequency(ies)" fieldValue="${timeFrequencyString}" showEmpty="false" />

<c:set var="locationsString">
    <c:forEach items="${dataset.descriptiveMetadata.locations}" var="location">
        ${location.name}<br />
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Location(s)" fieldValue="${locationsString}" showEmpty="false" />

<%-- This code is a bit rough, this tag is called from two places the /browse/view-dataset.jsp and the tags/cadis/dataset-summary-tab-content.tag.  For cadis we want to get the coordinates from the Project(Impl) or in this case 'activity'. --%>
<c:if test="${not empty dataset.descriptiveMetadata.geographicBoundingBox }">
    <c:set var="maxLatitudeString">
        <fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.northBoundLatitude}' maxFractionDigits="3" /> degrees
    </c:set>
    <c:set var="minLatitudeString">
        <fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.southBoundLatitude}' maxFractionDigits="3" /> degrees
    </c:set>
    <c:set var="maxLongitudeString">
        <fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.westBoundLongitude}' maxFractionDigits="3" /> degrees
    </c:set>
    <c:set var="minLongitudeString">
        <fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.eastBoundLongitude}' maxFractionDigits="3" /> degrees
    </c:set>
</c:if>

<browse:descriptive-row showEmpty="false" fieldValue="${maxLatitudeString}" fieldName="Northernmost Latitude" />
<browse:descriptive-row showEmpty="false" fieldValue="${minLatitudeString}" fieldName="Southernmost Latitude" />
<browse:descriptive-row showEmpty="false" fieldValue="${maxLongitudeString}" fieldName="Westernmost Longitude" />
<browse:descriptive-row showEmpty="false" fieldValue="${minLongitudeString}" fieldName="Easternmost Longitude" />

<c:if test="${not empty dataset.descriptiveMetadata.timePeriod }">
    <c:set var="timeString">
        <fmt:formatDate value="${dataset.descriptiveMetadata.timePeriod.begin}"/> - <fmt:formatDate value="${dataset.descriptiveMetadata.timePeriod.end}"/>
    </c:set>
</c:if>

<browse:descriptive-row showEmpty="false" fieldValue="${timeString}" fieldName="Time Coverage" />

<%-- End Geophysical Properties section --%>

<c:if test="${(dataset.currentDatasetVersion.logicalFileCount > 0 || fn:length(externalDataAccess) > 0) && not dataset.retracted}">
    <div class="panel panel-default">
        <div class="panel-body">
            <c:url var="datasetLicenseAgreement" value="/browse/viewCollectionFilesInitial.html" >
                <c:param name="datasetId" >${dataset.identifier}</c:param>
            </c:url>
            <a class="btn btn-default" href="${datasetLicenseAgreement}">Download Options</a>
        </div> <!-- .panel-body -->
    </div>
</c:if>

<c:if test="${dataset.hasAssociatedActivities}">
    <div class="panel panel-default">
        <div class="panel-heading">Related Activities</div>
        <div class="panel-body">
            <c:choose>
                <c:when test="${!dataset.hasAssociatedActivities}">
                    <p style="font-style: italic; color: #2647a0;">No Associated Activities</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="activity" items="${dataset.allActivities}">
                        <c:choose>
                            <c:when test="${activity.activityType.description eq 'Project' }">
                                <c:url var="viewActivityURL" value="/project/${activity.persistentIdentifier}.html" />
                            </c:when>
                            <c:otherwise>
                                <c:url var="viewActivityURL" value="/browse/viewActivity.html">
                                    <c:param name="activityId">${activity.identifier}</c:param>
                                </c:url>
                            </c:otherwise>
                        </c:choose>
                        <div style="padding-top: 5px;">${activity.activityType.description} - <a href="${viewActivityURL}">${activity.name}</a></div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div> <!-- .panel-body -->
    </div>
</c:if>

<c:if test="${not empty simulation}">
    <div class="panel panel-default">
        <div class="panel-heading">Model Metadata</div>
        <div class="panel-body">
            <div style="padding-top: 5px;">
                <span>Simulation</span> - <a href="<c:url value="/simulation/${simulation.name}.html" />">${simulation.name}</a>
            </div>
        </div> <!-- .panel-body -->
    </div>
</c:if>

<c:if test="${fn:length(gateway:filterPublished(dataset.directlyNestedDatasets)) > 0}">
    <browse:dataset-list-panel datasets="${gateway:filterPublished(dataset.directlyNestedDatasets)}" title="Child Datasets" />
</c:if>

<authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')" >

    <c:if test="${fn:length(gateway:filterPrePublished(dataset.directlyNestedDatasets)) > 0}">
        <browse:dataset-list-panel datasets="${gateway:filterPrePublished(dataset.directlyNestedDatasets)}" title="Pre-Published Child Datasets" />
    </c:if>

    <c:if test="${fn:length(gateway:filterRetracted(dataset.directlyNestedDatasets)) > 0}">
        <browse:dataset-list-panel datasets="${gateway:filterRetracted(dataset.directlyNestedDatasets)}" title="Retracted Child Datasets" />
    </c:if>

</authz:authorize>
