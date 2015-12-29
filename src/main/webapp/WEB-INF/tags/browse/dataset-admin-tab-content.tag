<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="false" type="sgf.gateway.model.metadata.Dataset"  %>

<div class="panel panel-default">
    <div class="panel-heading">Access Control</div>
    <div class="panel-body">
        <table class="table table-condensed table-bordered table-striped">
            <thead>
                <tr>
                    <th>Groups authorized for Reading</th>
                    <th>Groups authorized for Writing</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <c:forEach items="${dataset.readGroups}" var="group">
                            <c:out value="${group.description}"/><br/>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach items="${dataset.writeGroups}" var="group">
                            <c:out value="${group.description}"/><br/>
                        </c:forEach>
                    </td>
                </tr>
            </tbody>
        </table>
    </div> <!-- .panel-body -->
</div>

<c:choose>
    <c:when test="${dataset.brokered}">
        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    This dataset is brokered and may not be edited from this site.
                </p>
                <p>
                    Please see the following link for this dataset's authoritative source:
                </p>
                <p>
                    <a href="${dataset.currentDatasetVersion.authoritativeSourceURI}">${dataset.currentDatasetVersion.authoritativeSourceURI}</a>
                </p>
            </div> <!-- .panel-body -->
        </div>
        <authz:authorize access="hasAnyAuthority('group_Root_role_admin')" >
            <div class="panel panel-default">
                <div class="panel-heading">Curation</div>
                <div class="panel-body">
                    <p>
                        <a href="<c:url value="/dataset/${dataset.shortName}/form/confirmDelete.html" />" >Delete this Dataset</a>
                    </p>
                    <p>
                        <c:url var="pushToShare" value="/dataset/${dataset.shortName}/pushToShare.html" />
                        <a href="${pushToShare}">Push Dataset to SHARE site</a>
                        - Push this dataset to the Open Science Framework SHARE site.
                    </p>
                </div> <!-- .panel-body -->
            </div>
        </authz:authorize>
    </c:when>
    <c:otherwise>
        <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')" >
            <div class="panel panel-default">
                <div class="panel-heading">Publishing</div>
                <div class="panel-body">
                    <p>
                        <c:url var="createChildDataset" value="/publish/createDefaultDataset.html">
                            <c:param name="datasetId" value="${dataset.identifier}" />
                        </c:url>
                        <a href="${createChildDataset}">Publish new child collection</a>
                        - Create an empty child dataset with minimal metadata.
                    </p>
                    <p>
                        <c:url var="editDataset" value="/publish/editDefaultDatasetMetadata.htm">
                            <c:param name="datasetId" value="${dataset.identifier}" />
                        </c:url>
                        <a href="${editDataset}" >Edit this dataset's metadata</a>
                        - Populate the detailed metadata fields for the current dataset.
                    </p>
                    <p>
                        <a href="<c:url value="/dataset/${dataset.shortName}/editfiles.html" />" >Upload/Delete Data Files</a>
                        - Upload files into this dataset.
                    </p>
                    <p>
                        <a href="<c:url value="/dataset/${dataset.shortName}/relatedLink.html" />" >Add/Edit Related Links</a>
                        - Add or Edit Related Links to this dataset.
                    </p>
                    <p>
                        <c:url var="orderDatasets" value="/dataset/${dataset.shortName}/form/order.html" />
                        <a href="${orderDatasets}">Order Datasets</a>
                        - Change the order of the nested datasets.
                    </p>
                    <p>
                        <c:url var="pushToShare" value="/dataset/${dataset.shortName}/pushToShare.html" />
                        <a href="${pushToShare}">Push Dataset to SHARE site</a>
                        - Push this dataset to the Open Science Framework SHARE site.
                    </p>
                </div> <!-- .panel-body -->
            </div>
        </authz:authorize>
        <authz:authorize access="hasAnyAuthority('group_Root_role_admin')" >
            <div class="panel panel-default">
                <div class="panel-heading">Curation</div>
                <div class="panel-body">
                    <p>
                        <a href="<c:url value="/dataset/${dataset.shortName}/doi.html" />" >Edit DOI (Digital Object Identifier)</a>
                    </p>
                    <p>
                        <a href="<c:url value="/dataset/${dataset.shortName}/form/confirmDelete.html" />" >Delete this Dataset</a>
                    </p>
                </div> <!-- .panel-body -->
            </div>
        </authz:authorize>
    </c:otherwise>
</c:choose>