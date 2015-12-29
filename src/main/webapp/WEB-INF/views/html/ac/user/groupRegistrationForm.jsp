<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Group Registration: ${group.name}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Group Registration: ${group.name}"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>
                        <springForm:form commandName="command" method="post">
                            <c:forEach var="groupData" items="${groupDataSet}">
                                <c:choose>
                                    <c:when test="${groupData.type == groupDataShortText}">
                                        <browse:input-row fieldPath="groupData[${groupData.identifier}]"
                                                          fieldName="${groupData.name}" fieldExample="${groupData.description}"
                                                          isRequired="${group.groupData[groupData]}"/>
                                    </c:when>
                                    <c:when test="${groupData.type == groupDataLongText}">
                                        <browse:input-textarea fieldPath="groupData[${groupData.identifier}]"
                                                               fieldName="${groupData.name}" fieldHelp="${groupData.description}"
                                                               isRequired="${group.groupData[groupData]}"/>
                                    </c:when>
                                    <c:when test="${groupData.type == groupDataMailingList}">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" value="true"
                                                       name="groupData[${groupData.identifier}]"
                                                       CHECKED/>
                                                ${groupData.name}
                                            </label>
                                        </div> <!-- .checkbox -->
                                        <span class="small">${groupData.description}</span
                                    </c:when>
                                    <c:when test="${groupData.type == groupDataLicense}">
                                        ${groupData.name}.${groupData.type}</br>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                            <br/>
                            <button type="submit" class="btn btn-default" id="submit-button">Submit</button>
                            <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
                            <c:url var="cancelRegistration" value="/ac/user/listAvailableGroups.htm"/>
                            <a class="btn btn-default" href="<c:url value="${cancelRegistration}"/>">Cancel</a>
                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>