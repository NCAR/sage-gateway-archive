<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Edit DOI (Digital Object Identifier)"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Edit DOI (Digital Object Identifier)"/>


    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <p class="small">
            Required fields are marked with an asterisk <b>*</b>.
        </p>

        <c:url var="formAction" value="/dataset/${dataset.shortName}/doi/form/edit.html"/>
        <springForm:form method="post" commandName="command" action="${formAction}">
            <browse:input-row fieldPath="creator" fieldName="Creator" isRequired="true"/>
            <browse:input-row fieldPath="title" fieldName="Title" isRequired="true"/>
            <browse:descriptive-row fieldValue="${command.publisher}" fieldName="Publisher" showEmpty="true"/>
            <browse:input-row fieldPath="publicationYear" fieldName="Publication Year" isRequired="true"/>
            <browse:descriptive-row fieldValue="${command.resourceType}" fieldName="Resource Type" showEmpty="true"/>

            <button type="submit" class="btn btn-default" id="editButton">Edit DOI</button>
        </springForm:form>

    </tiles:putAttribute>

</tiles:insertDefinition>