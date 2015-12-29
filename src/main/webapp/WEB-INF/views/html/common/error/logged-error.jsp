<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="exception-layout">

    <tiles:putAttribute type="string" name="title" value="Internal Server Error"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Internal Server Error"/>

    <tiles:putAttribute name="body">
        <p>
            Our apologies, an error has occurred that we were unable to recover from.
        </p>

        <p>
            The problem has been logged and the administrator notified.
        </p>
    </tiles:putAttribute>

</tiles:insertDefinition>
