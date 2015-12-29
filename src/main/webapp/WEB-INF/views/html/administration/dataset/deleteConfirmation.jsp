<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Delete Dataset Confirmation"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Delete Dataset Confirmation"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    Are you sure you want to delete this Dataset?
                </p>
                <p><em>Note: <strong>Delete cannot be undone.</strong></em></p>
                Anything under this Dataset will also be deleted, including child Datasets, files, and all metadata.
                <p>&nbsp;</p>
                This cannot be undone!
                <p>&nbsp;</p>
                <form method="POST" style="display: inline;">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="deleteConfirmed" value="true"/>
                    <button type="submit" class="btn btn-default" id="confirmDeleteButton">Confirm Delete</button>
                </form>

                <c:url var="cancelDeleteDataset" value="/dataset/${dataset.shortName}.html"/>
                <form action="${cancelDeleteDataset}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>