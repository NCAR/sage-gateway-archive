<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="datasetVersions" required="true" type="java.util.Collection" %>

<c:forEach var="datasetVersion" items="${datasetVersions }">
    <div style="margin-top: 24px;">
        <browse:descriptive-row fieldName="Version" fieldValue="${datasetVersion.versionIdentifier}" showEmpty="false" />
        <browse:descriptive-row fieldName="Date" fieldValue="${datasetVersion.dateCreated}" showEmpty="false" />
        <browse:descriptive-row fieldName="Publisher" fieldValue="${datasetVersion.publisher.firstName} ${datasetVersion.publisher.lastName}" showEmpty="false" />
        <browse:descriptive-row fieldName="Published State" fieldValue="${datasetVersion.publishedState.name}" showEmpty="false" />
        <browse:descriptive-row fieldName="Source" fieldValue="${datasetVersion.authoritativeSourceURI}" showEmpty="false" />
    </div>
</c:forEach>