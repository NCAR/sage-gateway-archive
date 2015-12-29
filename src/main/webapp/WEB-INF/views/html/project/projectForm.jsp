<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${pageTitle}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="${pageTitle}"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="row">
            <div class="col-md-6">

                <p class="small">
                    Required fields are marked with an asterisk <b>*</b>.
                </p>

                <c:url value="${postUri}" var="formAction"/>
                <springForm:form method="post" commandName="command" action="${formAction}">

                    <springForm:hidden path="identifier"/>
                    <div class="form-group">
                        <label for="title">
                            Title *
                        </label>
                        <springForm:input path="title" id="title" class="form-control"/>
                    </div> <!-- .form-group -->

                    <browse:input-row fieldPath="shortName" fieldName="Short Name" isRequired="true"/>

                    <browse:input-textarea fieldPath="description" fieldName="Description" isRequired="true"/>
                    <browse:input-row fieldPath="projectGroup" fieldName="Project Group"/>

                    <browse:input-row fieldPath="startDate" fieldName="Period Of Performance Start Date"
                                      fieldExample="Format: yyyy-mm-dd" isRequired="true"/>
                    <browse:input-row fieldPath="endDate" fieldName="Period Of Performance End Date"
                                      fieldExample="Format: yyyy-mm-dd"/>

                    <browse:input-row fieldPath="northernLatitudeString" fieldName="Northern Latitude"
                                      fieldHelp="Must be between -90 and 90 degrees" isRequired="true"/>
                    <browse:input-row fieldPath="southernLatitudeString" fieldName="Southern Latitude"
                                      fieldHelp="Must be between -90 and 90 degrees" isRequired="true"/>
                    <browse:input-row fieldPath="westernLongitudeString" fieldName="Western Longitude"
                                      fieldHelp="Must be between -180 and 180 degrees" isRequired="true"/>
                    <browse:input-row fieldPath="easternLongitudeString" fieldName="Eastern Longitude"
                                      fieldHelp="Must be between -180 and 180 degrees" isRequired="true"/>
                    <div></div> <!-- div to force newline -->

                    <button type="submit" class="btn btn-default" id="createProject">${buttonText}</button>
                </springForm:form>
            </div>
        </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>