<%@tag body-content="empty"%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<!-- Users map containing (User,Status) pairs. -->
<%@ attribute name="users" required="true" type="java.util.Map"  %>

<!-- Optional status to filter the users map. -->
<%@ attribute name="status" required="false" type="java.lang.Integer"  %>

<p>&nbsp;</p>
<p>${fn:length(users)} entries</p>

<table id="group-users-table" class="table table-condensed table-bordered table-hover table-striped">
    <thead>
        <th>Last Name</th>
        <th>First Name</th>
        <th>User Name</th>
        <th class="hidden">UUID</th>
        <th>Email</th>
        <th class="hidden">Enroll</th>
        <th>Status</th>
    </thead>
    <tbody>
        <c:forEach var="entry" items="${users}">

            <!-- status=null used when enrolling users in bulk
                 status=0 used when listing all users, whether in group or not (when adding a new user)
                 status=entry.value.id used when listing group users in a specific state -->
            <c:if test="${status==null || status==0 || status==entry.value.id}">
                <tr>

                    <td><c:out value="${entry.key.lastName}" /></td>
                    <td><c:out value="${entry.key.firstName}" /></td>
                    <td><c:out value="${entry.key.userName}" /></td>
                    <td class="hidden uuid"><c:out value="${entry.key.identifier}" /></td>
                    <td><c:out value="${entry.key.email}" /></td>
                    <td class="hidden"><input type="checkbox" value=""></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty entry.value}">
                                <c:if test="${entry.value.name=='valid'}">
                                    Enrolled
                                </c:if>
                                <c:if test="${entry.value.name=='pending'}">
                                    Waiting Approval
                                </c:if>
                                <c:if test="${entry.value.name=='requested'}">
                                    Must Confirm Email
                                </c:if>
                                <c:if test="${entry.value.name=='invalid'}">
                                    Disabled
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                Not Enrolled
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </tbody>
</table>
<p>&nbsp;</p>
