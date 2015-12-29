<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Project: ${project.name}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Project: ${project.name}"/>

    <tiles:putAttribute name="links">
        <link rel="alternate" type="application/atom+xml" title="${project.name} Feed"
              href="<c:url value="/project/${project.persistentIdentifier}/dataset.atom" />"/>
    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <ul class="nav nav-tabs">
            <li class="active" role="presentation"><a href="#tab1" data-toggle="tab"><em>Summary</em></a></li>
            <li role="presentation"><a href="#tab2" data-toggle="tab"><em>Edit</em></a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tab1">
                <c:set var="description">${gateway:formatReturnToBreak(project.description)}</c:set>
                <browse:descriptive-row fieldName="Description" fieldValue="${description}" showEmpty="false"/>

                <c:if test="${not empty project.projectURI}">
                    <c:url var="redirect" value="/redirect.html" >
                        <c:param name="link" value="${project.projectURI}" />
                    </c:url>
                    <c:set var="projectURI">
                        <a href="${redirect}"/>${project.projectURI}</a>
                    </c:set>
                </c:if>

                <browse:descriptive-row fieldName="Homepage" fieldValue="${projectURI}" showEmpty="false"/>
                <c:if test="${project.associatedDatasetCount > 0}">
                    <browse:dataset-list-panel datasets="${gateway:filterPublished(project.associatedDatasets)}"
                                               title="Child Datasets"/>
                </c:if>
            </div>

            <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                <div class="tab-pane" id="tab2">

                    <p>&nbsp;</p>
                    <c:url var="createDataset" value="/publish/createDefaultDataset.html">
                        <c:param name="activityId" value="${project.identifier}"/>
                    </c:url>
                    <a href="${createDataset}">Publish new top level collection for this project</a>

                </div>
            </authz:authorize>
        </div>
        <br> <!-- visual whitespace -->

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
