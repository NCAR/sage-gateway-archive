<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="404 Not Found"/>
    <tiles:putAttribute type="string" name="pageTitle" value="404 Not Found"/>

    <tiles:putAttribute name="body">
        <p>
            The following file was not found:
        <ul>
            <li>${exception.file.name}</li>
        </ul>
        </p>
        <p>
            If this file was part of a Archive Storage request, the file might have been removed from our cache. Please
            consider resubmitting your Archive Storage request.
        </p>
    </tiles:putAttribute>

</tiles:insertDefinition>
