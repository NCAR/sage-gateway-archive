<%@tag body-content="empty" %>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ attribute name="message" required="true" type="java.lang.String" %>

<c:if test="${not empty message}">
    <div class="panel panel-warning">
        <div class="panel-heading">Information</div>
        <div class="panel-body">
            ${message}
        </div> <!-- .panel-body -->
    </div>
</c:if>