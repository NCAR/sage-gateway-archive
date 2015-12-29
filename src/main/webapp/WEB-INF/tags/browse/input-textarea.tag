<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ attribute name="fieldName" required="true" type="java.lang.String" %>
<%@ attribute name="fieldPath" required="true" type="java.lang.String" %>
<%@ attribute name="fieldExample" required="false" type="java.lang.String" %>
<%@ attribute name="fieldHelp" required="false" type="java.lang.String" %>
<%@ attribute name="isRequired" required="false" type="java.lang.Boolean" %>

<div class="descriptive_row_table">
    <span class="descriptive_row_name">
        <span>${fieldName}<c:if test="${isRequired}">&nbsp;*</c:if></span>
    </span>
</div>
<c:if test="${(fieldHelp != null && fn:length(fieldHelp) != 0)}">
    <div class="descriptive_row_table">
        <span class="descriptive_textarea_help">
            <span>${fieldHelp}</span>
        </span>
    </div>
</c:if>
<div class="descriptive_row_table<c:if test="${(fieldHelp == null && fn:length(fieldHelp) == 0) && (fieldExample == null && fn:length(fieldExample) == 0)}"> descriptive_row_table_bottom_row</c:if>">
    <span class="descriptive_row_textarea">
        <span><springForm:textarea cssClass="descriptive_row_textarea" path="${fieldPath}"/></span>
    </span>
</div>
<c:if test="${(fieldExample != null && fn:length(fieldExample) != 0)}">
    <div class="descriptive_row_table descriptive_row_table_bottom_row">
        <span class="descriptive_textarea_example">
            <span>${fieldExample }</span>
        </span>
    </div>
</c:if>
