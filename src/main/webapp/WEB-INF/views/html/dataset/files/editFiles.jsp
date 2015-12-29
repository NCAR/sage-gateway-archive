<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Upload/Delete Data Files"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Upload/Delete Data Files"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>
        <common:info-message message="${infoMessage}"/>

        <c:forEach var="datasetDownloadRow" items="${datasetDownloadRows}">

            <c:set scope="request" var="logicalFileDownloadRows"
                   value="${datasetDownloadRow.logicalFileDownloadRows}"></c:set>

            <div>
                <c:url var="datasetLink" value="/dataset/${datasetDownloadRow.dataset.shortName}.html"></c:url>
                <b><a href="${datasetLink}">${datasetDownloadRow.dataset.title}</a></b>
                <br>
            </div>

            <gateway:authForWrite dataset="${datasetDownloadRow.dataset}">

                <br>
                <c:url var="multiUploadFilesUrl" value="/dataset/${dataset.shortName}/bulkFileUploadForm.html"/>
                <a class="btn btn-default" href="${multiUploadFilesUrl}">Upload Files</a>
                <span style="padding: 15px;"></span>
                <a class="btn btn-default" href="<c:url value="/dataset/${dataset.shortName}/massDeleteFileForm.html" />">Mass Delete
                    Files</a>
                <br>

            </gateway:authForWrite>

            <br>
            <p>${fn:length(logicalFileDownloadRows)} entries</p>

            <div class="row">
                <div class="col-md-6">
                    <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                        <thead>
                            <tr>
                                <th>File Name</th>
                                <th>Size</th>
                                <th>Date Modified</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="logicalFileRow" items="${logicalFileDownloadRows}" varStatus="num">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${logicalFileRow.directDownload}">
                                                <c:url value="/download/fileDownload.htm" var="downloadURL">
                                                    <c:param name="logicalFileId">${logicalFileRow.identifier}</c:param>
                                                </c:url>
                                                <a href="${downloadURL}">${gateway:formatWordWrap(logicalFileRow.name)}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${gateway:formatWordWrap(logicalFileRow.name)}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <gateway:FileSizeUnitFormat
                                                fileSize="${logicalFileRow.size}"/>
                                    </td>
                                    <td><c:out value="${logicalFileRow.modifiedDate}"/></td>
                                    <td>
                                        <gateway:authForWrite dataset="${datasetDownloadRow.dataset}">
                                            <c:if test="${logicalFileRow.deletable}">
                                                <c:url var="deleteFile"
                                                       value="/dataset/${dataset.shortName}/file/${logicalFileRow.name}/confirmDeleteForm.html"/>
                                                <a href="${deleteFile}">Delete</a>
                                            </c:if>
                                        </gateway:authForWrite>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div> <!-- .col-md-6 -->
            </div> <!-- .row -->

        </c:forEach>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
