<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<!-- image and search bar at top of page for all except extra small devices -->
<div class="pageHeaderImage hidden-xs">
    <!-- display ACADIS Gateway image -->
    <spring:message var="banner_image" code="banner.image" />
    <a href="<c:url value="/home.html" />">
        <img src="<c:url value="${banner_image}"/>"
             class="pageHeaderAcadisText"
             alt="ACADIS Gateway: An Arctic Data Repository" />
    </a>
    <!--
        Display ACADIS logo.
        Hide the image on extra small and small devices.
    -->
    <img src="<c:url value="/themes/cadis/images/acadis-service-logo.png"/>"
         class="pageHeaderAcadisLogo hidden-xs hidden-sm"
         alt="A Service of ACADIS" />

    <div class="pageHeaderRight hidden-xs">     <!-- hide the div on extra small devices -->
        <div class="searchBox pageHeaderSearch"  >
            <form name="simpleQueryForm" id="simpleQueryForm" class="form-inline" role="form" method="GET" action="<c:url value="/search.html"/>">
                <div class="form-group">
                    <label class="sr-only" for="freeText">Search</label>
                    <input type="text" name="freeText" id="freeText" size="32" class="form-control">
                    <button type="submit" class="btn btn-default" id="submit-simple-query">Search</button>
                </div>
            </form>
        </div>
    </div> <!-- .pageHeaderRight -->
</div> <!-- .pageHeaderImage -->

<!-- logo text only at top of page for extra small devices -->
<div class="pageHeaderNoImage visible-xs">
    <!-- display ACADIS Gateway image -->
    <spring:message var="banner_image" code="banner.image" />
    <a href="<c:url value="/home.html" />">
        <img src="<c:url value="${banner_image}"/>"
             class="pageHeaderAcadisTextNoImage"
             alt="ACADIS Gateway: An Arctic Data Repository" />
    </a>
</div> <!-- .pageHeaderNoImage -->

<!-- menu bar -->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value="/home.html" />">Home</a></li></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                       role="button" aria-haspopup="true" aria-expanded="false">
                        Get Data <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/search.html"/>">Search</a></li>
                        <li><a href="<c:url value="/scienceKeywordTopic/topic.html"/>">Science Keyword Topics</a></li>
                        <li><a href="<c:url value="/contact.html"/>">Principal Investigators</a></li>
                        <li><a href="<c:url value="/project.html"/>">Projects</a></li>
                        <li><a href="<c:url value="/cadis/viewProjectsGeographically.html"/>">Geographic Tools</a></li>
                    </ul>
                </li>
                <li><a href="<c:url value="/user/publish/contributeData.html"/>">Contribute Data</a></li>
                <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                    <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
                    <li><a href="<c:url value="${gatewayAdministrationLink }"/>">Administration</a></li>
                </authz:authorize>
                <li><a href="<c:url value="/cadis/about/aboutUs.htm"/>">About</a></li>
                <li><a href="<c:url value="/contactUs.htm"/>">Contact</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <authz:authorize access="!hasAnyAuthority('group_User_role_default')">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                           role="button" aria-haspopup="true" aria-expanded="false">
                            Sign in <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/ac/guest/secure/login.html"/>">Sign in</a></li>
                            <li><a href="<c:url value="/ac/guest/secure/registration.html"/>">Register</a></li>
                        </ul>
                    </authz:authorize>
                    <authz:authorize access="hasAnyAuthority('group_User_role_default')">
                        <security:authentication property="principal" var="principal" />
                        <a href="#" role="button" aria-haspopup="true" aria-expanded="false">
                           ${principal.user.name} <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/ac/user/index.html"/>">Account Summary</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="<c:url value="/logout"/>">Logout</a></li>
                        </ul>
                    </authz:authorize>
                </li>
            </ul>
        </div> <!-- .navbar-collapse -->
    </div>
</nav>



