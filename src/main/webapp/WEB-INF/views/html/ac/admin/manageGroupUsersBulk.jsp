<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Bulk Enrollment of Group Users"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Bulk Enrollment of Users in Group ${model.group.name}"/>

    <tiles:putAttribute name="body">

        <p>&nbsp;</p>

        <div align="center">The following users were enrolled in group: <c:out value="${model.group.name}"/></div>
        &nbsp;
        <ac:group-users-table users="${model.users}"/>

        <div align="center">
            <form method="get" action='<c:url value="/ac/user/index.html"/>'>
                <button type="submit" class="btn btn-default" id="submit-button">Done</button>
            </form>
        </div>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
