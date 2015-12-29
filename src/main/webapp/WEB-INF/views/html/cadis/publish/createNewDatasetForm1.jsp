<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Create New Dataset - Step 1"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Create New Dataset - Step 1"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="wizardTable">
            <div class="wizardRowGroup">
                <div class="wizardCell wizardSelected">Step 1: Select Enclosing Container</div>
                <div class="wizardCell">Step 2: Select Metadata Source</div>
                <div class="wizardCell">Step 3: Edit Metadata</div>
            </div>
        </div>

        <div>
            <br>
            Select the enclosing Project or Dataset:
            <c:url value="/dataset/${dataset.shortName}/createNewDataset2.html" var="datasetContainerURL">
            </c:url>
            <ul>
                <li><a href="${datasetContainerURL}">${dataset.title}</a><br/>
                    <span style="padding-left: 10px; font-size: 85%; color: grey;">Last Updated: <fmt:formatDate
                            value="${dataset.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </li>
                <cadis:dataset-tree-root1 dataset="${dataset}"/>
            </ul>
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
