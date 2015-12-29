<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="fieldName" required="true" type="java.lang.String" %>
<%@ attribute name="fieldValue" required="true" type="java.util.Date" %>
<%@ attribute name="fieldHelp" required="false" type="java.lang.String" %>
<%@ attribute name="showEmpty" required="true" type="java.lang.Boolean" %>

<c:if test="${showEmpty || (fieldValue != null)}">
    <dl>
        <dt>${fieldName}</dt>
        <dd><fmt:formatDate value="${fieldValue}" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
    </dl>
    <c:if test="${fieldHelp != null && fn:length(fieldHelp) != 0}">
        <dl>
            <dd class="small">${fieldHelp}</dd>
        </dl>
    </c:if>
</c:if>
