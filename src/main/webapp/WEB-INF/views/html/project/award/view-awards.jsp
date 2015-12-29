<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Award Numbers"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Award Numbers"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div>
            <b><a href="<c:url value="/project/${dataset.shortName}.html" />">${dataset.title}</a></b>
            <br>
        </div>

        <p>
            <br>
            <c:url var="awardLink" value="/project/${dataset.shortName}/awardeditor.html"/>
            <a href="${awardLink}">Add new Award Number to this Project</a>
        </p>
        <div>${fn:length(awards)} entries</div>
        <br>

        <div class="row">
            <div class="col-md-4">
                <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                    <thead>
                        <tr>
                            <th>Award Number</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="award" items="${awards}" varStatus="num">
                            <tr>
                                <td><c:out value="${award.awardNumber}"/></td>
                                <td>
                                    <gateway:authForWrite dataset="${dataset}">
                                        <c:url var="removeAward"
                                               value="/project/${dataset.shortName}/awardNumber/${gateway:urlEncode(award.awardNumber)}/form/confirmDelete.html"/>
                                        <a href="<c:out value="${removeAward}"/>">Delete</a>
                                    </gateway:authForWrite>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div> <!-- .col-md-4 -->
        </div> <!-- .row -->

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
