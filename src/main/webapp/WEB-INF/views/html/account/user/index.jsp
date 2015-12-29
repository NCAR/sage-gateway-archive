<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tiles:insertDefinition name="general-layout">

<tiles:putAttribute type="string" name="title" value="User Account Home"/>
<tiles:putAttribute type="string" name="pageTitle" value="User Account Home"/>

<tiles:putAttribute name="body">

    <common:success-message message="${successMessage}"/>

    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">Personal Account</div>
                <div class="panel-body">
                    <a href="<c:url value="/ac/user/index.html"/>">Account Summary</a>
                    <br>
                    <a href="<c:url value="/ac/user/listAvailableGroups.htm"/>">Apply for Group Membership</a>
                    <br>
                    <a href="<c:url value="/ac/user/listUserGroups.html"/>">List Current Memberships</a>
                    <br>
                    <c:if test="${not user.remoteUser}">
                        <a href="<c:url value="/ac/user/secure/changePassword.html"/>">Change Password</a>
                        <br>
                    </c:if>
                    <a href="<c:url value="/ac/user/changeEmail.htm"/>">Change Email Address</a>
                </div> <!-- .panel-body -->
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">User Workspace</div>
                <div class="panel-body">
                    <a href="<c:url value="/workspace/user/summaryRequest.htm" />">Data Transfer Requests</a>
                </div> <!-- .panel-body -->
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">User Tools</div>
                <div class="panel-body">
                    <a href="<c:url value="/account/user/myProxyLogon.html"/>">Launch MyProxyLogon</a>
                </div> <!-- .panel-body -->
            </div>
        </div> <!-- .col-md-4 -->

        <c:if test="${not empty adminGroups}">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">Group Administration</div>
                    <div class="panel-body">
                        <c:set var="count" value="0"/>
                        <!-- collapse.in shows content -->
                        <c:set var="showContent" value="in"/>
                        <c:set var="selectedFlag" value=" selected"/>
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                            <c:forEach var="group" items="${adminGroups}">
                                <c:set var="count" value="${count+1}"/>
                                <c:if test="${count>1}">
                                    <c:set var="selectedFlag" value=""/>
                                    <!-- hide accordion content after the first row -->
                                    <c:set var="showContent" value=""/>
                                </c:if>
                                <div class="panel panel-default">
                                    <div class="panel-heading" role="tab" id="${group.name}">
                                        <h4 class="panel-title">
                                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${count}" aria-expanded="true" aria-controls="collapse${group.name}">
                                                <c:out value="${group.name}"/>
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse${count}" class="panel-collapse collapse ${showContent}" role="tabpanel" aria-labelledby="${group.name}">
                                        <div class="panel-body">
                                            <c:url value='/ac/admin/listGroupUsers.html' var="addMember" >
                                                <c:param name="status" value="0" />
                                                <c:param name="groupName" value="${group.name}" />
                                            </c:url>
                                            <a href="${addMember}">Add New User</a>
                                            <br>
                                            <c:url value='/ac/admin/listGroupUsers.html' var="waitingMembers" >
                                                <c:param name="status" value="2" />
                                                <c:param name="groupName" value="${group.name}" />
                                            </c:url>
                                            <a href="${waitingMembers}">Approve Waiting Users</a>
                                            <br>
                                            <c:url value='/ac/admin/listGroupUsers.html' var="currentMembers" >
                                                <c:param name="status" value="3" />
                                                <c:param name="groupName" value="${group.name}" />
                                            </c:url>
                                            <a href="${currentMembers}">Manage Current Users</a>
                                            <br>
                                            <c:url value="/ac/group/${group.name}/registrationField.html" var="registrationFields" />
                                            <a href="${registrationFields}">Change Registration Fields</a>
                                        </div> <!-- .panel-body -->
                                    </div>
                                </div>
                            </c:forEach>
                        </div> <!-- .panel-group -->
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-4 -->
        </c:if>
    </div> <!-- .row -->

</tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
