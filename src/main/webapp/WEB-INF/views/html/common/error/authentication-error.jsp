<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Account Failuer"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Login Failure"/>

    <tiles:putAttribute name="body">
        <p>
            ${authenticationErrorMessage}
        </p>
    </tiles:putAttribute>

</tiles:insertDefinition>
