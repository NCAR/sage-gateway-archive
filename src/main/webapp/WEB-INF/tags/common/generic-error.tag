<%@tag body-content="empty"%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="message" required="true" %>
<%--
    Full-width panel for displaying an error message

    To use, include the following line in the body element:
        <common:generic-error message="Message shown in the modal body"/>
--%>

<br>
<div class="panel panel-danger">
    <div class="panel-heading">ERROR</div>
    <div class="panel-body">
        <c:out value="${message}"/>
    </div> <!-- .panel-body -->
</div>
<br>