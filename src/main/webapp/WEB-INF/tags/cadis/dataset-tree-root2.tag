<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="container" required="true" type="sgf.gateway.model.metadata.Dataset" %>
<%@ attribute name="childDatasets" required="true" type="java.util.Collection<sgf.gateway.model.metadata.Dataset>" %>

	<ul>
	<c:forEach var="child" items="${childDatasets}">

		<c:url var="copyAsChildDataset" value="/dataset/${container.shortName}/createNewDataset3.html" >
			<c:param name="template_id" value="${child.shortName}" />
		</c:url>
		<li><a href="${copyAsChildDataset}">${child.title}</a><br/>
			<span style="padding-left: 10px; font-size: 85%; color: grey;">Last Updated: <fmt:formatDate value="${child.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
		</li>

		<cadis:dataset-tree-root2 container="${container}" childDatasets="${child.directlyNestedDatasets}" />

	</c:forEach>
	</ul>
