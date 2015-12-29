<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="API Documentation"/>
    <tiles:putAttribute type="string" name="pageTitle" value="API Documentation"/>

    <tiles:putAttribute name="body">

        <p><a href="<c:url value="/documentation/oai.html"/>">OAI-PMH</a></p>

        <p><a href="<c:url value="/documentation/api/fileUpload.html"/>">File Upload</a></p>

    </tiles:putAttribute>

</tiles:insertDefinition>