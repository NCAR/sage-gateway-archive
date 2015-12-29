<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout" >

    <tiles:putAttribute type="string" name="title" value="Add Registration Field" />
    <tiles:putAttribute type="string" name="pageTitle" value="Add Registration Field" />

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command" />

        <c:url value="/ac/group/${group.name}/registrationField.html" var="formAction"/>

        <div class="row">
            <div class="col-md-6">

                <p class="small">
                    Required fields are marked with an asterisk <b>*</b>.
                </p>

                <springForm:form method="post" commandName="command" action="${formAction}" >
                    <springForm:hidden path="identifier" />
                    <div class="form-group">
                        <label for="name">
                            Name *
                        </label>
                        <springForm:input path="name" id="name" class="form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="description">
                            Description *
                        </label>
                        <springForm:textarea path="description" id="description" rows="4" class="form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="value">
                            Value
                        </label>
                        <springForm:textarea path="value" id="value" rows="4" class="form-control"/>
                    </div> <!-- .form-group -->

                    <div class="row">
                        <div class="col-xs-4">
                            <div class="form-group">
                                <label for="groupDataType">
                                    Type *
                                </label>
                                <springForm:select path="groupDataType" multiple="false" class="form-control">
                                    <springForm:options items="${groupDataTypes}" itemLabel="name" />
                                </springForm:select>
                            </div> <!-- .form-group -->
                        </div> <!-- .col-xs-4 -->
                    </div> <!-- .row -->

                    <button type="submit" class="btn btn-default" id="addButton">Add Registration Field</button>
                </springForm:form>
            </div>
        </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>