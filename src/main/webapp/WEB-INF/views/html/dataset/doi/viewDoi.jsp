<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="DOI (Digital Object Identifier)"/>
    <tiles:putAttribute type="string" name="pageTitle" value="DOI (Digital Object Identifier)"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div>
            <c:url var="datasetLink" value="/dataset/${dataset.shortName}.html"></c:url>
            <a href="${datasetLink}">${dataset.title}</a>
            <br/>
        </div>

        <div>
            <br/>
            <c:choose>
                <c:when test="${empty dataset.DOI}">
                    <a href="<c:url value="/dataset/${dataset.shortName}/doi/form/add.html" />">Add New DOI</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/dataset/${dataset.shortName}/doi/form/edit.html" />">Edit DOI</a>
                </c:otherwise>
            </c:choose>
            <br/>
        </div>

        <c:if test="${not empty dataset.DOI}">
            <br/>
            <c:set var="doi_permanent_link"><a
                    href="http://dx.doi.org/${dataset.DOI}">http://dx.doi.org/${dataset.DOI}</a></c:set>
            <c:set var="ezid_citation_link"><a
                    href="http://ezid.cdlib.org/id/${dataset.DOI}">http://ezid.cdlib.org/id/${dataset.DOI}</a></c:set>
            <browse:descriptive-row showEmpty="true" fieldValue="${dataset.DOI}"
                                    fieldName="DOI"></browse:descriptive-row>
            <browse:descriptive-row showEmpty="true" fieldValue="${doi_permanent_link}"
                                    fieldName="Permanent Link"></browse:descriptive-row>
            <browse:descriptive-row showEmpty="true" fieldValue="${ezid_citation_link}"
                                    fieldName="Citation Link"></browse:descriptive-row>
        </c:if>

    </tiles:putAttribute>

</tiles:insertDefinition>