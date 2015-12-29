<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="true" type="sgf.gateway.model.metadata.Dataset" %>
<%@ attribute name="dataset_to_move" required="true" type="sgf.gateway.model.metadata.Dataset" %>
<%@ attribute name="descendants" required="true" type="java.util.Set" %>

<ul>
	<c:forEach var="dataset" items="${dataset.directlyNestedDatasets}">
		
		<%-- See if the current dataset in the hierarchy is a parent of the dataset to move.
		Because the higher ones get links and the lower ones don't. --%>
		
		<c:set var="isDescendant" value="false" />
		
		<c:forEach var="descendant" items="${descendants}">
			<c:if test="${descendant.shortName == dataset.shortName}">
				<c:set var="isDescendant" value="true" />
			</c:if>
		</c:forEach>
	
		<c:url value="/dataset/${dataset.shortName}/moveDatasetConfirmation.html" var="datasetContainerURL">
			<c:param name="dataset_to_move_name" value="${dataset_to_move.shortName}" />
		</c:url>
	
		<c:choose>
			<c:when test="${(isDescendant) or (dataset_to_move.shortName == dataset.shortName)}">
				<li><c:out value="${dataset.title}" /></li>
			</c:when>
			<c:otherwise>
			<li><a href="${datasetContainerURL}">${dataset.title}</a><br/>
				<span style="padding-left: 10px; font-size: 85%; color: grey;">
				<c:out value="Last Updated: ${dataset.dateUpdated}" />
				</span>
			</li>
			</c:otherwise>
		</c:choose>
		
		<cadis:move-dataset-tree-root dataset="${dataset}" dataset_to_move="${dataset_to_move}" descendants="${descendants}"/>
		
	</c:forEach>
</ul>
