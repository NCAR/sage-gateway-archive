<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Push to Share"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Push to Share"/>

    <tiles:putAttribute name="body">

        <p>Push dataset ${dataset.shortName} to the SHARE service.</p>

        <form method="POST" action='<c:url value="/dataset/${dataset.shortName}/pushToShare.html"/>' style="display: inline;" >

            <button type="submit" class="btn btn-default" id="pushToShareButton">Push to Share</button>

            <c:url var="datasetLink" value="/dataset/${dataset.shortName}.html"></c:url>
            <a class="btn btn-default" href="${datasetLink}">Cancel</a><br>

        </form>

    </tiles:putAttribute>

</tiles:insertDefinition>