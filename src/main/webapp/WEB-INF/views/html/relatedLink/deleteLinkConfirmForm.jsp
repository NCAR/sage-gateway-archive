<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Confirm Delete Related Link"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Delete Related Link"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                Are you sure you want to remove Related Link ${relatedLink.uri}?

                <p>&nbsp;</p>

                <c:url var="deleteRelatedLink"
                       value="/dataset/${dataset.shortName}/relatedLink/${relatedLink.identifier}.html"/>

                <form action="${deleteRelatedLink}" method="POST" style="display: inline;">

                    <input id="relatedLinkId" name="relatedLinkId" type="hidden" value="${relatedLink.identifier}"/>

                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="confirmDelete" value="true"/>
                    <button type="submit" class="btn btn-default" id="confirmDeleteButton">Confirm Delete</button>
                </form>

                <c:url var="cancelDeleteRelatedLink" value="/dataset/${dataset.shortName}/relatedLink.html"/>

                <form action="${cancelDeleteRelatedLink}" method="GET" style="display: inline;">
                    <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
                </form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
