<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${topic.name}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="${topic.name}"/>

    <tiles:putAttribute name="body">

        <browse:dataset-list-panel datasets="${gateway:filterPublished(datasets)}"
                                   title="Associated Datasets"/>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
