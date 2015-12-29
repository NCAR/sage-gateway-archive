<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Investigator: ${contact}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Investigator: ${contact}"/>

    <tiles:putAttribute name="body">

        <p>${fn:length(datasets)} entries</p>
        <c:forEach var="dataset" items="${datasets}">
            <div style="padding-top: 10px;">
                <c:if test="${dataset.containerType == 'DATASET'}">
                    <a href="<c:url value="/dataset/${dataset.shortName}.html" />">${dataset.title}</a>
                </c:if>
                <c:if test="${dataset.containerType== 'PROJECT'}">
                    <a href="<c:url value="/project/${dataset.shortName}.html" />">${dataset.title}</a>
                </c:if>
                <div class="containerType">
                    <c:out value="${dataset.containerType}"/>
                </div>
                <div class="result">
                    <div class="description">
                        <gateway:Abbreviate maxWidth="250" value="${dataset.description}"/>
                    </div>
                </div>
            </div>
        </c:forEach>
        <br>

    </tiles:putAttribute>

</tiles:insertDefinition>
