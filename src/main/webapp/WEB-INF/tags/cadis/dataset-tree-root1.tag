<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="true" type="sgf.gateway.model.metadata.Dataset" %>

	<ul>
	<c:forEach var="dataset" items="${dataset.directlyNestedDatasets}">
		<c:url value="/dataset/${dataset.shortName}/createNewDataset2.html" var="datasetContainerURL"></c:url>
		<li><a href="${datasetContainerURL}">${dataset.title}</a><br/>
			<span style="padding-left: 10px; font-size: 85%; color: grey;">Last Updated: <fmt:formatDate value="${dataset.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
		</li>
		<cadis:dataset-tree-root1 dataset="${dataset}" />
	</c:forEach>
	</ul>
