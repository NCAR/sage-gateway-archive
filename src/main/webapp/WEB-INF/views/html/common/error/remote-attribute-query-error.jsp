<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Internal Server Error"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Internal Server Error"/>

    <tiles:putAttribute name="body">
        <p>
            Oops, to allow access to the requested dataset, we are required to contact a remote web server (<span
                style="font-weight: bold;">${exception.endpoint.host}</span>) that seems to not be responding at the
            moment. Please try your request again at a later time.
        </p>

        <p>
            The problem has been logged and the administrator notified.
        </p>
    </tiles:putAttribute>

</tiles:insertDefinition>
