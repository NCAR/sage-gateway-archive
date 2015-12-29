<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="View Projects Geographically"/>
    <tiles:putAttribute type="string" name="pageTitle" value="View Projects Geographically"/>

    <tiles:putAttribute name="body">

            <h2> MapServer</h2>

            <c:url var="redirectURLmapsurf" value="/redirect.html">
                <c:param name="link" value="http://mapserver.eol.ucar.edu/acadis/"/>
            </c:url>
            <a href="${redirectURLmapsurf}"><img src="<c:url value="/themes/cadis/images/map_surfer.jpg" />"
                                                 width="254" height="160"/></a>

            <p>
                <a href="${redirectURLmapsurf}">MapServer</a>
            </p>

            <p>
                Selected NSF-funded Arctic projects with the option of displaying alongside other field
                projects supported by NCAR.

            <p/>

            <p>
                Use the MapServer interface to view project locations in a traditional GIS environment.
                When the Healy is operating in the Arctic, supporting data are accessed using MapServer.
                MapSurfer is a full-featured GIS hosted on an NCAR server.
            </p>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
