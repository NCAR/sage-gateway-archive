<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="false" type="sgf.gateway.model.metadata.Dataset" %>

<div>
    <c:choose>
        <c:when test="${dataset.brokered}">
            <p>
                This Project is brokered and may not be edited from this site.
            </p>

            <p>
                Please see the following link for this Project's authoritative source:
            </p>

            <p>
                <c:url var="redirectURL" value="/redirect.html">
                    <c:param name="link" value="${dataset.currentDatasetVersion.authoritativeSourceURI}"/>
                </c:url>
                <a href="${redirectURL}">${dataset.currentDatasetVersion.authoritativeSourceURI}</a>
            </p>

            <p>
                <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                    <c:url var="projectDeletion" value="/project/${dataset.shortName}/form/confirmDelete.html"/>
                    <a href="${projectDeletion}">Delete this Project</a>
                </authz:authorize>
            </p>

        </c:when>
        <c:otherwise>
            <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                <p>
                    <c:url var="editProjectLink" value="/project/${dataset.shortName}/form/edit.html"/>
                    <a href="${editProjectLink}">Edit Metadata</a>
                </p>
                <p>
                    <a href="<c:url value="/project/${dataset.shortName}/responsibleParty.html" />">Add/Edit
                        Responsible Parties (Authors, PIs, Contacts, etc.)</a>
                </p>
                <p>
                    <c:url var="awardLink" value="/project/${dataset.shortName}/awardNumber.html"/>
                    <a href="${awardLink}">Add/Delete Award Numbers</a>
                </p>
                <p>
                    <c:url var="datasetPermissions" value="/ac/root/datasetPermissions.html">
                        <c:param name="datasetId" value="${dataset.identifier}"/>
                    </c:url>
                    <a href="${datasetPermissions}">Add write permissions to this Project</a>
                </p>
            </authz:authorize>

            <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')">
                <p>
                    <c:url var="createChildDataset"
                           value="/dataset/${dataset.rootParentDataset.shortName}/createNewDataset1.html"/>
                    <a href="${createChildDataset}">Create new Dataset within this Project</a>
                </p>

                <c:if test="${fn:length(dataset.directlyNestedDatasets) > 1}">
                    <p>
                        <c:url var="orderDatasets" value="/project/${dataset.shortName}/form/order.html"/>
                        <a href="${orderDatasets}">Order Datasets</a>
                    </p>
                </c:if>
            </authz:authorize>

            <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                <p>
                    <c:url var="deleteProjectLink" value="/project/${dataset.shortName}/form/confirmDelete.html"/>
                    <a href="${deleteProjectLink}">Delete this Project</a>
                </p>
            </authz:authorize>

        </c:otherwise>
    </c:choose>
</div>