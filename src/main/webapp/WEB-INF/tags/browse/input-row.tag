<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ attribute name="fieldName" required="true" type="java.lang.String" %>
<%@ attribute name="fieldPath" required="true" type="java.lang.String" %>
<%@ attribute name="fieldExample" required="false" type="java.lang.String" %>
<%@ attribute name="fieldHelp" required="false" type="java.lang.String" %>
<%@ attribute name="isRequired" required="false" type="java.lang.Boolean" %>

<div class="descriptive_row_table<c:if test="${(fieldHelp == null && fn:length(fieldHelp) == 0) && (fieldExample == null && fn:length(fieldExample) == 0)}"> descriptive_row_table_bottom_row</c:if>">
        <span class="descriptive_row_name">
            <span>${fieldName}<c:if test="${isRequired}">&nbsp;*</c:if></span>
        </span>
        <span class="descriptive_row_value">
            <span><springForm:input cssClass="descriptive_row_value"
                       path="${fieldPath}"/></span>
        </span>
</div>
<c:if test="${(fieldHelp != null && fn:length(fieldHelp) != 0) || (fieldExample != null && fn:length(fieldExample) != 0)}">
    <span class="descriptive_row_table descriptive_row_table_bottom_row">
            <span class="descriptive_row_name descriptive_row_help">
                <span>${fieldHelp}</span>
            </span>
            <span class="descriptive_row_value descriptive_row_example">
                <span>${fieldExample }</span>
            </span>
    </span>
</c:if>
