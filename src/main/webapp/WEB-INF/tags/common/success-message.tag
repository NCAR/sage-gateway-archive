<%@tag body-content="empty" %>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ attribute name="message" required="true" type="java.lang.String" %>

<c:if test="${not empty message}">
    <div class="panel panel-success">
        <div class="panel-heading">Success</div>
        <div class="panel-body">
            ${message}
        </div> <!-- .panel-body -->
    </div>
</c:if>