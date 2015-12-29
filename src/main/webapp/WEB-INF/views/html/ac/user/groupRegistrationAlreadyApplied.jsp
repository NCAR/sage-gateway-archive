<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Group Registration Status"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Group Registration Status"/>

    <tiles:putAttribute name="body">

        <div class="panel panel-default">
            <div class="panel-body">
                <c:choose>
                    <c:when test="${status.name == statusRequested.name}">
                        <p>
                            You have already applied for membership in group ${group.name}.
                        </p>

                        <p>
                            Your request will be processed after full completing the registration process.
                        </p>
                    </c:when>
                    <c:when test="${status.name == statusPending.name}">
                        <p>
                            Your membership is currently Pending for group ${group.name}.
                        </p>

                        <p>
                            A group Administrator will process your request shortly.
                        </p>
                    </c:when>
                    <c:when test="${status.name == statusValid.name}">
                        <p>
                            You are currently already enrolled in group ${group.name}.
                        </p>
                    </c:when>
                    <c:when test="${status.name == statusInvalid.name}">
                        <p>
                            Your membership is Disabled for group ${group.name}.
                        </p>

                        <p>
                            Please contact the group administrator for further assistance.
                        </p>
                    </c:when>
                </c:choose>

                <p>&nbsp;</p>
                Thank you for your interest in ${group.name}.
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>