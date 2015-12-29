<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="User Account Summary"/>
    <tiles:putAttribute type="string" name="pageTitle" value="User Account Summary"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>
        <ac:user-info user="${user}"/>

    </tiles:putAttribute>

</tiles:insertDefinition>
