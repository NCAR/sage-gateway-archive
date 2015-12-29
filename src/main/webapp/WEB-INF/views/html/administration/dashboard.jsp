<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Dashboard"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Dashboard"/>

    <tiles:putAttribute name="body">

        <p>
            Disk Downloads: ${diskDownloadCount}
        </p>

        <p>
            SRM Downloads: ${srmDownloadCount}
        </p>

    </tiles:putAttribute>

</tiles:insertDefinition>