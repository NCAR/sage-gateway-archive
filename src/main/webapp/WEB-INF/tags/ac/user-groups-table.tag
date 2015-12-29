<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<%@ attribute name="userGroups" required="true" type="java.util.Map"  %>

<script type="text/javascript">
    var myDataTable;

    function initUserGroupsTable() {
        $("button#modalYes").on("click", function removeFromGroup() {
            // get the group name for the current row
            var groupName = $('#users-group-table tbody tr.highlightRow').find("td.groupName").text();

            $("#simpleDialogModal").modal('hide');

            /*
             * Set the hidden identifier field in the form to the group name so it can
             * be handed to the back-end Java code.
             */
            $("#remove-form input[name=name]").val(groupName);
            // submit the form
            $("#remove-form").submit();
        });

        // row was single-clicked, so highlight it
        $('#users-group-table tbody').on("click", "tr", function usersGroupTableClick() {
            $(this).addClass('highlightRow').siblings().removeClass('highlightRow');
        });
        // row was double-clicked
        $('#users-group-table tbody').on("dblclick", "tr", function usersGroupTableDoubleClick() {
            // confirmation dialog
            $("#simpleDialogModal").modal('show');
        });
    } // initUserGroupsTable()

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
            initUserGroupsTable();
        }, false);
</script>

<%-- Use Bootstraps's small class to de-emphasizing the text --%>
<p class="small">
    <br/>
    <i>
        The <span style='color:green'>green</span> color indicates an active membership
        the <span style='color:blue'>blue</span> color indicates a membership that is waiting
        for group administrator approval.
    </i>
</p>
<p>${fn:length(userGroups)} entries</p>

<table id="users-group-table" class="table table-condensed table-bordered table-hover">
    <thead>
        <th>Group</th>
        <th>Description</th>
        <th>Status</th>
        <th>Roles</th>
    </thead>
    <tbody>
        <c:forEach var="entries" items="${userGroups}">
            <c:if test="${entries.key.name!=groupDefault}">
                <tr>
                    <td class="groupName"><c:out value='${entries.key.name}'/></td>
                    <td><c:out value='${entries.key.description}'/></td>
                    <td>
                        <c:forEach var="membership" items="${entries.value}">
                            <c:if test="${membership.role==roleDefault}">
                                <c:choose>
                                    <c:when test="${membership.status==statusPending}">
                                        <span style="color:blue">Waiting Approval</span>
                                    </c:when>
                                    <c:when test="${membership.status==statusValid}">
                                        <span style="color:green">Enrolled</span>
                                    </c:when>
                                    <c:when test="${membership.status==statusInvalid}">
                                        <span style="color:red">Rejected</span>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:set var="count" value="0"/>
                        <c:forEach var="membership" items="${entries.value}">
                            <c:if test="${membership.role!=roleDefault}">
                                <c:choose>
                                    <c:when test="${membership.status==statusRequested}">
                                        <span style="color:blue"><c:if test="${count>0}">,</c:if> <c:out value="${membership.role.description}"/></span>
                                    </c:when>
                                    <c:when test="${membership.status==statusValid}">
                                        <span style="color:green"><c:if test="${count>0}">,</c:if> <c:out value="${membership.role.description}"/></span>
                                    </c:when>
                                </c:choose>
                                <c:set var="count" value="${count +1}"/>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </tbody>
</table>