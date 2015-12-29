<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Projects"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Projects"/>

    <tiles:putAttribute name="body">

        <p>${fn:length(projects)} entries</p>

        <c:forEach var="project" items="${projects}">
            <div style="padding-top: 10px;">
                <c:url var="projectLink" value="/project/${gateway:urlEncode(project.persistentIdentifier)}.html"/>
                <a href="${projectLink}"> ${project.name}</a> <br>

                <div style="padding-left: 6px; padding-right: 6px;'">
                    <gateway:Abbreviate maxWidth="250" value="${project.description}"/>
                </div>
            </div>
        </c:forEach>

        <br> <!-- puts one blank line at the bottom of the listing -->

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
