<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Move Dataset"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Move Dataset"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="wizardCell wizardSelected wizardRowGroup">Select Enclosing Container</div>
        <br>

        <div class="panel panel-default">
            <div class="panel-heading">Click below to select the new enclosing Project or Dataset</div>
            <div class="panel-body">
                <%--Include project option --%>
                <c:url value="/dataset/${rootDataset.shortName}/moveDatasetConfirmation.html" var="datasetContainerURL">
                    <c:param name="dataset_to_move_name" value="${datasetToMove.shortName}" />
                </c:url>
                <ul>
                    <li><a href="${datasetContainerURL}">${rootDataset.title}</a><br/>
                        <span style="padding-left: 10px; font-size: 85%; color: grey;">Last Updated: <fmt:formatDate value="${rootDataset.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                    </li>
                    <cadis:move-dataset-tree-root dataset="${rootDataset}" dataset_to_move="${datasetToMove}" descendants="${descendants}"/>
                </ul>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
