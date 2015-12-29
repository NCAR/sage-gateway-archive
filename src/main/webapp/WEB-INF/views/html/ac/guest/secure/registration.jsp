<%--
    Maintain this code in parallel with:
    WEB-INF/views/html/cadis/ac/guest/secure/registration.jsp
--%>

<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Register"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Register"/>

    <tiles:putAttribute name="body">

        <!-- display form validation errors -->
        <common:form-errors commandName="registrationCommand"/>

        <div>
            <spring:message var="test" code="legal.login1"/>
            <c:if test="${!empty test}">
                <p><spring:message code="legal.login1"/></p>
            </c:if>
            <spring:message var="test" code="legal.login2"/>
            <c:if test="${!empty test}">
                <p><spring:message code="legal.login2"/></p>
            </c:if>
            <p>We encourage you to read our <a href="<c:url value="/legal/privacy_policy.htm" />">Privacy
                Policy</a> and <a href="<c:url value="/legal/terms_of_use.htm" />">Terms of Use</a> prior to
                registration.
            </p>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p>
                            The fields below may only contain: letters, numbers, and the special characters
                            - _ . , : ; ' @ ( )
                        </p>

                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>
                        <springForm:form commandName="registrationCommand" method="post">
                            <div class="form-group">
                                <label for="username">
                                    Username *
                                </label>
                                <springForm:input path="username" id="username" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="password">
                                    Password *
                                </label>
                                <springForm:password path="password" id="password" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="confirmationPassword">
                                    Confirm Password *
                                </label>
                                <springForm:password path="confirmationPassword" id="confirmationPassword" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="firstName">
                                    First Name *
                                </label>
                                <springForm:input path="firstName" id="firstName" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="lastName">
                                    Last Name *
                                </label>
                                <springForm:input path="lastName" id="lastName" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="email">
                                    Email Address *
                                </label>
                                <springForm:input type="email" path="email" id="email" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group hidden">
                                <label for="organization">
                                    Organization
                                </label>
                                <springForm:input path="organization" id="organization" class="form-control" title="Leave Blank"/>
                            </div> <!-- .form-group -->

                            <button type="submit" class="btn btn-default" id="submit-button">
                                Register
                            </button>
                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->
    </tiles:putAttribute>
</tiles:insertDefinition>