<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${keyword.displayText}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="${keyword.displayText}"/>


    <tiles:putAttribute name="body">
        <cadis:dataset-list-panel datasets="${gateway:filterPublished(datasets)}"/>

    </tiles:putAttribute>

</tiles:insertDefinition>
