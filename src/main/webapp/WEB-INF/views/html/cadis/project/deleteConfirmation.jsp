<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Delete Project Confirmation"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Delete Project Confirmation"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    Are you sure you want to delete this Project?
                </p>
                <p>&nbsp;</p>
                <p>
                    Deleting a Project cannot be undone.</p>
                <p>
                    Anything under this Project will also be deleted, including child Datasets, files, and all metadata.
                </p>
                <p>
                    This cannot be undone!
                </p>
                <p>&nbsp;</p>
                <form method="POST" style="display: inline;">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="deleteConfirmed" value="true"/>
                    <button type="submit" class="btn btn-default" id="confirmDeleteButton">Confirm Delete</button>
                </form>

                <c:url var="cancelDeleteProject" value="/project/${dataset.shortName}.html"/>
                <form action="${cancelDeleteProject}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>