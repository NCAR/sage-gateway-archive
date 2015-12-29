<%--
   ESG Dataset page
--%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Dataset: ${dataset.title}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Dataset"/>

    <tiles:putAttribute name="links">
        <link rel="self" type="text/html" href="<c:url value="/dataset/id/${dataset.identifier}.html"/>"/>
        <link rel="self" type="text/dif" href="<c:url value="/dataset/${dataset.shortName}.dif"/>"/>
        <link rel="self" type="text/iso19139" href="<c:url value="/dataset/${dataset.shortName}.iso19139"/>"/>
        <link rel="alternate" type="application/atom+xml" title="${dataset.title} Version Feed" href="${feedUri}"/>
    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <c:if test="${dataset.retracted}">
            <div class="retracted_dataset">
                <span>COLLECTION RETRACTED</span>
            </div>
        </c:if>

        <div class="dataset_parent_hierarchy">

            <gateway:datasetParentHierarchy parentList="${parentList}"/>

        </div>

        <div class="dataset_name">
            <h1>${dataset.title}</h1>
        </div>

        <ul class="nav nav-tabs">
            <li class="active" role="presentation"><a href="#summaryTab" data-toggle="tab"><em>Summary</em></a></li>
            <c:if test="${not empty dataset.currentDatasetVersion.variables }">
                <li role="presentation"><a href="#variablesTab" data-toggle="tab"><em>Variables</em></a></li>
            </c:if>
            <li role="presentation"><a href="#historyTab" data-toggle="tab"><em>History</em></a></li>

            <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')" >
                <li role="presentation"><a href="#adminTab" data-toggle="tab"><em>Edit</em></a></li>
            </authz:authorize>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="summaryTab">
                <browse:dataset-summary-tab-content dataset="${dataset}"/>
            </div>
            <c:if test="${not empty dataset.currentDatasetVersion.variables }">
                <div class="tab-pane" id="variablesTab">
                    <%--
                        Variables can't be entered directly through the UI,
                        but can be added to the gateway via other means.
                    --%>
                    <browse:geodataset-variable-tab-content
                            variables="${dataset.currentDatasetVersion.variables}"/>
                </div>
            </c:if>
            <div class="tab-pane" id="historyTab">
                <browse:dataset-history-tab-content datasetVersions="${datasetVersions}"/>
            </div>
            <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')" >
                <div class="tab-pane" id="adminTab">
                    <browse:dataset-admin-tab-content dataset="${dataset}"/>
                </div>
            </authz:authorize>
        </div> <!-- .tab-content -->
        <br> <!-- visual whitespace -->


    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
