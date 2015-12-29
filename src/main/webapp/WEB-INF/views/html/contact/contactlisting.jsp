<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Principal Investigators"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Principal Investigators"/>

    <tiles:putAttribute name="body">

        <p>
            To view the contributions for an investigator, double-click the row in the table.
        </p>

        <div class="row">
            <div class="col-md-10">
                <table id="investigators-table" class="table table-condensed table-bordered table-hover">
                    <thead>
                        <tr>
                            <th class="hidden">Investigator Path</th>
                            <th>Principal Investigator</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pi" items="${pis}">
                            <tr>
                                <td class="hidden investigatorPath">
                                    <c:out value="/contact/${gateway:urlEncode(pi.fullName)}/project.html"/>
                                </td>
                                <td>
                                    ${pi.lastName}<c:if test="${not empty pi.firstName}">,</c:if> ${pi.firstName}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

        <%-- form to handle navigation when the user does a double-click --%>
        <form method="GET" id="lookup-form">
        </form>

        <script type="text/javascript">
            function init() {
                /*
                 * Initialize the DataTables jQuery plugin
                 */
                $("#investigators-table").DataTable({
                    // sort on the second column in ascending order
                    "order": [[1, "asc"]]
                });

                // row was single-clicked, so highlight it
                $('#investigators-table').on("click", "tr", function investigatorsTableClick() {
                    $(this).addClass('highlightRow').siblings().removeClass('highlightRow');
                });
                // row was double-clicked
                $('#investigators-table tbody').on("dblclick", "tr", function investigatorsTableDoubleClick() {
                    // get the navigation path for the current row
                    var investigatorPath = $('#investigators-table tbody tr.highlightRow').find("td.investigatorPath").text();
                    /*
                     * Set the action in the form to the pathname for the investigator
                     */
                    $("#lookup-form").attr("action", investigatorPath);
                    // submit the form
                    $("#lookup-form").submit();
                });
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute>

</tiles:insertDefinition>
