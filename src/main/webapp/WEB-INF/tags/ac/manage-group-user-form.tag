<%@tag body-content="empty"%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<!-- Command object -->
<%@ attribute name="command" required="true" type="sgf.gateway.web.controllers.security.ManageGroupUserCommand" %>

<%-- include the simple-dialog modal --%>
<common:simple-dialog message="Are you sure you want to reject this user?"/>

<script type="text/javascript">
	function init() {
        // form submission button
        $("#reject-user").on("click", rejectButtonClick);
        $("button#modalYes").on("click", submitForm);
	} // init()

    function rejectButtonClick() {
        $("#simpleDialogModal").modal('show');
        /*
         * Stop the event from propagating. There's something odd about
         * JSP/buttons that causes the yes button to fire when the
         * event isn't suppressed.
         */
        return false;
    } // rejectButtonClick()

    // confirmation dialog
    function submitForm() {
        $("#simpleDialogModal").modal('hide');
        // submit the form
        $("#reject-form").submit();
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
            window.removeEventListener("load", load, false); // remove listener, no longer needed
            init();
        }, false);
</script>

<!-- display form validation errors -->
<common:form-errors commandName="command" />

<p>&nbsp;</p>
<ul class="nav nav-tabs">
    <li class="active" role="presentation"><a href="#tab1" data-toggle="tab"><em>User Details</em></a></li>
    <li role="presentation"><a href="#tab2" data-toggle="tab"><em>Membership Data</em></a></li>
    <li role="presentation">
        <a href="#tab3" data-toggle="tab">
            <em>
                <c:choose>
                    <c:when test="${command.enroll}">
                        Enroll User
                    </c:when>
                    <c:otherwise>
                        Update User
                    </c:otherwise>
                </c:choose>
            </em>
        </a>
    </li>
    <li role="presentation">
        <a href="#tab4" data-toggle="tab">
            <em>
                <c:choose>
                    <c:when test="${command.enroll}">
                        Reject User
                    </c:when>
                    <c:otherwise>
                        Remove User
                    </c:otherwise>
                </c:choose>
            </em>
        </a>
    </li>
</ul>

<div class="tab-content">
    <!-- User Details -->
    <div class="tab-pane active" id="tab1">
        <ac:user-info user="${command.user}" />
    </div>

    <!-- This Group's Membership Data -->
    <div class="tab-pane" id="tab2">
        <p>${fn:length(command.group.groupData)} entries</p>
        <div class="row">
            <div class="col-xs-6">
                <table class="table table-condensed table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Registration Field</th>
                            <th>Registration Value</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${command.group.groupData}" var="groupData">
                            <tr>
                                <td>
                                    <c:out value="${groupData.key.name}" />
                                </td>
                                <td>
                                    <c:forEach items="${command.user.userData}" var="userData">
                                        <c:if test="${groupData.key.name==userData.groupData.name}">
                                            <c:out value="${userData.value}" />
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div> <!-- .row -->
    </div>

    <!-- Enroll/Update User -->
    <div class="tab-pane" id="tab3">
        <p>&nbsp;</p>
        <c:choose>
            <c:when test="${command.enroll}">
                <div>
                    Assign special roles (if any) to the user (the default roles
                    for this group are pre-selected).
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    Select any special roles (if any) to assign to the user (the user's current roles are pre-selected).
                </div>
            </c:otherwise>
        </c:choose>

        <p>&nbsp;</p>

        <springForm:form method="post">
            <input type="hidden" name="remove" value="false"/>
            <!-- Roles Selection -->
            <div class="row">
                <div class="col-md-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">Special Roles</h4>
                        </div>
                        <div class="panel-body">
                            <c:forEach var="role" items="${command.roles}">
                                <!-- but don't show it -->
                                <c:if test="${role!=roleDefault && role!=roleNone}">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="userRoles" value="<c:out value="${role.name}"/>"
                                                    <c:if test="${fn:contains(command.userRoles,role.name)}">
                                                        <c:out value="checked"/>
                                                    </c:if>
                                                    />
                                            <c:out value="${role.description}"/>
                                        </label>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <!-- always add default role -->
                            <input type="hidden" name="userRoles" value="default"/>
                        </div>
                    </div>
                </div>

                <!-- Administrator Message -->
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">Optional Administrator Message</h4>
                        </div>
                        <div class="panel-body">
                            <springForm:textarea rows="4" cols="60" path="message" />
                            <br/>
                            The message sent to the user can only contain the following characters:
                            <br/>letters (upper or lower case), numbers, and the characters '-', '_', '.', ':', ';' and '@'.
                        </div>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-default" id="enroll-user">Submit</button>

        </springForm:form>

    </div> <!-- Enroll/Update User -->

    <!-- Reject/Remove User -->
    <div class="tab-pane" id="tab4">
        <p>&nbsp;</p>

        <c:choose>
            <c:when test="${command.enroll}">
                Click the button below to reject the user request for group membership.
            </c:when>
            <c:otherwise>
                Click the button below to completely remove the user from the Group.
            </c:otherwise>
        </c:choose>

        <p>&nbsp;</p>

        <springForm:form method="post" id="reject-form">
            <input type="hidden" name="remove" value="true"/>

            <!-- Administrator Message -->
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">Optional Administrator Message</h4>
                        </div>
                        <div class="panel-body">
                            <springForm:textarea rows="4" cols="60" path="message" />
                            <br/>
                            The message sent to the user can only contain the following characters:
                            <br/>letters (upper or lower case), numbers, and the characters '-', '_', '.', ':', ';' and '@'.
                        </div>
                    </div>
                </div>
            </div>

            <!--
            <input type="button" value="Submit" id="reject-user" class="btn btn-default" />
            -->
            <button type="submit" id="reject-user" class="btn btn-default">Submit</button>
        </springForm:form>

    </div> <!-- Reject/Remove User -->

</div>
<br/>   <!-- white space -->