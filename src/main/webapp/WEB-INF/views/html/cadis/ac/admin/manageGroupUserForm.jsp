<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Manage Group User"/>
    <tiles:putAttribute type="string" name="pageTitle"
                        value="Manage User ${command.userName} in Group ${command.groupName}"/>

    <tiles:putAttribute name="body">

        <div class="return_link">
            <a href="<c:url value="/ac/admin/listGroupUsers.html?status=0&groupName=ACADIS"/>">Add New User to ACADIS
                Group</a>
            <br/>
            <a href="<c:url value="/ac/admin/listGroupUsers.html?status=3&groupName=ACADIS"/>">Manage Current ACADIS
                Users</a>
            <br/>
        </div>

        <ac:manage-group-user-form command="${command}"/>
    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
