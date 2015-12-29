<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Confirm Delete File"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Delete File"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                Are you sure you want to delete the file: ${file}
                <br><br>
                Note: Delete cannot be undone.
                <br><br>

                <c:url var="deleteFile" value="/dataset/${dataset.shortName}/file/${file}.html"/>
                <form action="${deleteFile}" method="POST" style="display: inline;">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="deleteConfirmed" value="true"/>

                    <button type="submit" class="btn btn-default" id="confirmDeleteButton">Confirm Delete</button>
                </form>

                <c:url var="cancelDeleteFile" value="/dataset/${dataset.shortName}/editfile.html"/>
                <form action="${cancelDeleteFile}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
