<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Lookup User Information"/>
    <tiles:putAttribute type="string" name="pageTitle" value="User Information"/>

    <tiles:putAttribute name="body">
        <%-- include the simple-dialog modal --%>
        <common:simple-dialog message="Proceed with user account modifications?"/>

        <common:success-message message="${successMessage}"/>

        <div class="return_link">
            <a href="<c:url value="/ac/root/listAllUsers.html"/>">Manage User Accounts</a>
        </div>

        <p>&nbsp;</p>

        <ul class="nav nav-tabs">
            <li class="active" role="presentation"><a href="#tab1" data-toggle="tab"><em>Account Summary</em></a></li>
            <li role="presentation"><a href="#tab2" data-toggle="tab"><em>Group Roles</em></a></li>
            <li role="presentation"><a href="#tab3" data-toggle="tab"><em>Membership Data</em></a></li>
        </ul>

        <div class="tab-content">
            <div class="tab-pane active" id="tab1">
                <ac:user-info user="${user}"/>
            </div>

            <div class="tab-pane" id="tab2">
                <c:set var="accountEnabled" value="true"/>
                <p>&nbsp;</p>
                <p>${fn:length(user.memberships)} entries</p>

                <table id="roles-table" class="table table-condensed table-bordered">
                    <thead>
                        <tr>
                            <th class="text_bold">Group</th>
                            <th class="text_bold">Role</th>
                            <th class="text_bold">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${user.memberships}" var="membership">
                            <tr>
                                <td align="center"><c:out value="${membership.group.name}"/></td>
                                <td align="center"><c:out value="${membership.role.description}"/></td>
                                <td align="center"><c:out value="${membership.status.description}"/></td>
                            </tr>
                            <c:if test="${membership.group.name==groupDefault && membership.role==roleDefault && membership.status==roleInvalid}">
                                <c:set var="accountEnabled" value="false"/>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
                <p>&nbsp;</p>
            </div>
            <div class="tab-pane" id="tab3">
                <p>&nbsp;</p>

                <p>${fn:length(user.userData)} entries</p>

                <table id="user-data-table" class="table table-condensed table-bordered">
                    <thead>
                    <tr>
                        <th>Registration Field</th>
                        <th>Registration Value</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${user.userData}" var="userData">
                        <tr>
                            <td>
                                <c:out value="${userData.groupData.name}"/>
                            </td>
                            <td><c:out value="${userData.value}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <p>&nbsp;</p>
            </div>
        </div>

        <br>
        <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
            <form action="<c:url value="/ac/root/confirmAccount.htm"/>" method="POST" style="display: inline;">
                <input type="hidden" name="identifier" value="${user.identifier}"/>
                <button type="submit" class="btn btn-default" id="confirm-button">Confirm User Account</button>
            </form>
            <form action="<c:url value="/ac/root/disableUserAccount.html" />" method="POST"
                  id="disable-form" style="display: inline;">
                <input type="hidden" name="identifier" value="${user.identifier}"/>
                <c:choose>
                    <c:when test="${user.status == 'VALID' }">
                        <input type="hidden" name="disable" value="true"/>
                        <button type="submit" class="btn btn-default" id="account-button">Disable User Account</button>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="disable" value="false"/>
                        <button type="submit" class="btn btn-default" id="account-button">Enable User Account</button>
                    </c:otherwise>
                </c:choose>
            </form>
            <form action="<c:url value="/ac/root/editAccountSummaryInfo.html"/>" method="GET" style="display: inline;">
                <input type="hidden" name="identifier" value="${user.identifier}"/>
                <button type="submit" class="btn btn-default" id="edit-button">Edit User Account</button>
            </form>
            <br>
        </authz:authorize>
        <br> <!-- visual whitespace -->

        <script type="text/javascript">
            function init() {
                // form submission button
                $("#account-button").on("click", accountButtonClick);
                $("button#modalYes").on("click", submitForm);
            } // init()

            function accountButtonClick() {
                // show confirmation dialog
                $("#simpleDialogModal").modal('show');
                /*
                 * Stop the event from propagating. There's something odd about
                 * JSP/buttons that causes the yes button to fire when the
                 * event isn't suppressed.
                 */
                return false;
            } // accountButtonClick()

            // confirmation dialog
            function submitForm() {
                $("#simpleDialogModal").modal('hide');
                // submit the form
                $("#disable-form").submit();
            } // submitForm()

            /*
             * run the script after page load
             *
             * Using an event listener instead of window.onload because there may be
             * multiple onload events on the page. You can't assign two different
             * functions to window.onload; the last one will always win.
             */
            window.addEventListener("load",
                function load(event) {
                    window.removeEventListener("load", load, false);    // remove listener, no longer needed
                    init();
                }, false);
        </script>
    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
