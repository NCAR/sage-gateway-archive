<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="false" type="sgf.gateway.model.metadata.Dataset" %>
<%@ attribute name="projectList" required="false" type="java.util.List" %>
<%@ attribute name="partiesByRole" required="false" type="java.util.Map" %>
<%@ attribute name="moreLikeThisList" required="false" type="java.util.List" %>

<%@ attribute name="discipline" required="false" type="java.lang.String" %>
<%@ attribute name="pi" required="false" type="java.lang.String" %>
<%@ attribute name="aonProject" required="false" type="java.lang.String" %>

<c:if test="${dataset.publishedState!='PUBLISHED'}">
	<browse:descriptive-row fieldName="State" fieldValue="${dataset.publishedState.name}" showEmpty="false" />
	<browse:descriptive-row fieldName="Identifier" fieldValue="${dataset.shortName}" showEmpty="false" />
</c:if>

<c:forEach var="role" items="${partiesByRole.keySet()}">
	<c:set var="partiesString">
		<c:forEach var="party" items="${partiesByRole.get(role)}">
			${party}
			<br/>
		</c:forEach>
	</c:set>
	<browse:descriptive-row fieldName="${role}" fieldValue="${partiesString}" showEmpty="false"/>
</c:forEach>

<c:if test="${not empty fn:trim(dataset.DOI)}">
	<c:set var="doi_permanent_link"><a href="http://dx.doi.org/${dataset.DOI}">Permanent Link</a></c:set>
	<c:set var="ezid_citation_link"><a href="http://ezid.cdlib.org/id/${dataset.DOI}">Citation</a></c:set>

	<browse:descriptive-row fieldName="DOI" fieldValue="${dataset.DOI} ${doi_permanent_link}, ${ezid_citation_link}" showEmpty="false" />
</c:if>

<browse:descriptive-row fieldName="Description" fieldValue="${gateway:formatReturnToBreak(dataset.description)}" showEmpty="false" />

<c:set var="progressString">
	${dataset.descriptiveMetadata.datasetProgress.name}
	<c:if test="${fn:length(dataset.descriptiveMetadata.datasetProgress.description) > 0}">
		 - ${dataset.descriptiveMetadata.datasetProgress.description}
	</c:if>
</c:set>
<browse:descriptive-row fieldName="Progress" fieldValue="${progressString}" showEmpty="false" />

<c:if test="${not empty dataset.descriptiveMetadata.timePeriod }">
	<c:set var="timeString">
		<fmt:formatDate value="${dataset.descriptiveMetadata.timePeriod.begin}"/> - <fmt:formatDate value="${dataset.descriptiveMetadata.timePeriod.end}"/>
	</c:set>
</c:if>
<browse:descriptive-row showEmpty="false" fieldValue="${timeString}" fieldName="Time Coverage" />

<div>

	<c:if test="${not empty dataset.descriptiveMetadata.geographicBoundingBox }">
		<c:set var="maxLatitude">
			<fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.northBoundLatitude}' maxFractionDigits="3" />
		</c:set>
		<c:set var="minLatitude">
			<fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.southBoundLatitude}' maxFractionDigits="3" />
		</c:set>
		<c:set var="maxLongitude">
			<fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.westBoundLongitude}' maxFractionDigits="3" />
		</c:set>
		<c:set var="minLongitude">
			<fmt:formatNumber type="number" value='${dataset.descriptiveMetadata.geographicBoundingBox.eastBoundLongitude}' maxFractionDigits="3" />
		</c:set>
	</c:if>

	<div style="float:left; width: 500px;">
		<browse:descriptive-row showEmpty="false" fieldValue="${maxLatitude}" fieldName="Northernmost Latitude" />
		<browse:descriptive-row showEmpty="false" fieldValue="${minLatitude}" fieldName="Southernmost Latitude" />
		<browse:descriptive-row showEmpty="false" fieldValue="${maxLongitude}" fieldName="Westernmost Longitude" />
		<browse:descriptive-row showEmpty="false" fieldValue="${minLongitude}" fieldName="Easternmost Longitude" />
	</div>

	<%-- May not have a bounding box if top level ds --%>
	<c:if test="${not empty dataset.descriptiveMetadata.geographicBoundingBox }">
		<div style="width:259px; height:130px; float:left; ">
			<div id="map-canvas" style="width: 100%; height: 100%;"></div>
		</div>
	
		<div style="padding-top: 20px; clear: both;"></div>
	</c:if>

