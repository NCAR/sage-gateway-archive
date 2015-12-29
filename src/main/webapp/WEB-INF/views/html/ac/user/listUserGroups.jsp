<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Group Memberships"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Group Memberships"/>

    <tiles:putAttribute name="body">
        <%-- include the simple-dialog modal --%>
        <common:simple-dialog message="Are you sure you want to remove yourself from this group?"/>

        <p>
            You are currently enrolled in the following groups.
            <br>
            To remove yourself from a group, double-click the row in the table.
        </p>

        <div class="row">
            <div class="col-md-8">
                <c:choose>

                    <c:when test="${fn:length(userGroups) > 1}">
                        <ac:user-groups-table userGroups="${userGroups}"/>

                        <form action="<c:url value="/ac/user/deleteUserFromGroup.html"/>" method="POST" id="remove-form">
                            <input type="hidden" name="name" value=""/>
                        </form>

                    </c:when>

                    <c:otherwise>
                        You are not currently enrolled in any special groups.
                        <p>&nbsp</p>
                        <a href="<c:url value="/ac/user/listAvailableGroups.htm" />">Apply for Group Membership</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
