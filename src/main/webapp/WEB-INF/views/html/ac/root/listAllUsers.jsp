<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Manage User Accounts"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Manage User Accounts"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <p>
            <c:url var="navLink" value="/cadis/root/index.html"/>
            <a href="${navLink}">Return to Gateway Root Administrator Index</a>
        </p>

        <br>
        <ac:filter-users-form />

        <%--
            Only display the search results table when there's data to show.
        --%>
        <c:if test="${fn:length(users) gt 0}">
            <c:if test="${userTotal > 1000}">
                <p>
                    <fmt:formatNumber value="${userTotal}" type="number"/> users meet the search criteria.
                    <br>
                    Due to system constraints, a limit of 1000 Users will be
                    returned at the present time. If you cannot find your desired
                    user, please search using the search box above.
                </p>
            </c:if>
            <br>
            <p>
                To edit or view detailed data for a user, double-click the row in the table.
            </p>

            <table id="users-table" class="table table-condensed table-bordered table-hover">
                <thead>
                    <tr>
                        <th class="hidden">Identifier</th>
                        <th>Last Name</th>
                        <th>First Name</th>
                        <th>Username</th>
                        <th>Email Address</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}" varStatus="num">
                        <tr>
                            <td class="hidden identifier"><c:out value="${user.identifier}"/></td>
                            <td><c:out value="${user.lastName}"/></td>
                            <td><c:out value="${user.firstName}"/></td>
                            <td><c:out value="${user.userName}"/></td>
                            <td><c:out value="${user.email}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <%-- form to handle navigation when the user does a double-click --%>
            <form action="<c:url value="/ac/root/lookupUser.htm"/>" method="GET" id="lookup-form">
                <input type="hidden" name="identifier" value=""/>
            </form>

            <script type="text/javascript">
                function init() {
                    /*
                     * Initialize the DataTables jQuery plugin
                     */
                    $("#users-table").DataTable({
                        // sort on the second column in ascending order
                        "order": [[1, "asc"]],
                        /*
                         * turn off searching in DataTables since it's handled by
                         * ac:filter-users-form
                         */
                        "searching": false
                    });

                    // row was single-clicked, so highlight it
                    $('#users-table').on("click", "tr", function usersTableClick() {
                        $(this).addClass('highlightRow').siblings().removeClass('highlightRow');
                    });
                    // row was double-clicked
                    $('#users-table tbody').on("dblclick", "tr", function usersTableDoubleClick() {
                        // get the user identifier for the current row
                        var identifier = $('#users-table tbody tr.highlightRow').find("td.identifier").text();
                        /*
                         * Set the hidden identifier field in the form to the user identifier so it can
                         * be handed to the form.
                         */
                        $("#lookup-form input[name=identifier]").val(identifier);
                        // submit the form
                        $("#lookup-form").submit();
                    });
                } // init()

                // run the script after page load
                window.onload = init;
            </script>
        </c:if>


    </tiles:putAttribute>
</tiles:insertDefinition>
