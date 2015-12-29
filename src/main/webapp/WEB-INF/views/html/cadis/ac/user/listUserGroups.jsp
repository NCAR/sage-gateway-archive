<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="User Group Memberships"/>
    <tiles:putAttribute type="string" name="pageTitle" value="User Group Memberships"/>

    <tiles:putAttribute name="body">
        <%-- include the simple-dialog modal --%>
        <common:simple-dialog message="Are you sure you want to remove yourself from this group?"/>

        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Options</h4>
                    </div>
                    <div class="panel-body">
                        <cadis:account-navigator selected_page="CURRENT_MEMBERSHIPS"/>
                    </div>
                </div>
            </div>

            <div class="col-md-8">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Current Group Memberships</h4>
                    </div>
                    <div class="panel-body">
                        <c:choose>
                            <c:when test="${fn:length(userGroups) > 1}">
                                To remove yourself from a group, double-click the row in the table.
                                <ac:user-groups-table userGroups="${userGroups}"/>

                                <form action="<c:url value="/ac/user/deleteUserFromGroup.html"/>" method="POST" id="remove-form">
                                    <input type="hidden" name="name" value=""/>
                                </form>

                            </c:when>

                            <c:otherwise>
                                You are not currently enrolled in any special groups.
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
