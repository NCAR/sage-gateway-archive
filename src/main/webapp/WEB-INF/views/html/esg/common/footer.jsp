<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<div id="text_menu_div">
    <a href="<c:url value="/" />">Home</a> |
    <a href="<c:url value="/search.html" />">Search</a> |
    <a href="<c:url value="/project.html" />">Projects</a> |
    <a href="<c:url value="/account/user/index.html" />">Account</a> |
    <authz:authorize access="hasAuthority('group_Root_role_admin')">
        <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
        <a href="<c:url value="${gatewayAdministrationLink }" />">Admin</a> |
    </authz:authorize>
    <a href="<c:url value="/about/overview.htm" />">About</a> |
    <a href="<c:url value="/contactus/contact-us.htm" />">Contact Us</a> |
    <a href="<c:url value="/feedback.html" />">Feedback</a> |
    <a href="<c:url value="/legal/privacy_policy.htm" />">Privacy Policy</a> |
    <a href="<c:url value="/legal/terms_of_use.htm" />">Terms of Use</a> |
    <authz:authorize access="!hasAuthority('group_User_role_default')">
        <a href="<c:url value="/ac/guest/secure/sso.html" />">Login</a>
    </authz:authorize>
    <authz:authorize access="hasAuthority('group_User_role_default')">
        <a href="<c:url value="/logout" />">Logout</a>
    </authz:authorize>
</div>

<div id="user_div">
    User: <c:out value="${user.openid}"/>
</div>
<div id="software_version_div">
    Website powered by Science Gateway Framework (version <c:out value="${gateway.versionStr}"/>)
</div>