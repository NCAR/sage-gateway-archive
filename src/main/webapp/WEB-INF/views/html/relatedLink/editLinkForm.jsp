<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Edit Related Link"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Edit Related Link"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <c:url value="/dataset/${dataset.shortName}/relatedLink/${command.relatedLinkId}" var="formAction"/>

        <p class="small">
            Required fields are marked with an asterisk <b>*</b>.
        </p>

        <springForm:form method="post" commandName="command" action="${formAction}">
            <input id="relatedLinkId" name="relatedLinkId" type="hidden" value="${command.relatedLinkId}"/>

            <browse:descriptive-row fieldName="Dataset" fieldValue="${command.dataset.shortName}" showEmpty="false"/>
            <browse:input-row fieldPath="linkText" fieldName="Description" isRequired="true"/>
            <browse:input-row fieldPath="linkUri" fieldName="Related Link URL" isRequired="true"/>

            <button type="submit" class="btn btn-default" id="updateLink">Update Related Link</button>
        </springForm:form>

    </tiles:putAttribute>

</tiles:insertDefinition>