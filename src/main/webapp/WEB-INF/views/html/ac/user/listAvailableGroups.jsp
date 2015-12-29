<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Available Groups"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Available Groups"/>

    <tiles:putAttribute name="body">

        <p>
            The following groups are available for subscription.
            <br>
            To apply for membership in a group, double-click the corresponding table row.
        </p>
        <div>${fn:length(groups)} entries</div>

        <div class="row">
            <div class="col-md-8">
                <table id="groups-table" class="table table-condensed table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="group" items="${groups}">
                            <tr>
                                <td class="groupName" nowrap="nowrap">${group.name}</td>
                                <td nowrap="nowrap">${group.description}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <form action="<c:url value="/ac/user/secure/groupRegistrationForm.html"/>" method="GET"
                      id="subscribe-form">
                    <input type="hidden" name="groupName" value=""/>
                </form>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

        <script type="text/javascript">
            function init() {
                // row was single-clicked, so highlight it
                $('#groups-table tbody').on("click", "tr", function groupUsersTableClick() {
                    $(this).addClass('highlightRow').siblings().removeClass('highlightRow');
                });
                // row was double-clicked
                $('#groups-table tbody').on("dblclick", "tr", function groupUsersTableDoubleClick() {
                    // get the groupName for the current row
                    var groupName = $(this).find("td.groupName").text();

                    /*
                     * Set the hidden identifier field in the form to the group name so it can
                     * be handed to the back-end Java code.
                     */
                    $("#subscribe-form input[name=groupName]").val(groupName);
                    // submit the form
                    $("#subscribe-form").submit();
                });
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
