<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="datasets" required="true" type="java.util.Collection" %>
<%@ attribute name="expandProjects" required="false" type="java.lang.String" %>

<div>
    <p>${fn:length(datasets)} entries</p>
    <c:forEach var="dataset" items="${datasets}">

        <div style="padding-bottom: 20px">
            <div>
                <a href="<c:url value="/dataset/${dataset.shortName}.html" />">${dataset.title}</a>
            </div>
            <div style="padding-left: 20px; font-size: 85%; color: grey;">
                <c:out value="${dataset.containerType}"/>
                <c:if test="${dataset.metadataOnly}">
                    [Metadata only]
                </c:if>
            </div>
            <div style="padding-left:20px;">
                <gateway:Abbreviate maxWidth="500" value="${dataset.description}"/>
            </div>
        </div>

    </c:forEach>
</div>