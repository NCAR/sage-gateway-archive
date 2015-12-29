<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Get Data"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Get Data"/>

    <tiles:putAttribute name="body">

        <h3>Search for Data</h3>
        <ul>
            <li><a href="<c:url value="/search.html"/>">Search</a></li>
        </ul>
        <h3>Browse for Data</h3>
        <ul>
            <li><a href="<c:url value="/scienceKeywordTopic/topic.html"/>">Science Keyword Topics</a></li>
            <li><a href="<c:url value="/contact.html"/>">Principal Investigators</a></li>
            <li><a href="<c:url value="/project.html"/>">Projects</a></li>
            <li><a href="<c:url value="/cadis/viewProjectsGeographically.html"/>">Geographic Tools</a></li>
        </ul>
        <h3>Additional Arctic Data Resources</h3>
        <ul>
            <c:url var="adeLink" value="/redirect.html">
                <c:param name="link" value="http://nsidc.org/acadis/"/>
            </c:url>
            <li><a href="${adeLink}">Arctic Data Explorer</a></li>
        </ul>

    </tiles:putAttribute>

</tiles:insertDefinition>
