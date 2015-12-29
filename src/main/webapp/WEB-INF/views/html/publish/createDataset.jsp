<%-- ESG > Admin > Create New Top-Level Dataset --%>

<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Create New Dataset"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Create New Dataset"/>

    <tiles:putAttribute name="body">
        <common:form-errors commandName="command"/>

        <p>
            Please enter the information below to create a new dataset.
        </p>

        <br>
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <c:choose>
                                <c:when test="${command.parentDataset !=null }">
                                    Enclosing Dataset Name: <c:out value="${command.parentDataset.title}"/>
                                    <br>
                                    Enclosing Dataset Identifier: <c:out value="${command.parentDataset.shortName}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${command.activity.name}"/> Top Level Dataset
                                </c:otherwise>
                            </c:choose>
                        </div> <!-- .panel-heading -->
                        <div class="panel-body">
                            <p class="small">
                                Required fields are marked with an asterisk <b>*</b>.
                            </p>

                            <springForm:form commandName="command">
                                <div class="form-group">
                                    <label for="datasetTitle">
                                        Title *
                                    </label>
                                    <springForm:input path="datasetTitle" id="datasetTitle" size="40" class="form-control"/>
                                </div> <!-- .form-group -->

                                <div class="form-group">
                                    <label for="datasetShortName">
                                        Short Name *
                                    </label>
                                    <springForm:input path="datasetShortName" id="datasetShortName" size="40" class="form-control"/>
                                </div> <!-- .form-group -->

                                <div class="form-group">
                                    <label for="doi">
                                        DOI
                                    </label>
                                    <springForm:input path="doi" id="doi" size="40" class="form-control"/>
                                </div> <!-- .form-group -->

                                <div class="form-group">
                                    <label>Dataset Type</label>

                                    <div class="checkbox">
                                        <label>
                                            <springForm:checkbox path="datasetTypes" id="checkboxData"
                                                                 value="GeophysicalDataset"/>
                                            Data
                                        </label>
                                    </div> <!-- .checkbox -->

                                    <div class="checkbox">
                                        <label>
                                            <springForm:checkbox path="datasetTypes" id="checkboxSoftware"
                                                                 value="Software"/>
                                            Analysis/Visualization Software
                                        </label>
                                    </div> <!-- .checkbox -->

                                    <div class="checkbox">
                                        <label>
                                            <springForm:checkbox path="datasetTypes" id="checkboxSoftware"
                                                                 value="ModelSourceCode"/>
                                            Model Source Code
                                        </label>
                                    </div> <!-- .checkbox -->

                                </div> <!-- .form-group -->

                                <button type="submit" class="btn btn-default" id="submit-button">Save</button>
                                <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
                                <a class="btn btn-default" href="<c:url value="${gatewayAdministrationLink }"/>">Cancel</a>
                            </springForm:form>
                        </div> <!-- .panel-body -->
                    </div>
                </div> <!-- .col-md-8 -->
            </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>
