<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="fileCount" required="true" %>
<%@ attribute name="size" required="true" %>
<%@ attribute name="unit" required="true" %>

<div style="float: left; background-color: #d0d0d0; text-align: center; padding: 8px; margin-right: 5px;">
    <span style="font-weight: bold;">${fileCount}</span>
    <br>
    Files
</div>

<div style="float: left; background-color: #d0d0d0; text-align: center; padding: 8px;">
    <span style="font-weight: bold;">${size}</span>
    <br>${unit}
</div>
