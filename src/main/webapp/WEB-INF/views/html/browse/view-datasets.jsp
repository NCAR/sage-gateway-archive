<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Top Level Collections"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Collection Browsing"/>

    <tiles:putAttribute name="script">

    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <browse:dataset-list-panel datasets="${collections}" title="Top Level Datasets"/>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
