<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Responsible Parties"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Responsible Parties"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div>
            <c:url var="datasetLink" value="/dataset/${dataset.shortName}.html"></c:url>
            <b><a href="${datasetLink}">${dataset.title}</a></b>
            <br>
        </div>
        <br>

        <gateway:authForWrite dataset="${dataset}">
            <div>
                <a href="<c:url value="/dataset/${dataset.shortName}/responsibleParty/form/add.html" />">Add New
                    Responsible Party</a>
                <br><br>
                <c:if test="${fn:length(responsibleParties) > 1}">
                    <a href="<c:url value="/dataset/${dataset.shortName}/responsibleParty/form/order.html" />">Order
                        Responsible Parties</a>
                    <br><br>
                </c:if>
            </div>
        </gateway:authForWrite>
        <p>${fn:length(responsibleParties)} entries</p>

        <div class="row">
            <div class="col-md-8">
                <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                    <thead>
                        <tr>
                            <th>Role</th>
                            <th>Individual Name</th>
                            <th>Email Address</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="responsibleParty" items="${responsibleParties}" varStatus="num">
                            <tr>
                                <td>${responsibleParty.role.roleName}</td>
                                <td>${responsibleParty.individualName}</td>
                                <td>${responsibleParty.email}</td>
                                <td>
                                    <gateway:authForWrite dataset="${dataset}">
                                        <c:url var="editFile"
                                               value="/dataset/${dataset.shortName}/responsibleParty/${responsibleParty.identifier}/form/edit.html"/>
                                        <a href="${editFile}">Edit</a>
                                    </gateway:authForWrite>
                                </td>
                                <td>
                                    <gateway:authForWrite dataset="${dataset}">
                                        <c:url var="deleteFile"
                                               value="/dataset/${dataset.shortName}/responsibleParty/${responsibleParty.identifier}/form/delete.html"/>
                                        <a href="${deleteFile}">Delete</a>
                                    </gateway:authForWrite>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>