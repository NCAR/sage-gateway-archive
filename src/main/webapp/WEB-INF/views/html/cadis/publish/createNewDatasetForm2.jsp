<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Create New Dataset - Step 2"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Create New Dataset - Step 2"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="wizardTable">
            <div class="wizardRowGroup">
                <div class="wizardCell">Step 1: Select Enclosing Container</div>
                <div class="wizardCell wizardSelected">Step 2: Select Metadata Source</div>
                <div class="wizardCell">Step 3: Edit Metadata</div>
            </div>
        </div>

        <div>
            <br>
            Enter new Metadata:<br>
            <c:url var="createChildDataset" value="/dataset/${dataset.shortName}/createNewDataset3.html"/>
            <ul>
                <li><a href="${createChildDataset}">Blank, no copied Metadata</a></li>
            </ul>
        </div>
        <div>
            <br>
            Or, choose Metadata to copy into your new Dataset:
            <ul>
                <c:url var="copyAsChildDataset" value="/dataset/${dataset.shortName}/createNewDataset3.html">
                    <c:param name="template_id" value="${rootDataset.shortName}"/>
                </c:url>
                <li><a href="${copyAsChildDataset}">${rootDataset.title}</a><br/>
                    <span style="padding-left: 10px; font-size: 85%; color: grey;">Last Updated: <fmt:formatDate
                            value="${rootDataset.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </li>
                <cadis:dataset-tree-root2 container="${dataset}"
                                          childDatasets="${rootDataset.directlyNestedDatasets}"/>
            </ul>
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
