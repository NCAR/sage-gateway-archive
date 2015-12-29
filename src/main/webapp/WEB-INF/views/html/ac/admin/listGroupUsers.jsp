<%@ include file="/WEB-INF/views/html/common/include.jsp"%>

<tiles:insertDefinition name="general-layout">
	
	<tiles:putAttribute type="string" name="title" value="Manage Group Users" />
	<tiles:putAttribute type="string" name="pageTitle" value="Manage Users for Group ${command.groupName}" />
	
	<tiles:putAttribute name="body">
	
		<common:form-errors commandName="command"/>
		<p>&nbsp;</p>
		<ac:list-group-users command="${command}" />
		
	</tiles:putAttribute> <%-- body --%>
	
</tiles:insertDefinition>
