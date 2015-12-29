<%@tag body-content="empty"%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<!-- Command object -->
<%@ attribute name="command" required="true" type="sgf.gateway.web.controllers.security.ListGroupUsersCommand" %>

<%-- include the simple-dialog modal --%>
<common:simple-dialog message="None"/>

<script type="text/javascript">
    function init() {
        /*
         * Initialize the DataTables jQuery plugin
         */
        $("#group-users-table").DataTable({
            // sort on the first column in ascending order
            "order": [[0, "asc"]]
        });

        // row was double-clicked
        $('#group-users-table tbody').on("dblclick", "tr", function groupUsersTableDoubleClick() {
            // get the uuid for the current row
            var uuid = $(this).find("td.uuid").text();

            /*
             * Set the hidden identifier field in the form to the uuid so it can
             * be handed to the back-end Java code.
             */
            $("#my-form input[name=identifier]").val(uuid);
            // submit the form
            $("#my-form").submit();
        });

        <%-- if ESG --%>
        <c:if test="${command.status==2}">
        /*
         * Only show checkboxes if this is an ESG page. Columns are
         * numbered starting at 1.
         */
        $("#group-users-table").find("th").eq(5).removeClass("hidden");
        $("#group-users-table").find(":checkbox").parent().removeClass("hidden");

        // form submission button
        $("#submit-bulk-button").click(function submitBulkButtonClick() {
            // list of UUIDs formatted for consumption by the back-end Java
            var uuids = "";
            // get the td elements that are checked
            var $tds = $("#group-users-table tr").has(":checkbox:checked").find("td.uuid");

            if (!$tds.length) {
                createWarningDialog("Please check one or more users from the table.");
            } else {
                // build the list of UUIDs
                $tds.each(function buildUuidList() {
                    uuids += $(this).text() + "|";
                });

                /*
                 * Set the hidden UUIDs field in the form to the uuid list so it can
                 * be handed to the back-end Java code.
                 */
                $("#bulk-form input[name=uuids]").val(uuids);
                // submit the form
                $("#bulk-form").submit();
            } // if
        });
        </c:if>
    } // init()

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

<div>
    <c:choose>
        <c:when test="${command.status==0}">
            <h3>Add New User</h3>
            To view detailed data for a user, double-click the row in the table.
        </c:when>
        <c:when test="${command.status==2}">
            <h3>Approve Waiting Users</h3>
            To view detailed data for a user, double-click the row in the table.<br/>
            To enroll more than user at once, use the checkboxes to select the desired users, then click <i>Enroll All Checked Users</i> button.
        </c:when>
        <c:otherwise>
            <h3>Manage Current Users</h3>
            To view detailed data for a user, double-click the row in the table.
        </c:otherwise>
    </c:choose>

    <c:if test="${userTotal > 1000}">
        <br/><br/>
        <p>
            Due to system constraints, a limit of 1000 random users are being returned at the present time.  If you cannot find your desired user, please search for him or her using the search box above.
        </p>
    </c:if>

    <!-- Group Users Table -->
    <ac:group-users-table users="${command.userMap}" status="${command.status}" />

    <div align="center">
        <form method="get" action='<c:url value="/ac/admin/manageGroupUser.htm"/>' id="my-form">
            <%-- identifier must be set to a row's UUID before form submission --%>
            <input type="hidden" name="identifier" value="" />
            <input type="hidden" name="gatewayName" value="" />
            <input type="hidden" name="groupName" value="<c:out value="${command.groupName}"/>" />
        </form>

        <%-- if ESG --%>
        <c:if test="${command.status==2}">
            &nbsp;
            <form method="post" action='<c:url value="/ac/admin/manageGroupUsersBulk.htm"/>' id="bulk-form">
                <input type="hidden" name="groupName" value="<c:out value="${command.groupName}"/>" />
                <input type="hidden" name="uuids" value=""/>
                <button type="button" class="btn btn-default" id="submit-bulk-button">Enroll All Checked Users</button>
            </form>
        </c:if>
    </div>
</div>
