<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout" >

    <tiles:putAttribute type="string" name="title" value="Add Group Confirmation" />
    <tiles:putAttribute type="string" name="pageTitle" value="New Group Creation Confirmation" />

    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="descriptive_row_table descriptive_row_table_bottom_row">
                            <span class="descriptive_row_name">
                                <span>Group Name</span>
                            </span>
                            <span><c:out value="${command.groupName}"/></span>
                        </div>

                        <div class="descriptive_row_table descriptive_row_table_bottom_row">
                            <span class="descriptive_row_name">
                                <span>Description</span>
                            </span>
                            <span><c:out value="${command.groupDescription}"/></span>
                        </div>

                        <div class="descriptive_row_table descriptive_row_table_bottom_row">
                            <span class="descriptive_row_name">
                                <span>Approval Type</span>
                            </span>
                            <span>
                                <c:choose>
                                    <c:when test="${command.automaticApproval}">automatic</c:when>
                                    <c:otherwise>manual</c:otherwise>
                                </c:choose>
                            </span>
                        </div>

                        <div class="descriptive_row_table descriptive_row_table_bottom_row">
                            <span class="descriptive_row_name">
                                <span>User visible</span>
                            </span>
                            <span>
                                <c:choose>
                                    <c:when test="${command.visibleToUsers}">yes</c:when>
                                    <c:otherwise>no</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div> <!-- .panel-body -->
                </div>
            </div>
        </div> <!-- .row -->

    </tiles:putAttribute>
</tiles:insertDefinition>
