<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Confirm Remove Contact"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Remove Contact"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                Are you sure you want to remove this contact?: ${contact.firstName} ${contact.lastName}

                <p>&nbsp;</p>

                <c:url var="removeContact"
                       value="/project/${project.persistentIdentifier}/contact/${contact.identifier}/type/${contactType}.html"/>
                <form action="${removeContact}" method="POST" style="display: inline;">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="removeConfirmed" value="true"/>

                    <button type="submit" class="btn btn-default" id="confirmRemoveButton">Confirm Delete</button>
                </form>

                <c:url var="cancelRemoveFile" value="/project/${project.persistentIdentifier}/contact.html"/>
                <form action="${cancelRemoveFile}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
