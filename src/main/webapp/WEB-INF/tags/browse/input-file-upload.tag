<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ attribute name="id" required="false" type="java.lang.String" %>
<%@ attribute name="fieldName" required="true" type="java.lang.String" %>
<%@ attribute name="fieldPath" required="true" type="java.lang.String" %>
<%@ attribute name="fieldExample" required="false" type="java.lang.String" %>
<%@ attribute name="fieldHelp" required="false" type="java.lang.String" %>
<%@ attribute name="fileSize" required="false" type="java.lang.Integer" %>
<%@ attribute name="isRequired" required="false" type="java.lang.Boolean" %>
<%@ attribute name="multiple" required="false" type="java.lang.Boolean" %>

<c:if test="${empty fileSize}"><c:set var="fileSize" value="39"/></c:if>
<div class="descriptive_row_table<c:if test="${(fieldHelp == null && fn:length(fieldHelp) == 0) && (fieldExample == null && fn:length(fieldExample) == 0)}"> descriptive_row_table_bottom_row</c:if>">
    <span class="descriptive_row_name">
        <span <c:if test="${isRequired}">class="required"</c:if>>${fieldName}<c:if
            test="${fn:length(fieldName) > 0}">:</c:if>
        </span>
    </span>
    <span class="descriptive_row_value">
        <!-- button with nested file input -->
        <span class="btn btn-default btn-file">
            Browse&hellip;
            <input type="file"
                   class="descriptive_row_value"
                   <c:if test="${id != null}">id="${id}"</c:if>
                   name="${fieldPath}"
                   size=${fileSize} <c:if test="${multiple}" >multiple</c:if> />
        </span>
    </span>
</div>
<c:if test="${(fieldHelp != null && fn:length(fieldHelp) != 0) || (fieldExample != null && fn:length(fieldExample) != 0)}">
    <div class="descriptive_row_table descriptive_row_table_bottom_row">
        <span class="descriptive_row_name descriptive_row_help">
            <span>${fieldHelp}</span>
        </span>
        <span class="descriptive_row_value descriptive_row_example">
            <span>${fieldExample }</span>
        </span>
    </div>
</c:if>