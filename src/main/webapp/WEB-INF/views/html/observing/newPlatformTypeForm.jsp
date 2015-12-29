<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Platform Types"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Platform Types"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="row">
            <div class="col-md-4">
                <c:forEach items="${platformTypes}" var="platform">
                    <c:out value="${platform.shortName}"/>
                    <br/>
                </c:forEach>
            </div> <!-- .col-md-4 -->
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>

                        <springForm:form method="post" commandName="command" action="platform">
                            <browse:input-row fieldPath="shortName" fieldName="Short Name" isRequired="true"/>

                            <button type="submit" class="btn btn-default" id="addPlatformType">Add Platform Type</button>

                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->
        <br>

    </tiles:putAttribute>

</tiles:insertDefinition>