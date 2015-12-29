<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Confirm Delete Award"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Delete Award"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                Are you sure you want to delete Award Number: ${awardNumber}

                <p>&nbsp;</p>

                <c:url var="removeAward"
                       value="/project/${dataset.shortName}/awardNumber/${gateway:urlEncode(awardNumber)}.html"/>
                <form action="${removeAward}" method="POST" style="display: inline;">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="deleteConfirmed" value="true"/>
                    <button type="submit" class="btn btn-default" id="confirmDeleteButton">Confirm Delete</button>
                </form>

                <c:url var="cancelRemoveAward" value="/project/${dataset.shortName}/awardNumber.html"/>
                <form action="${cancelRemoveAward}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
