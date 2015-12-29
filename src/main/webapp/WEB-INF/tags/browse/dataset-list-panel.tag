<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="title" required="true" type="java.lang.String"  %>
<%@ attribute name="datasets" required="true" type="java.util.Collection"  %>

<div class="panel panel-default">
    <div class="panel-heading">${title}</div>
    <div class="panel-body">
        <p>${fn:length(datasets)} entries</p>
        <c:forEach var='dataset' items="${datasets}">
            <div style="padding-top: 7px">
                <div>
                    <c:set var="dataset_url" value="/dataset/${dataset.shortName}.html" />
                    <a href="<c:url value="${dataset_url}" />" >${dataset.title}</a>
                    <c:choose>
                        <c:when test="${(dataset.nestedDatasetCount == 0) && (dataset.currentDatasetVersion.logicalFileCount == 0)}">
                            <span style="font-style: italic; font-size:85%;" >[Empty]</span>
                        </c:when>
                    </c:choose>
                </div>
                <div style="padding-left:25px;">
                    <c:choose>
                        <c:when test="${fn:length(dataset.description) > 500}">
                            ${fn:substring(dataset.description, 0, 500)}...
                        </c:when>
                        <c:otherwise>
                            ${dataset.description}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div> <!-- .panel-body -->
</div>
