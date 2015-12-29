<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Create New Project"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Create New Project"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <p>
            Please enter the information below to create a new project.
        </p>

        <br>
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>
                        <springForm:form commandName="command">
                            <div class="form-group">
                                <label for="title">
                                    Title *
                                </label>
                                <springForm:input path="title" id="title" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="description">
                                    Description *
                                </label>
                                <springForm:textarea path="description" id="description" rows="5" class="form-control"/>
                            </div> <!-- .form-group -->

                            <button type="submit" class="btn btn-default" id="submit-button">Create Project</button>
                            <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
                            <a class="btn btn-default" href="<c:url value="${gatewayAdministrationLink }"/>">Cancel</a>
                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>
