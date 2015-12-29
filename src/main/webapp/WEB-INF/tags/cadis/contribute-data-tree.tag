<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="originalDataset" required="true" type="sgf.gateway.model.metadata.Dataset" %>
<%@ attribute name="dataset" required="true" type="sgf.gateway.model.metadata.Dataset" %>

	<ul>
	<c:forEach var="dataset" items="${dataset.directlyNestedDatasets}">

		<c:url value="/dataset/${dataset.shortName}.html" var="datasetURL"></c:url>

		<li>
		<c:choose>
			<c:when  test="${originalDataset.shortName == dataset.shortName}">
				${dataset.title} 
			</c:when>
			<c:otherwise>
				${dataset.title}
			</c:otherwise>
		</c:choose>

		<br/>
			<span style="padding-left: 0px; font-size: 85%; color: grey;">
				Last Updated: <fmt:formatDate value="${dataset.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss" />
			</span>
			<br>
			<div style="padding-top: 2px;">
				<c:url var="editURL" value="/dataset/${dataset.shortName}/editDatasetForm.html" />
				<c:url var="responsiblePartyURL" value="/dataset/${dataset.shortName}/responsibleParty.html" />
				<c:url var="uploadURL" value="/dataset/${dataset.shortName}/editfiles.html" />
				<c:url var="moveURL" value="/dataset/${dataset.shortName}/moveDataset.html" />
				<c:url var="datasetDeletion" value="/dataset/${dataset.shortName}/form/confirmDelete.html"/>

				<a href="${datasetURL}" >View</a> | <a href="${editURL}" >Edit</a> | <a href="${responsiblePartyURL}" >Add/Edit Responsible Parties</a> | <a href="${uploadURL}" >Upload Files</a> | <a href="${moveURL}" >Move</a> <authz:authorize access="hasAnyAuthority('group_Root_role_admin')" >| <a href="${datasetDeletion}" >Delete</a></authz:authorize>
			</div>
		</li>

		<cadis:contribute-data-tree originalDataset="${originalDataset}" dataset="${dataset}" /> 

	</c:forEach>
	</ul>
