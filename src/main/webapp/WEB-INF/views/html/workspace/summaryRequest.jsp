<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Data Transfer Requests"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Data Transfer Requests"/>


    <tiles:putAttribute name="body">

        <p>
            A listing of your current and past data transfer requests follows.
        </p>

        <p>
            ACTIVE: Transfer is in progress. You will receive an email when complete.<br/>
            SUCCESS: Transfer is complete. Visit the View Details link to download.<br/>
            EXPIRED: Files no longer available for download. Please reissue request.<br/>
            ERROR: Transfer failed. Please retry. Contact us if error persists.
        </p>

        <c:set var="userTransferRequests" value="${dataTransferCommand.userRequests}"/>
        <p>${fn:length(dataTransferCommand.userRequests)} entries</p>

        <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
            <thead>
                <tr>
                    <th>Request #</th>
                    <th>Status</th>
                    <th>Request Date</th>
                    <th>Complete Date</th>
                    <th>File Count</th>
                    <th>Total Size</th>
                    <th>Details</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="req" items="${dataTransferCommand.userRequests}" varStatus="num">
                    <tr>
                        <td><c:out value="${req.requestNumber}"/></td>
                        <td><c:out value="${req.status}"/></td>
                        <td><c:out value="${req.startTime}"/></td>
                        <td><c:out value="${req.stopTime}"/></td>
                        <td><c:out value="${req.itemCount}"/></td>
                        <td>
                            <c:set var="fileSize">
                                <gateway:FileSizeFormat fileSize="${req.totalSize}"/>
                            </c:set>
                            <c:set var="fileUnit">
                                <gateway:FileUnitFormat fileSize="${req.totalSize}"/>
                            </c:set>
                            <c:out value="${fileSize} ${fileUnit}"/>
                        </td>
                        <td>
                            <c:url var="detailLink" value="/workspace/user/transferRequestDetail.html">
                                <c:param name="dataTransferRequestId" value="${req.identifier}"/>
                            </c:url>
                            <a href="<c:out value="${detailLink}"/>">View Details</a>
                            <c:choose>
                                <c:when test="${request.status == 'SUCCESS'}">
                                    <c:url var="wgetUrl"
                                           value="/archive/request/${req.identifier}.wget"/>
                                    <a href="<c:out value="${wgetUrl}"/>">Download Wget Script</a>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
