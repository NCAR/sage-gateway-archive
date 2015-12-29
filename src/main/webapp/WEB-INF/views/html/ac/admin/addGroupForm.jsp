<%-- ESG group creation --%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout" >

    <tiles:putAttribute type="string" name="title" value="Create New Group" />
    <tiles:putAttribute type="string" name="pageTitle" value="Create New Group" />

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <p>
            Please enter the information below to create a new group.
        </p>

        <br>
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>
                        <springForm:form method="post">
                            <div class="form-group">
                                <label for="groupName">
                                    Group Name *
                                </label>
                                <springForm:input path="groupName" id="groupName" size="30" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="groupDescription">
                                    Description *
                                </label>
                                <springForm:textarea path="groupDescription" id="groupDescription" cols="40" rows="3" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="checkbox">
                                <label>
                                    <springForm:checkbox path="automaticApproval" id="automaticApproval"/>
                                    Automatic Approval
                                </label>
                            </div> <!-- .checkbox -->

                            <div class="checkbox">
                                <label>
                                    <springForm:checkbox path="visibleToUsers" id="visibleToUsers"/>
                                    Visible to Users
                                </label>
                            </div> <!-- .checkbox -->

                            <button type="submit" class="btn btn-default" id="submit-button">Submit</button>
                            <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
                            <a class="btn btn-default" href="<c:url value="${gatewayAdministrationLink }"/>">Cancel</a>
                        </springForm:form>
                    </div>
                </div>
            </div>
        </div> <!-- .row -->

    </tiles:putAttribute>
</tiles:insertDefinition>
