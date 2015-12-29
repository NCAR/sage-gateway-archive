<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Science Keyword Topics"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Science Keyword Topics"/>

    <tiles:putAttribute name="body">

        <p>${fn:length(scienceKeywordTopics)} entries</p>

        <c:forEach var="keywordTopic" items="${scienceKeywordTopics}">
            <div style="padding-top: 10px;">
                <c:url var="topicLink" value="/scienceKeywordTopic/${gateway:urlEncode(keywordTopic.key)}.html"/>
                <a href="${topicLink}"> ${keywordTopic.key}</a><span class="dataset_count">(${keywordTopic.value} Datasets)</span>
            </div>
        </c:forEach>

    </tiles:putAttribute>

</tiles:insertDefinition>
