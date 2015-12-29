<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="originalDataset" required="true" type="sgf.gateway.model.metadata.Dataset" %>
<%@ attribute name="dataset" required="true" type="sgf.gateway.model.metadata.Dataset" %>

	<ul>
	<c:forEach var="dataset" items="${dataset.directlyNestedDatasets}">

		<c:url value="/dataset/${dataset.shortName}.html" var="datasetURL"></c:url>

			<li>
			<c:choose>
				<c:when  test="${originalDataset.shortName == dataset.shortName}">
					<a href="${datasetURL}" style="font-size: large; color: blue; font-weight: bold;" >${dataset.title}</a> 
				</c:when>
				<c:otherwise>
					<a href="${datasetURL}">${dataset.title}</a>
				</c:otherwise>
			</c:choose>

			<br/>
				<span style="padding-left: 10px; font-size: 85%; color: grey;">
					Last Updated: <fmt:formatDate value="${dataset.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss" />
				</span>
				<br>
				<div style="padding-top: 0px;">
				<c:url var="editURL" value="/dataset/${dataset.shortName}/editDatasetForm.html" />
			
				</div>
			</li>

		<cadis:dataset-tree originalDataset="${originalDataset}" dataset="${dataset}" /> 

	</c:forEach>
	</ul>
