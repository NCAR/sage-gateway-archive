<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="OpenID Login"/>
    <tiles:putAttribute type="string" name="pageTitle" value="OpenID Login"/>

    <tiles:putAttribute name="body">

        <!-- display login errors -->
        <common:form-errors commandName="command"/>
        <common:login-errors/>

        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    You have requested to authenticate at <span style="font-weight: bold;"><c:out
                        value="${requestSite}"/></span> as <span style="font-weight: bold;"><c:out
                        value="${command.username}"/></span>
                </p>
            </div> <!-- .panel-body -->
        </div>

        <c:url value="/openid/login.html?${pageContext.request.queryString}" var="loginURL"/>
        <springForm:form method="post" name="loginForm" action="${loginURL}">
            <div class="row">
                <div class="col-xs-4">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <springForm:hidden path="username"/>
                            <browse:descriptive-row fieldName="OpenID" fieldValue="${command.username}" showEmpty="false" />

                            <div class="form-group">
                                <label for="password">
                                    Password
                                </label>
                                <springForm:password path="password" id="password" class="form-control"/>
                            </div> <!-- .form-group -->

                            <button type="submit" class="btn btn-default" id="login-button">Login</button>
                        </div> <!-- .panel-body -->
                    </div>
                </div> <!-- .col-xs-4 -->
            </div> <!-- .row -->

        </springForm:form>

        <p>
            <a href="<c:url value="/ac/guest/secure/emailPassword.html"/>">Forgot your Password?</a>
        </p>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
