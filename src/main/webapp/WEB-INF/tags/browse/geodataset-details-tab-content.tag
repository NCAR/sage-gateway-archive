<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@tag import="sgf.gateway.model.metadata.ResolutionType"%>
<%@tag import="sgf.gateway.model.metadata.DataProductType"%>
<%@tag import="sgf.gateway.model.metadata.DataType"%>
<%@tag import="sgf.gateway.model.metadata.CadisResolutionType"%>

<%@ attribute name="dataset" required="false" type="sgf.gateway.model.metadata.Dataset"  %>

<browse:descriptive-row fieldName="Grid" fieldValue="${gridString}" showEmpty="false" />
<browse:descriptive-row fieldName="Physical Domain" fieldValue="${dataset.descriptiveMetadata.physicalDomain.name}" showEmpty="false" />

<c:set var="timeFrequencyString">
    <c:forEach items="${dataset.descriptiveMetadata.timeFrequencies}" var="timeFrequency">
        ${timeFrequency.name}
        <c:if test="${fn:length(timeFrequency.description) > 0}">
            - ${timeFrequency.description}
        </c:if>
        <br />
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Time Frequency(ies)" fieldValue="${timeFrequencyString}" showEmpty="false" />

<c:set var="locationsString">
    <c:forEach items="${dataset.descriptiveMetadata.locations}" var="location">
        ${location.name}<br />
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Location(s)" fieldValue="${locationsString}" showEmpty="false" />

<c:set var="unknownCadisResolution">
    <%=CadisResolutionType.RESOLUTION_UNKNOWN %>
</c:set>
<c:set var="cadisResolutionString">
    <c:forEach var="cadisResolutionType" items="${dataset.descriptiveMetadata.resolutionTypes}">
        <c:if test="${cadisResolutionType != unknownCadisResolution}">
            ${cadisResolutionType.longName}<br />
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Resolution(s)" fieldValue="${cadisResolutionString}" showEmpty="false" />

<c:set var="unknownResolution">
    <%=ResolutionType.UNKNOWN %>
</c:set>
<c:set var="resolutionString">
    <c:if test="${dataset.descriptiveMetadata.resolutionType != unknownResolution}">
        ${dataset.descriptiveMetadata.resolutionType.name}
    </c:if>
</c:set>
<browse:descriptive-row fieldName="Resolution" fieldValue="${resolutionString}" showEmpty="false" />

<browse:descriptive-row fieldName="Data Product Type" fieldValue="${dataset.descriptiveMetadata.dataProductType.name}" showEmpty="false" />

<c:set var="unknownDataType">
    <%=DataType.UNKNOWN %>
</c:set>
<c:set var="dataTypeString">
    <c:forEach var="dataType" items="${dataset.descriptiveMetadata.dataTypes}">
        <c:if test="${dataType != unknownDataType}">
            ${dataType.longName}<br />
        </c:if>
    </c:forEach>
</c:set>
<browse:descriptive-row fieldName="Data Type(s)" fieldValue="${dataTypeString}" showEmpty="false" />
