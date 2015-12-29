<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="pageTitle" value="Topic: ${topic.name}"/>
    <tiles:putAttribute type="string" name="title" value="Topic: ${topic.name}"/>

    <tiles:putAttribute name="body">

        <cadis:dataset-list-panel datasets="${gateway:filterPublished(datasets)}" expandProjects="true"/>

    </tiles:putAttribute>

</tiles:insertDefinition>
