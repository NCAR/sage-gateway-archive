<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Edit Responsible Party"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Edit Responsible Party"/>

    <tiles:putAttribute name="body">

        <div>
            <c:url var="datasetLink" value="/dataset/${dataset.shortName}.html"></c:url>
            <b><a href="${datasetLink}">${dataset.title}</a></b>
        </div>

        <common:form-errors commandName="command"/>

        <br>
        <div class="row">
            <div class="col-md-6">

                <p class="small">
                    Required fields are marked with an asterisk <b>*</b>.
                </p>

                <c:url value="/project/${dataset.shortName}/responsibleParty/${responsibleParty.identifier}.html"
                       var="formAction"/>
                <springForm:form method="post" commandName="command" action="${formAction}">
                    <div class="form-group">
                        <label for="role">
                            Role *
                        </label>
                        <select id="role" name="role" class="form-control">
                                <%--
                                    Build the option list. When a role is available,
                                    use it to set the default.
                                 --%>
                            <c:forEach var="role" items="${responsiblePartyRoles}">
                                <option value="${role}" title="${role.roleDescription}"
                                        <c:if test="${role == responsibleParty.role}" >
                                            selected
                                        </c:if>
                                >
                                    ${role.roleName}
                                </option>
                            </c:forEach>
                        </select>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="individualName">
                            Individual Name *
                        </label>
                        <springForm:input path="individualName" id="individualName" class="form-control"/>
                        <span class="small">
                            Format: first_name (optional) middle_initial. last_name<br>
                            Examples:
                            <ul>
                                <li>Joe Smith</li>
                                <li>Joe R. Smith</li>
                            </ul>
                        </span>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="email">
                            Email Address
                        </label>
                        <springForm:input type="email" path="email" id="email" class="form-control"/>
                    </div> <!-- .form-group -->

                    <button type="submit" class="btn btn-default" id="updateButton">Update Responsible Party</button>
                </springForm:form>

            </div>
        </div> <!-- .row -->

        <style>
            <%-- This overrides the style so the name list is left justified (vs center) --%>
            .ui-menu-item {
                text-align: left;
            }

        </style>

        <script type="text/javascript">
            function init() {
                var availableNames = [${existingResponsiblePartiesString}];

                $("#individualName").autocomplete({
                    source: availableNames
                });

            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute>

</tiles:insertDefinition>