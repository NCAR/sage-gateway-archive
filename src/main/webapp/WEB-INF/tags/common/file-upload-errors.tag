<%@tag body-content="empty" %>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="commandName" required="true" type="java.lang.String" %>

<spring:hasBindErrors name="${commandName}">
    <div class="panel panel-danger">
        <div class="panel-heading">ERROR</div>
        <div class="panel-body">
            <p>
                No files have been uploaded to the server.
            </p>

            <p>
                Please fix the following error(s) before continuing:
            </p>
            <ul>
                <spring:bind path="${commandName}.*">
                    <c:forEach items="${status.errorMessages}" var="error">
                        <li><span><c:out value="${error}"/></span></li>
                    </c:forEach>
                </spring:bind>
            </ul>
        </div> <!-- .panel-body -->
    </div>
</spring:hasBindErrors>
