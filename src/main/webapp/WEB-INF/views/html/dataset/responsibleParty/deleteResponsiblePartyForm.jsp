<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Confirm Delete Responsible Party"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Delete Responsible Party"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                Are you sure you want to delete the Responsible Party: ${responsibleParty.individualName}

                <p>&nbsp;</p>

                <c:url var="deleteResponsibleParty"
                       value="/dataset/${dataset.shortName}/responsibleParty/${responsibleParty.identifier}.html"/>
                <form action="${deleteResponsibleParty}" method="POST" style="display: inline;">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="confirmDelete" value="true"/>
                    <button type="submit" class="btn btn-default" id="deleteButton">Confirm Delete</button>
                </form>

                <c:url var="cancelDeleteResponsibleParty" value="/dataset/${dataset.shortName}/responsibleParty.html"/>
                <form action="${cancelDeleteResponsibleParty}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
