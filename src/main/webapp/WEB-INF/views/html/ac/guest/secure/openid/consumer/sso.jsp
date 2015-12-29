<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ page import="sgf.gateway.web.controllers.security.openid.OpenidParameters" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Login"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Login"/>

    <tiles:putAttribute name="body">

        <authz:authorize access="!hasRole('group_User_role_default')">

            <common:form-errors commandName="command"/>
            <common:login-errors/>

            <div class="panel panel-default">
                <div class="panel-body">
                    <c:url value="/login/openid" var="openidURL"/>
                    <springForm:form method="post" id="openidForm" action="${openidURL}">
                        <browse:input-row fieldName="Enter your OpenID"
                                          fieldPath="<%= OpenidParameters.OPENID_IDENTIFIER %>"/>
                        <button type="submit" class="btn btn-default">Login</button>
                        <br><br>
                        <div>
                            Your <a href="http://en.wikipedia.org/wiki/OpenID">OpenID</a> is a URL.
                            Example: <i>${exampleOpenID}</i>
                        </div>
                        <div style="text-align: left;">
                            <spring:message code="messages.security.registration.link" var="registrationLink"/>
                            <div style="padding-bottom: 3px; padding-top: 20px;">Need an account: <a
                                    href="<c:url value="${registrationLink}"/>">Register</a></div>
                            <div style="padding-bottom: 3px;">
                                Forgot your OpenID: <a
                                    href="<c:url value="/ac/guest/secure/emailOpenid.htm"/>">Retrieve it</a>
                            </div>
                            <div>
                                Forgot your password: <a
                                    href="<c:url value="/ac/guest/secure/emailPassword.html"/>">Get a New One</a>
                            </div>
                        </div>
                    </springForm:form>
                </div> <!-- .panel-body -->
            </div>
        </authz:authorize>

        <authz:authorize access="hasRole('group_User_role_default')">
            <p>
                You are already logged in as: <c:out value="${user.openid}"/>
            </p>

            <p>
                Please <a href="<c:url value="/logout" />">Logout</a> if you wish to login as a different User.
            </p>
        </authz:authorize>
    </tiles:putAttribute>

</tiles:insertDefinition>