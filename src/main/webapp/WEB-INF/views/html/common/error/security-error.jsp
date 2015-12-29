<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Security Error"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Access Denied"/>

    <tiles:putAttribute name="body">
        <p>
            Access Denied
        </p>
    </tiles:putAttribute>

</tiles:insertDefinition>