</div>

<c:set var="awardsString">
	<c:forEach var="award" items="${dataset.awards}">
		<div style="padding-top: 0px;">
			<c:url var="awardLink" value="/redirect.html" >
				<c:param name="link" value="http://www.nsf.gov/awardsearch/showAward?AWD_ID=${award.awardNumber}" />
			</c:url>
			<a href="${awardLink}"><c:out value="${award.awardNumber}"/></a>
		</div>
	</c:forEach>
</c:set>

<browse:descriptive-row fieldName="Award Numbers" fieldValue="${awardsString}" showEmpty="false" />

<c:set var="disciplineString">
	<c:forEach var="topic" items="${dataset.inheritedTopics}">
		<c:if test="${topic.type == 'CADIS_DISCIPLINE'}">
		<c:url value="/topic/${topic.identifier}.html" var="topicUrl"></c:url>
		<a href="${topicUrl}" >${topic.name}</a><br>
		</c:if>
	</c:forEach>
</c:set>
<browse:descriptive-row fieldName="Topic" fieldValue="${disciplineString}" showEmpty="false" />

<c:set var="scienceKeywordString">
	<c:forEach var="keyword" items="${dataset.descriptiveMetadata.scienceKeywords}">
		
			${keyword.displayText}<br>
		
	</c:forEach>
</c:set>
<browse:descriptive-row fieldName="Science Keywords" fieldValue="${scienceKeywordString}" showEmpty="false" />

<c:set var="isoString">
	<c:forEach var="topic" items="${dataset.inheritedTopics}">
		<c:if test="${topic.type == 'ISO'}">
			${topic.name}<br>
		</c:if>
	</c:forEach>
</c:set>
<browse:descriptive-row fieldName="ISO Topic(s)" fieldValue="${isoString}" showEmpty="false" />

<browse:geodataset-details-tab-content dataset="${dataset}" />

<c:set var="platformTypeString">
	<c:forEach var='platform' items="${dataset.descriptiveMetadata.platformTypes}">
		${platform.shortName}
		<c:if test="${fn:length(platform.longName) > 0}">
			 (${platform.longName})
		</c:if>
		<br />
	</c:forEach>
</c:set>
<browse:descriptive-row fieldName="Platform(s)" fieldValue="${platformTypeString}" showEmpty="false" />

<c:set var="instrumentKeywordString">
	<c:forEach var='instrument' items="${dataset.descriptiveMetadata.instrumentKeywords}">
		${instrument.displayText}
		<c:if test="${fn:length(instrument.longName) > 0}">
			 (${instrument.longName})
		</c:if>
		<br />
	</c:forEach>
</c:set>

<browse:descriptive-row fieldName="Instrument(s)" fieldValue="${instrumentKeywordString}" showEmpty="false" />

<c:set var="dataFormatString">
	<c:forEach var="format" items="${dataset.dataFormats}">
		${format.name}
		<c:if test="${fn:length(format.description) > 0}">
			(${format.description})
		</c:if>
		<br />
	</c:forEach>
</c:set>
<browse:descriptive-row fieldName="Data Format(s)" fieldValue="${dataFormatString}" showEmpty="false" />

<browse:descriptive-row fieldName="Language" fieldValue="${dataset.descriptiveMetadata.language}" showEmpty="false" />

