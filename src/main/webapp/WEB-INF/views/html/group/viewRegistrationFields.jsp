<%@ include file="/WEB-INF/views/html/common/include.jsp"%>

<tiles:insertDefinition name="general-layout">

	<tiles:putAttribute type="string" name="title" value="Group Registration Fields" />
	<tiles:putAttribute type="string" name="pageTitle" value="Group Registration Fields" />

	<tiles:putAttribute name="body">

		<common:success-message message="${successMessage}"/>

        <p>
            <a href="<c:url value="/ac/group/${group.name}/registrationField/form/add.html" />" >
                Add New Registration Field to Group
            </a>
            <br><br>
            <a href="<c:url value="/ac/group/${group.name}/registrationField/form/existing.html" />" >
                Add Existing Registration Field to Group
            </a>
        </p>
        <br>
        <p>${fn:length(registrationFields)} entries</p>

        <div class="row">
            <div class="col-md-6">
                <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Value</th>
                            <th>Type</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="groupData" items="${registrationFields}" varStatus="num">
                            <tr>
                                <td><c:out value="${groupData.name}"/></td>
                                <td><c:out value="${groupData.description}"/></td>
                                <td><c:out value="${groupData.value}"/></td>
                                <td><c:out value="${groupData.type.name}"/></td>
                                <td>
                                    <c:url var="deleteFile" value="/ac/group/${group.name}/registrationField/${groupData.identifier}/form/confirmDelete.html" />
                                    <a href="<c:out value="${deleteFile}"/>">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

	</tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>