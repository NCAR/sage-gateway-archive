<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">
    <tiles:putAttribute name="title" value="Transfer Request ${dataTransferCommand.selectedRequest.requestNumber}"/>
    <tiles:putAttribute name="pageTitle" value="Transfer Request ${dataTransferCommand.selectedRequest.requestNumber}"/>

    <tiles:putAttribute name="body">

        <a href="<c:url value="/workspace/user/summaryRequest.html" />">Data Transfer Requests</a>
        <br/>
        <fmt:formatDate var="formattedRequestDate" type="both"
                        value="${dataTransferCommand.selectedRequest.startTime}" dateStyle="medium"/>
        <browse:descriptive-row showEmpty="true" fieldValue="${formattedRequestDate}" fieldName="Requested Date"/>
        <c:choose>
            <c:when test="${dataTransferCommand.selectedRequest.fractionComplete >= 1}">
                <fmt:formatDate var="formattedCompleteDate" type="both"
                                value="${dataTransferCommand.selectedRequest.stopTime}" dateStyle="medium"/>
                <browse:descriptive-row showEmpty="true" fieldValue="${formattedCompleteDate}"
                                        fieldName="Completed Date"/>
            </c:when>
            <c:otherwise>
                <browse:descriptive-row showEmpty="true" fieldValue="Request not yet Completed"
                                        fieldName="Completed Date"/>
            </c:otherwise>
        </c:choose>
        <browse:descriptive-row showEmpty="true" fieldValue="${dataTransferCommand.selectedRequest.status}"
                                fieldName="Request Status"/>

        <c:choose>
            <c:when test="${dataTransferCommand.selectedRequest.status == 'SUCCESS'}">
                <br/>
                <a href="<c:url value="/archive/request/${dataTransferCommand.selectedRequest.identifier}.wget" />">Download
                    Wget Script</a>
            </c:when>
        </c:choose>
        <br>
        <p>${fn:length(dataTransferCommand.selectedRequest.items)} entries</p>

        <table id="transferRequestDetailsTable" class="table table-condensed table-bordered table-striped">
            <thead>
                <tr>
                    <th>File Name</th>
                    <th>Size</th>
                    <th>Status</th>
                    <th>Message</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${dataTransferCommand.selectedRequest.items}">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${item.status == 'SUCCESS'}">
                                    <c:url var="downloadUrl"
                                           value="/archive/request/${dataTransferCommand.selectedRequest.identifier}/file/${item.identifier}"/>
                                    <a href="${downloadUrl}">${item.source.logicalFile.name}</a>
                                </c:when>
                                <c:otherwise>
                                    ${item.source.logicalFile.name}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <gateway:FileSizeUnitFormat fileSize="${item.source.logicalFile.size}"/>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.status == 'SUCCESS'}">
                                    Success
                                </c:when>
                                <c:when test="${item.status == 'ACTIVE'}">
                                    Active
                                </c:when>
                                <c:when test="${item.status == 'EXPIRED'}">
                                    Released
                                </c:when>
                                <c:when test="${item.status == 'ABORTED'}">
                                    Canceled
                                </c:when>
                                <c:when test="${item.status == 'ERROR'}">
                                    Error
                                </c:when>
                                <c:otherwise>
                                    Unknown
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            ${item.message}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
