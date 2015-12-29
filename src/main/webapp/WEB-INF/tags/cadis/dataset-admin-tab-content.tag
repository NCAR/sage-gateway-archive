<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="false" type="sgf.gateway.model.metadata.Dataset" %>


<div>
    <c:choose>
        <c:when test="${dataset.brokered}">
            <p>
                This Dataset is brokered and may not be edited from this site.
            </p>
            <p>
                Please see the following link for this Dataset's authoritative source:
            </p>
            <p>
                <c:url var="redirectURL" value="/redirect.html">
                    <c:param name="link" value="${dataset.currentDatasetVersion.authoritativeSourceURI}"/>
                </c:url>
                <a href="${redirectURL}">${dataset.currentDatasetVersion.authoritativeSourceURI}</a>
            </p>
            <p>
                <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                    <c:url var="datasetDeletion" value="/dataset/${dataset.shortName}/form/confirmDelete.html"/>
                    <a href="${datasetDeletion}">Delete this Dataset</a>
                </authz:authorize>
            </p>
        </c:when>
        <c:otherwise>
            <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')">

                <p>
                    <a href="<c:url value="/dataset/${dataset.shortName}/editDatasetForm.html" />">Edit Metadata</a>
                </p>
                <p>
                    <a href="<c:url value="/dataset/${dataset.shortName}/responsibleParty.html" />">Add/Edit
                        Responsible Parties (Authors, PIs, Contacts, etc.)</a>
                </p>
                <p>
                    <a href="<c:url value="/dataset/${dataset.shortName}/awardNumber.html" />">Add/Delete Award
                        Numbers</a>
                </p>
                <p>
                    <a href="<c:url value="/dataset/${dataset.shortName}/relatedLink.html" />">Add/Edit Related
                        Links</a>
                </p>

                <p>
                    <a href="<c:url value="/dataset/${dataset.shortName}/editfiles.html" />">Upload/Delete Data
                        Files</a>
                </p>

                <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                    <p>
                        <c:url var="doiUrl" value="/dataset/${dataset.shortName}/doi.html"/>
                        <a href="${doiUrl}">Add/Edit DOI (Digital Object Identifier)</a>
                    </p>
                    <p>
                        <c:url var="datasetPermissions" value="/ac/root/datasetPermissions.html">
                            <c:param name="datasetId" value="${dataset.identifier}"/>
                        </c:url>
                        <a href="${datasetPermissions}">Add write permissions to this Dataset</a><br>
                    </p>
                </authz:authorize>

                <p>
                    <c:url var="createChildDataset"
                           value="/dataset/${dataset.rootParentDataset.shortName}/createNewDataset1.html"/>
                    <a href="${createChildDataset}">Create New Dataset</a>
                </p>

                <p>
                    <c:url var="moveDataset" value="/dataset/${dataset.shortName}/moveDataset.html"/>
                    <a href="${moveDataset}">Move this Dataset</a>
                </p>
                <p>
                    <c:url var="viewDatasetHierarchy" value="/dataset/${dataset.shortName}/hierarchy.html"/>
                    <a href="${viewDatasetHierarchy}">View Dataset Hierarchy</a>
                </p>

                <c:if test="${fn:length(dataset.directlyNestedDatasets) > 1}">
                    <p>
                        <c:url var="orderDatasets" value="/dataset/${dataset.shortName}/form/order.html"/>
                        <a href="${orderDatasets}">Order Datasets</a>
                    </p>
                </c:if>

                <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                    <p>
                        <c:url var="datasetDeletion" value="/dataset/${dataset.shortName}/form/confirmDelete.html"/>
                        <a href="${datasetDeletion}">Delete this Dataset</a>
                    </p>
                </authz:authorize>

                <p>
                    <c:url var="rosettaUrl" value="/redirect.html">
                        <c:param name="link"
                                 value="http://rosetta.unidata.ucar.edu/createAcadis?dataset=${dataset.identifier}"/>
                    </c:url>
                    <a href="${rosettaUrl}">Rosetta Data Translation Tool</a>
                </p>

                <div style="padding-left: 2em;">
                    <c:url var="cfUrl" value="/redirect.html">
                        <c:param name="link"
                                 value="http://en.wikipedia.org/wiki/Climate_and_Forecast_Metadata_Conventions"/>
                    </c:url>
                    <p>
                        Rosetta is an external collaborative website which transforms ASCII files (csv, excel,
                        etc...) into <a href="${cfUrl}">Climate and Forecast</a> (CF) compliant netCDF files.
                    </p>
                    <p>
                        You will be required by the Rosetta website to input your Username and Password from
                        this website (ACADIS) to upload your transformed files back to this site.
                    </p>
                    <c:url var="youtubeUrl" value="/redirect.html">
                        <c:param name="link" value="https://www.youtube.com/watch?v=G5pLIjjnK00"/>
                    </c:url>
                    <p>
                        <a href="<c:url value="${youtubeUrl}"/>">Rosetta Data Translation Tool Demo Video</a>
                    </p>
                </div>
            </authz:authorize>
        </c:otherwise>
    </c:choose>
</div>