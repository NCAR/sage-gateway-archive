<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="link" required="true" type="java.lang.String"  %>
<%@ attribute name="text" required="true" type="java.lang.String"  %>
<%@ attribute name="selected" required="false" type="java.lang.String"  %>
<%@ attribute name="new_window" required="false" type="java.lang.String"  %>

<li <c:if test="${selected eq 'true'}">style="font-weight: bold; list-style-type: none;"</c:if>
    <c:if test="${selected eq 'false'}">style="list-style-type: none;"</c:if> >
    <div style="padding: 2px;">
        <a href="${link}" <c:if test="${new_window eq 'true'}">target="new"</c:if> >${text}</a>
    </div>
</li>