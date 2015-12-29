<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout" >

<tiles:putAttribute type="string" name="title" value="Project Award Editor" />
<tiles:putAttribute type="string" name="pageTitle" value="Project Award Editor" />


<tiles:putAttribute name="body">
	
	<common:form-errors commandName="command" />

    <p class="small">
        Required fields are marked with an asterisk <b>*</b>.
    </p>

	<springForm:form method="post" commandName="command" >
		<browse:descriptive-row fieldName="Project" fieldValue="${command.dataset.title}" showEmpty="false" />
		<browse:input-row fieldPath="awardNumber" fieldName="Award Number" isRequired="true" />
		
        <button type="submit" class="btn btn-default" id="addAward">Add Award</button>
	</springForm:form>
	
	</tiles:putAttribute>
	
</tiles:insertDefinition>