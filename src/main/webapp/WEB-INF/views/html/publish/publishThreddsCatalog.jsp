<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Publish THREDDS Catalog"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Publish THREDDS Catalog"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <p>
            Please enter the information below to publish the THREDDS catalog.
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
                            <browse:input-row fieldPath="threddsCatalogURI" fieldName="THREDDS Catalog URI" isRequired="true"/>
                            <browse:input-row fieldPath="parentDatasetIdentifier" fieldName="Enclosing Dataset Short Name"
                                              isRequired="false"/>
                            <br/>
                            <button type="submit" class="btn btn-default">Publish Catalog</button>
                            <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
                            <a class="btn btn-default" href="<c:url value="${gatewayAdministrationLink }"/>">Cancel</a>
                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>
