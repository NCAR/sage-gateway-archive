<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <!--
        The following 3 meta tags *must* come first in the head; any other head
        content must come *after* these tags
    -->
    <meta charset="utf-8">
    <!-- viewport set to for desired rendering and touch zooming -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- to ensure the best rendering mode for IE -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- favicon display -->
    <spring:message code="favicon" var="favicon"/>
    <link rel="icon" href="<c:url value="${favicon}" />" type="image/x-icon">

    <!-- title for the browser tab -->
    <title><tiles:getAsString name="title"/></title>

    <!-- include header CSS files -->
    <tiles:insertAttribute name="head"/>

    <spring:message code="styleSheet" var="styleSheet"/>
    <link rel="stylesheet" href="<c:url value="${styleSheet}"/>" type="text/css">
    <style type="text/css">
        <tiles:insertAttribute name="style" />
    </style>

    <tiles:insertAttribute name="links"/>

    <tiles:insertAttribute name="googleanalytics"/>
</head>

<body>

    <div id="pageContainer">

        <tiles:insertAttribute name="broadcast-message"/>

        <tiles:insertAttribute name="header"/>

        <div id="pageBody">
            <div class="container-fluid">
                <!-- title for the page -->
                <h1><tiles:getAsString name="pageTitle" ignore="true"/></h1>
                <noscript>
                    <%-- include the generic error handler --%>
                    <common:generic-error message="JavaScript must be enabled."/>
                </noscript>
                <div class="noDisplay">
                    <%-- include the generic error handler --%>
                    <common:generic-error message="Cookies must be enabled."/>
                </div>
                <tiles:getAsString name="body-header" ignore="true"/>
                <tiles:insertAttribute name="body" ignore="true"/>
            </div>
        </div> <!-- # pageBody -->

        <footer id="pageFooter">
            <tiles:insertAttribute name="footer" ignore="true"/>
        </footer>
    </div> <!-- #pageContainer -->

    <!-- include JavaScript files -->
    <tiles:insertAttribute name="scripts"/>

    <script>
        // test whether cookies are enabled in the browser
        if (!navigator.cookieEnabled) {
            document.write('<style>.noDisplay { display: block; }</style>');
        } // if
    </script>

</body>
</html>
