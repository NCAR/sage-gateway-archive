<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Registration: Account Confirmed"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Registration: Account Confirmed"/>

    <tiles:putAttribute name="body">
        <div align="center">

            <!-- Registration confirmation -->
            <p>&nbsp;</p>
            Your account has been confirmed. You will now be able to login.<br/>

            <p>&nbsp;</p>
            <a href="<c:url value="${loginForm}"/>" id="login-button">Login Now</a>

        </div>
    </tiles:putAttribute>

</tiles:insertDefinition>
