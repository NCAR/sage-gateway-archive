<%@tag body-content="empty" %>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<!-- Command object -->
<%@ attribute name="command" required="true" type="sgf.gateway.web.controllers.security.ManageGroupUserCommand" %>

<br>
<div class="row">
    <div class="col-md-6">
        <div class="panel panel-default">
            <div class="panel-body">
                <c:choose>

                    <c:when test="${command.remove}">
                        User <span class="highlight"><c:out value="${command.userName}"/></span>
                        has been removed from group <span class="highlight"><c:out value="${command.groupName}"/></span>.
                    </c:when>

                    <c:otherwise>
                        User <span class="highlight"><c:out value="${command.userName}"/></span>
                        has been assigned the following roles in group <span class="highlight"><c:out
                            value="${command.groupName}"/></span>:
                        <p>&nbsp;</p>

                        <div align="center">
                            <c:set var="count" value="0"/>
                            <c:forEach var="membership" items="${command.user.memberships}">
                                <c:if test="${membership.group==command.group}">
                                    <span class="highlight"><c:if test="${count>0}">, </c:if><c:out
                                    value="${membership.role.description}"/></span>
                                    <c:set var="count" value="${count+1}"/>
                                </c:if>
                            </c:forEach>
                        </div>

                    </c:otherwise>
                </c:choose>
            </div> <!-- .panel-body -->
        </div>
    </div> <!-- .col-md-6 -->
</div> <!-- .row -->