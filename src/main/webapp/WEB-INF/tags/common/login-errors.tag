<%@tag body-content="empty" %>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<c:set var="authentication_exception"
       value="<%= org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION %>"/>
<c:if test="${!empty sessionScope[authentication_exception]}">
    <div class="panel panel-danger">
        <div class="panel-heading">ERROR</div>
        <div class="panel-body">
            Please fix the following error before continuing:
            <ul>
                <gateway:LoginErrorMessage/>
                <li><span>${errorMessage}</span></li>
            </ul>
            <c:if test="${!empty openidGuesses}">
                <div style="padding: 10px 0 0 0;">
                    Your OpenID might be one of the following:
                    <ul>
                        <c:forEach items="${openidGuesses}" var="guess">
                            <li>${guess}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </div> <!-- .panel-body -->
    </div>
</c:if>
