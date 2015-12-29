<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="selected_page" required="false" type="java.lang.String"  %>

<ul>
	<c:url var="changePasswordLink" value="/ac/user/index.html" />
	<cadis:navigator-item link="${changePasswordLink}" text="Summary" selected="${selected_page eq 'SUMMARY' }" />

	<c:url var="changeEmailLink" value="/ac/user/changeEmail.htm" />
	<cadis:navigator-item link="${changeEmailLink}" text="Change Email Address" selected="${selected_page eq 'CHANGE_EMAIL' }" />

	<c:url var="changePasswordLink" value="/ac/user/secure/changePassword.html" />
	<cadis:navigator-item link="${changePasswordLink}" text="Change Password" selected="${selected_page eq 'CHANGE_PASSWORD' }" />

	<c:url var="currentMembershipLink" value="/ac/user/listUserGroups.html" />
	<cadis:navigator-item link="${currentMembershipLink}" text="Current Memberships" selected="${selected_page eq 'CURRENT_MEMBERSHIPS' }" />
</ul>
