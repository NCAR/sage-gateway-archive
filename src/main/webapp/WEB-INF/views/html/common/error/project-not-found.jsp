<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="404 Not Found"/>
    <tiles:putAttribute type="string" name="pageTitle" value="404 Not Found"/>

    <tiles:putAttribute name="body">
        <p>
            The Project with the identifier '${exception.identifier}' could not be found.
        </p>
    </tiles:putAttribute>

</tiles:insertDefinition>
