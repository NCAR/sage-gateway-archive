<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Edit Collection Metadata"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Edit Collection Metadata"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">

                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>

                        <springForm:form commandName="command">

                            <div class="descriptive_row_table descriptive_row_table_bottom_row">
                                <span class="descriptive_row_name">
                                    <span>Short Name</span>
                                </span>
                                <span class="descriptive_row_value">
                                    <span>${command.shortName}</span>
                                </span>
                            </div>

                            <div class="form-group">
                                <label for="title">
                                    Title *
                                </label>
                                <springForm:input path="title" id="title" size="60" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="description">
                                    Description
                                </label>
                                <springForm:textarea path="description" id="description" cols="60" rows="4" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="doi">
                                    DOI
                                </label>
                                <springForm:input path="doi" id="doi" size="40" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="projectId">
                                    Project *
                                </label>
                                <springForm:select path="projectId" multiple="false" class="form-control">
                                    <springForm:option value="">--INHERITED--</springForm:option>
                                    <c:forEach items="${command.allProjects}" var="prj">
                                        <springForm:option value="${prj.identifier}">
                                            <c:out value="${prj.name}"/>
                                        </springForm:option>
                                    </c:forEach>
                                </springForm:select>
                            </div> <!-- .form-group -->

                            <label>
                                State *
                                <label class="radio-inline">
                                    <springForm:radiobutton path="datasetState" id="datasetState" value="PUBLISHED"/>
                                    Published
                                </label>
                                <label class="radio-inline">
                                    <springForm:radiobutton path="datasetState" id="datasetState" value="RETRACTED"/>
                                    Retracted
                                </label>
                            </label> <!-- .radio -->

                            <div class="row">
                                <div class="col-xs-5">
                                    <div class="form-group">
                                        <label for="readGroups">
                                            Groups Authorized for Reading *
                                        </label>
                                        <springForm:select path="readGroups" id="readGroups" multiple="true" class="form-control">
                                            <springForm:option value="">--INHERITED--</springForm:option>
                                            <c:forEach items="${command.allGroups}" var="group">
                                                <springForm:option value="${group.name}"/>
                                                <c:out value="${group.name}"/>
                                            </c:forEach>
                                        </springForm:select>
                                    </div> <!-- .form-group -->
                                </div>

                                <div class="col-xs-5">
                                    <div class="form-group">
                                        <label for="readGroups">
                                            Groups Authorized for Writing *
                                        </label>
                                        <springForm:select path="writeGroups" id="writeGroups" multiple="true" class="form-control">
                                            <springForm:option value="">--INHERITED--</springForm:option>
                                            <c:forEach items="${command.allGroups}" var="group">
                                                <springForm:option value="${group.name}"/>
                                                <c:out value="${group.name}"/>
                                            </c:forEach>
                                        </springForm:select>
                                    </div> <!-- .form-group -->
                                </div>
                            </div> <!-- .row -->

                            <button type="submit" class="btn btn-default" id="submit-button">Save</button>
                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-8 -->
        </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>
