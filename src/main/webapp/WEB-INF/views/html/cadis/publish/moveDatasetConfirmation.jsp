<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Confirm Move Dataset"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Move Dataset"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                Are you sure you want to move Dataset: <br><br>
                <span style="margin: 10px 20px;">${currentDataset.title}</span><br><br>
                To new enclosing container: <br><br>
                <span style="margin: 20px;">${parentDataset.title}</span>
                <br><br>
                Note:<br><br>
                Everything contained within this Dataset will also be moved, including data files.<br>
                <br>

                <c:url var="moveDataset"
                       value="/dataset/${currentDataset.shortName}/setParent/${parentDataset.shortName}.html"/>
                <form action="${moveDataset}" method="POST" style="display: inline;">
                    <input type="hidden" name="moveConfirmed" value="true"/>
                    <button type="submit" class="btn btn-default" id="confirmMoveButton">Confirm Move</button>
                </form>

                <c:url var="cancelMove" value="/dataset/${currentDataset.shortName}.html"/>
                <form action="${cancelMove}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>