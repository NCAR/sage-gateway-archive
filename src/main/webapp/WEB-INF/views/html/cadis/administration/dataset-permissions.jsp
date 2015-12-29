<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout" >
	
	<tiles:putAttribute type="string" name="title" value="Add Write Permissions" />
	<tiles:putAttribute type="string" name="pageTitle" value="Add Write Permissions: ${command.dataset.title}" />

	<tiles:putAttribute name="body">
		<p>
            To add write permissions for a user, select the checkbox for that user.<br>
            When you are done adding users, click the <span class='highlight'>Submit</span> button.<br>
            Click on any column to sort.
        </p>
        <p>${fn:length(command.individualPermissions)} entries</p>
        <springForm:form method="post" id="permissions-form">
            <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                <thead>
                    <tr>
                        <th>Last Name</th>
                        <th>First Name</th>
                        <th>User Name</th>
                        <th>Email</th>
                        <th>Write</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${command.individualPermissions}" varStatus="num">
                        <tr>
                            <td><c:out value="${entry.element.lastName}"/></td>
                            <td><c:out value="${entry.element.firstName}"/></td>
                            <td><c:out value="${entry.element.userName}"/></td>
                            <td><c:out value="${entry.element.email}"/></td>
                            <td>
                                <springForm:checkbox path="individualPermissions[${num.count - 1}].selected" disabled="${entry.selected}"></springForm:checkbox>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <br>
            <button type="submit" id="submit-button" class="btn btn-default">Submit</button>

        </springForm:form>
        <br>

        <script type="text/javascript">

            function init() {
                /*
                 * Create an array with the values of all the checkboxes in a
                 * column. Because this parses the table, it needs to be inside
                 * init() so it runs after the page is built.
                 *
                 * Source code example here:
                 * https://github.com/DataTables/DataTables/blob/master/examples/plug-ins/dom_sort.html#L37
                 */
                $.fn.dataTable.ext.order['dom-checkbox'] = function  (settings, col) {
                    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
                        return $('input', td).prop('checked') ? '1' : '0';
                    } );
                } // order['dom-checkbox']()

                /*
                 * Initialize the DataTables jQuery plugin
                 */
                $("#users-table").DataTable({
                    // sort on the first column in ascending order
                    "order": [[0, "asc"]],
                    /*
                     * Initialize the table with the required column ordering
                     * data types. There must be an entry in the array for every
                     * column in the table. Use a "null" in columns that don't
                     * have any special options.
                     */
                    "columns": [
                        null,
                        null,
                        null,
                        null,
                        { "orderDataType": "dom-checkbox" }
                    ]
                });

                // submit button (in addition to double-click)
                $("#submit-button").on("click", submitForm);

            } // init()

            var submitForm = function() {
                $("permissions-form").submit();
            }; // submitForm()

            // run the script after page load
            window.onload = init;
        </script>
	</tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
