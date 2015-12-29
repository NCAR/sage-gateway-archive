<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Login"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Login"/>

    <tiles:putAttribute name="body">

        <div class="panel panel-default">
            <div class="panel-body">
                <div style="font-size: 15px; color:red">Registration is not required to access data.</div>
                <p>
                    An account is required to contribute or edit data and metadata.
                </p>
            </div> <!-- .panel-body -->
        </div>

        <authz:authorize access="!hasAnyAuthority('group_User_role_default')">

            <common:form-errors commandName="command"/>
            <common:login-errors/>

            <c:url value="/login" var="loginURL"/>
            <springForm:form method="post" action='${loginURL}'>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="username">
                                        Username
                                    </label>
                                    <springForm:input path="username" id="username" class="form-control"/>
                                </div> <!-- .form-group -->

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
                <spring:message code="messages.security.registration.link" var="registrationLink"/>
                <a href='<c:url value="${registrationLink}"/>'>Register</a>
                &nbsp;|&nbsp;
                <a href='<c:url value="/ac/guest/secure/emailPassword.html"/>'>Forgot Your Password?</a>
            </p>
        </authz:authorize>
    </tiles:putAttribute>

</tiles:insertDefinition>
