<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout" >
	
	<tiles:putAttribute type="string" name="title" value="Manage Group User" />
	<tiles:putAttribute type="string" name="pageTitle" value="Manage User ${command.userName} in Group ${command.groupName}" />
		
	<tiles:putAttribute name="body">
        <ac:manage-group-user-form command="${command}" />
	</tiles:putAttribute> <%-- body --%>
	
</tiles:insertDefinition>
