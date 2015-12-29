<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Add Related Link"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Add Related Link"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <p class="small">
            Required fields are marked with an asterisk <b>*</b>.
        </p>

        <springForm:form method="POST" commandName="command">
            <springForm:hidden path="relatedLinkId"/>
            <browse:descriptive-row fieldName="Dataset" fieldValue="${command.dataset.shortName}" showEmpty="false"/>
            <browse:input-row fieldPath="linkText" fieldName="Description" isRequired="true"/>
            <browse:input-row fieldPath="linkUri" fieldName="Related Link" isRequired="true"/>

            <button type="submit" class="btn btn-default" id="addLink">Add Related Link</button>
        </springForm:form>

    </tiles:putAttribute>

</tiles:insertDefinition>