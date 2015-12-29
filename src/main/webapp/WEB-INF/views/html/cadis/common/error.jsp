<%@ include file="/WEB-INF/views/html/common/include.jsp"%>

<tiles:insertDefinition name="general-layout">
	
	<tiles:putAttribute type="string" name="title" value="Error" />
	<tiles:putAttribute type="string" name="pageTitle" value="Error" />
	
	<tiles:putAttribute name="body">

		<c:out value="${errorText}"/> 
		
	</tiles:putAttribute>
	
</tiles:insertDefinition>
