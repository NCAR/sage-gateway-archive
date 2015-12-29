<%@ include file="/WEB-INF/views/html/common/include.jsp"%>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Add Existing Group Registration Fields" />
    <tiles:putAttribute type="string" name="pageTitle" value="Add Existing Group Registration Fields" />

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <c:url value="/ac/group/${group.name}/registrationField/form/existing.html" var="formAction"/>
        <br>
        <p>${fn:length(groupDataCollection)} entries</p>

        <springForm:form method="post" commandName="command" action="${formAction}" >
            <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                <thead>
                    <tr>
                        <th>Select</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Value</th>
                        <th>Type</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="groupData" items="${groupDataCollection}" varStatus="num">
                        <tr>
                            <td>
                                <springForm:checkbox path="groupDataIdentifiers" value="${groupData.identifier}" />
                            </td>
                            <td>${groupData.name}</td>
                            <td>${groupData.description}</td>
                            <td>${groupData.value}</td>
                            <td>${groupData.type.name}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br>
            <input type="submit" class="btn btn-default" id="addButton" value="Add Existing Registration Field(s)" />
        </springForm:form>
        <br>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>