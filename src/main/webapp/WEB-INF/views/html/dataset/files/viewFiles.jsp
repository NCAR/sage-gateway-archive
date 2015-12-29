<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Download Files"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Download Files"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <c:forEach var="datasetDownloadRow" items="${datasetDownloadRows}">

            <c:set scope="request" var="logicalFileDownloadRows"
                   value="${datasetDownloadRow.logicalFileDownloadRows}"></c:set>

            <div>
                <c:url var="datasetLink" value="/dataset/${datasetDownloadRow.dataset.shortName}.html"></c:url>
                <b><a href="${datasetLink}">${datasetDownloadRow.dataset.title}</a></b>
                <br>
            </div>

            <div><br>${fn:length(logicalFileDownloadRows)} entries</div>

            <div class="row">
                <div class="col-md-8">
                    <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                        <thead>
                            <tr>
                                <th>File Name</th>
                                <th>Size</th>
                                <th>Date Modified</th>
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
                                        <gateway:FileSizeUnitFormat fileSize="${logicalFileRow.size}"/>
                                    </td>
                                    <td>
                                        ${logicalFileRow.modifiedDate}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div> <!-- .col-md-6 -->
            </div> <!-- .row -->

            <br>

        </c:forEach>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
