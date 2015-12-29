<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Add Responsible Party"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Add Responsible Party"/>

    <tiles:putAttribute name="body">

        <div>
            <c:url var="datasetLink" value="/project/${dataset.shortName}.html"></c:url>
            <b><a href="${datasetLink}">${dataset.title}</a></b>
        </div>

        <common:form-errors commandName="command"/>

        <c:url value="/project/${dataset.shortName}/responsibleParty.html" var="formAction"/>

        <br>
        <p class="small">
            Required fields are marked with an asterisk <b>*</b>.
        </p>

        <springForm:form method="post" commandName="command" action="${formAction}">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="partyRole">
                            Role *
                        </label>
                        <select id="partyRole" name="role" class="form-control">
                            <c:forEach var="role" items="${responsiblePartyRoles}">
                                <option value="${role}" title="${role.roleDescription}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="individualName">
                            Individual Name *
                        </label>
                        <springForm:input path="individualName" id="individualName" class="form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <span class="small">Format: First_name (optional) Middle_initial. Last_name<br>Examples:
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

                    <button type="submit" class="btn btn-default" id="addButton">Add Responsible Party</button>
                </div>
            </div> <!-- .row -->
        </springForm:form>

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

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>