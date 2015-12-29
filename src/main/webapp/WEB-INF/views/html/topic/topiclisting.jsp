<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Topics"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Topics"/>

    <tiles:putAttribute name="body">

        <c:forEach var="topic" items="${topics}">
            <div style="padding-top: 10px;">
                <c:url var="topicLink" value="/topic/${gateway:urlEncode(topic.identifier)}.html"/>
                <a href="${topicLink}"> ${topic.name}</a> <br>

            </div>
        </c:forEach>

    </tiles:putAttribute>

</tiles:insertDefinition>
