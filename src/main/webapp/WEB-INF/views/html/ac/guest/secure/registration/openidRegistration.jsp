<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Welcome to the Earth System Grid"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Welcome to the Earth System Grid"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    Thank you for using OpenID to login to this website.
                </p>

                <p>
                    Before continuing, for this website to work properly, we need a small amount of personal
                    information. Please enter the requested information below before continuing.
                </p>
                <spring:message var="test" code="legal.login1"/>
                <c:if test="${!empty test}">
                    <p>
                        <spring:message code="legal.login1"/>
                    </p>
                </c:if>
                <spring:message var="test" code="legal.login2"/>
                <c:if test="${!empty test}">
                    <p>
                        <spring:message code="legal.login2"/>
                    </p>
                </c:if>
                <p>We encourage you to read our <a href="<c:url value="/legal/privacy_policy.htm" />">Privacy
                    Policy</a> and <a href="<c:url value="/legal/terms_of_use.htm" />">Terms of Use</a> prior to
                    registration.
                </p>
            </div> <!-- .panel-body -->
        </div>

        <%-- Registration panel --%>
        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    The fields below may only contain: letters, numbers, and the special characters
                    - _ . , : ; ' @ ( )
                </p>

                <p class="small">
                    Required fields are marked with an asterisk <b>*</b>.
                </p>
                <springForm:form commandName="command" method="post">
                    <div class="descriptive_row_table descriptive_row_table_bottom_row">
                        <span class="descriptive_row_name">
                            <span>OpenID</span>
                        </span>
                        <span class="descriptive_row_value">
                            <span>${command.openid}</span>
                        </span>
                    </div>

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

                    <button type="submit" class="btn btn-default" name="_eventId_submit" id="submit-button">
                        Continue
                    </button>
                </springForm:form>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>