<c:choose>
	<c:when test="${dataset.brokered}">
		<browse:descriptive-row-date fieldName="Date Created" fieldValue="${dataset.currentDatasetVersion.authoritativeSourceDateCreated}" showEmpty="false" />
		<browse:descriptive-row-date fieldName="Date Last Updated" fieldValue="${dataset.currentDatasetVersion.authoritativeSourceDateModified}" showEmpty="false" />
	</c:when>
	<c:otherwise>
		<browse:descriptive-row-date fieldName="Date Created" fieldValue="${dataset.dateCreated}" showEmpty="false" />
		<browse:descriptive-row-date fieldName="Date Last Updated" fieldValue="${dataset.dateUpdated}" showEmpty="false" />
	</c:otherwise>
</c:choose>

<c:set var="dataCenter" value="${dataset.dataCenter}" />

<c:url var="redirectURL" value="/redirect.html" >
	<c:param name="link" value="${dataCenter.URL}" />
</c:url>

<c:set var="source"><a href="${redirectURL}">${dataCenter.longName}</a></c:set>
<browse:descriptive-row fieldName="Data Center" fieldValue="${source}" showEmpty="false" />

<c:if test="${dataset.brokered}">
	<c:url var="redirectURL" value="/redirect.html" >
		<c:param name="link" value="${dataset.currentDatasetVersion.authoritativeSourceURI}" />
	</c:url>
	<c:set var="source"><a href="${redirectURL}">${dataset.currentDatasetVersion.authoritativeSourceURI.host}</a></c:set>
	<browse:descriptive-row fieldName="Authoritative Source" fieldValue="${source}" showEmpty="false" />
</c:if>

<browse:descriptive-row fieldName="Physical Domain" fieldValue="${dataset.descriptiveMetadata.physicalDomain.name}" showEmpty="false" />

<c:set var="relatedUrlsString">
	<c:forEach var="url" items="${dataset.descriptiveMetadata.relatedLinks}">
		<c:url var="redirectURL" value="/redirect.html" >
			<c:param name="link" value="${url.uri}" />
		</c:url>
		<a href="${redirectURL}">${url.text}</a><br>
	</c:forEach>
</c:set>
<browse:descriptive-row fieldName="Related Links" fieldValue="${relatedUrlsString}" showEmpty="false" />

<c:set var="moreLikeThisString">
	<c:forEach var="result" items="${moreLikeThisList}">
		<c:choose>
			<c:when test="${result.remoteIndexable}">
				<c:url var="redirectURL" value="/redirect.html" >
					<c:param name="ref" value="moreLikeThis" />
					<c:param name="link" value="${result.detailsURI}" />
				</c:url>
				<a href="${redirectURL}">${result.title}</a> 
				<a href="${redirectURL}"><img src="<c:url value="/images/icons/external-link-icon.svg" />" ></a> 
				<br>
			</c:when>
			<c:otherwise>
				<c:url var="gatewayURL" value="/${result.type.toLowerCase()}/${result.shortName}.html" >
					<c:param name="ref" value="moreLikeThis" />
				</c:url>
				<a href="${gatewayURL}">${result.title}</a><br>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:set>
<browse:descriptive-row fieldName="More Like This Dataset" fieldValue="${moreLikeThisString}" showEmpty="false" />

<c:if test="${fn:length(gateway:filterPublished(dataset.directlyNestedDatasets)) > 0}">
	<h2>Nested Datasets</h2>
	<cadis:dataset-list-panel datasets="${gateway:filterPublished(dataset.directlyNestedDatasets)}" />
</c:if>

<authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')" >
	
	<c:if test="${fn:length(gateway:filterPrePublished(dataset.directlyNestedDatasets)) > 0}">
		<h2>Pre-Published Nested Datasets</h2>
		<cadis:dataset-list-panel datasets="${gateway:filterPrePublished(dataset.directlyNestedDatasets)}" />
	</c:if>
	<c:if test="${fn:length(gateway:filterRetracted(dataset.directlyNestedDatasets)) > 0}">
		<h2>Retracted Nested Datasets</h2>
		<cadis:dataset-list-panel datasets="${gateway:filterRetracted(dataset.directlyNestedDatasets)}" />
	</c:if>
	
</authz:authorize>
