<%-- ACADIS Order Responsible Parties --%>

<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Order Responsible Parties"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Order Responsible Parties"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div>
            <c:url var="datasetLink" value="/dataset/${dataset.shortName}.html"></c:url>
            <b><a href="${datasetLink}">${dataset.title}</a></b>
            <br>
        </div>
        <p><br>Drag and Drop to reorder Responsible Parties.</p>
        <p>${fn:length(command.responsibleParties)} entries</p>

        <c:url var="orderAction" value="/project/${dataset.shortName}/orderResponsibleParties.html"></c:url>

        <springForm:form method="post" action="${orderAction}" commandName="command" id="rpform">

            <ul class="sortable">

                <c:forEach var="rp" items="${command.responsibleParties}" varStatus="status">

                    <li>${rp.individualName}
                        <span style="float:right;">(<c:out value="${rp.role.roleName}"/>)</span>
                        <input class="inputclass" id="party_${status.index}" type="hidden"
                               name="responsibleParties[${status.index}]" value="${rp.identifier}"/>
                    </li>

                </c:forEach>

            </ul>

            <br>
            <button type="submit" class="btn btn-default" id="save-button">Save</button>
            <br>

        </springForm:form>

        <script type="text/javascript">
            function init() {
                $(".sortable").sortable({
                    //axis: 'y',
                    // parent element constrains sortable items while dragging
                    containment: "parent",
                    tolerance: "pointer",
                    opacity: 0.7,

                    update: function (event, ui) {
                        $(".inputclass").each(function (idx, el) {
                            // Set new name using index!!
                            $(this).attr('name', 'responsibleParties[' + idx + ']');
                        });
                    }
                }); // sortable()
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>