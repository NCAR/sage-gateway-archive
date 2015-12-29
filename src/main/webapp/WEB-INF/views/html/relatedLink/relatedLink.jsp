<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Related Links"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Related Links"/>


    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div class="return_link">
            <b><a href="<c:url value="/dataset/${dataset.shortName}.html" />">${dataset.title}</a></b>
            <br><br>
        </div>

        <gateway:authForWrite dataset="${dataset}">
            <p>
                <c:url var="newRelatedLink" value="/dataset/${dataset.shortName}/relatedLink/form/add.html"/>
                <a href="${newRelatedLink}">Add New Related Link</a>
            </p>
        </gateway:authForWrite>

        <p>${fn:length(relatedLinks)} entries</p>

        <div class="row">
            <div class="col-md-6">
                <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                    <thead>
                        <tr>
                            <th>Description</th>
                            <th>Link URI</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="relatedLink" items="${relatedLinks}" varStatus="num">
                            <tr>
                                <td><c:out value="${relatedLink.text}"/></td>
                                <td><c:out value="${relatedLink.uri}"/></td>
                                <td>
                                    <gateway:authForWrite dataset="${dataset}">
                                        <c:url var="editLink"
                                               value="/dataset/${dataset.shortName}/relatedLink/${relatedLink.identifier}/form/edit.html"/>
                                        <a href="<c:out value="${editLink}"/>">Edit</a>
                                    </gateway:authForWrite>
                                </td>
                                <td>
                                    <gateway:authForWrite dataset="${dataset}">
                                        <c:url var="deleteLink"
                                               value="/dataset/${dataset.shortName}/relatedLink/${relatedLink.identifier}/form/delete.html"/>
                                        <a href="<c:out value="${deleteLink}"/>">Delete</a>
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