<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout" >
	
	<tiles:putAttribute type="string" name="title" value="Manage Group User Submitted" />
	<tiles:putAttribute type="string" name="pageTitle" value="Manage User ${command.userName} in Group ${command.groupName}" />
	
	<tiles:putAttribute name="body">
		<ac:manage-group-user-submitted command="${command}" />
	</tiles:putAttribute>
	
</tiles:insertDefinition>
