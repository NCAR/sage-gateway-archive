<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Authorization Required"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Authorization Required"/>

    <tiles:putAttribute name="body">

        <br>
        <div class="col-md-8" align="center">
            <div class="panel panel-default">
                <div class="panel-body">
                    You do not have enough privileges to access the resource you requested.

                    <c:if test="${not empty groups}">
                        <br>
                        Please request membership in the following group(s) to gain access to the requested resource:
                        <br>

                        <table id="groups-table" class="table table-condensed table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Group Name</th>
                                    <th>Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${groups}" var="group">
                                    <tr>
                                        <td class="groupName"><c:out value="${group.name}"/></td>
                                        <td><c:out value="${group.description}"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        To apply for membership in a group, double-click the corresponding table row.
                        <br>

                        <form action="<c:url value="/ac/user/secure/groupRegistrationForm.html"/>" method="GET"
                              id="group-application-form">
                            <c:url var="datasetURL" value="/dataset/${dataset.shortName}.html"/>

                            <input type="hidden" name="redirect" value="${datasetURL}"/>
                            <input type="hidden" name="groupName" value=""/>
                        </form>
                    </c:if>
                </div> <!-- .panel-body -->
            </div>
        </div>

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
                    $("#group-application-form input[name=groupName]").val(groupName);
                    // submit the form
                    $("#group-application-form").submit();
                });
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
