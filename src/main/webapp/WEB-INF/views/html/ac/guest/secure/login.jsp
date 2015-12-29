<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Administrator Login"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Administrator Login"/>

    <tiles:putAttribute name="body">

        <authz:authorize access="!hasRole('group_User_role_default')">

            <common:form-errors commandName="command"/>
            <common:login-errors/>

            <div class="panel panel-default">
                <div class="panel-body">
                    <spring:message var="test" code="legal.login1"/>
                    <c:if test="${!empty test}">
                        <p><spring:message code="legal.login1"/></p>
                    </c:if>
                    <spring:message var="test" code="legal.login2"/>
                    <c:if test="${!empty test}">
                        <p><spring:message code="legal.login2"/></p>
                    </c:if>
                    <p>
                        We encourage you to read our <a href="<c:url value="/legal/privacy_policy.htm" />">Privacy
                        Policy</a> and <a href="<c:url value="/legal/terms_of_use.htm" />">Terms of Use</a> prior to
                        login.
                    </p>
                </div> <!-- .panel-body -->
            </div>

            <!-- Standard Login -->
            <c:url value="/login" var="loginURL"/>
            <div class="row">
                <div class="col-xs-4">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <springForm:form method="post" action="${loginURL}">
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

                                <button type="submit" class="btn btn-default">Login</button>
                            </springForm:form>
                        </div> <!-- .panel-body -->
                    </div>
                </div> <!-- .col-xs-4 -->
            </div> <!-- .row -->

        </authz:authorize>

        <authz:authorize access="hasRole('group_User_role_default')">
            <p>
                You are already logged in as: <c:out value="${user.openid}"/>
            </p>

            <p>
                Please <a href="<c:url value="/logout" />">Logout</a> if you wish to login as a different User.
            </p>
        </authz:authorize>
    </tiles:putAttribute>

</tiles:insertDefinition>