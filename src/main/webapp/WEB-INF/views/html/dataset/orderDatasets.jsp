<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Order Datasets"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Order Datasets"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div>
            <c:url var="datasetLink" value="/dataset/${dataset.shortName}.html"></c:url>
            <b><a href="${datasetLink}">${dataset.title}</a></b>
            <br>
        </div>
        <p><br>Drag and Drop to reorder Datasets.</p>
        <p>${fn:length(command.movingDatasets)} entries</p>

        <c:url var="orderAction" value="/dataset/${dataset.shortName}/orderDatasets.html"></c:url>

        <springForm:form method="post" action="${orderAction}" commandName="command" id="dsform">

            <ul class="sortable">

                <c:forEach var="ds" items="${command.movingDatasets}" varStatus="status">

                    <li>${ds.title}
                        <input class="inputclass" id="ds_${status.index}" type="hidden"
                               name="movingDatasets[${status.index}]" value="${ds.shortName}"/>
                    </li>

                </c:forEach>

            </ul>

            <br>
            <button type="submit" class="btn btn-default" id="save-button">Save</button>

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

                        // LOOP
                        $(".inputclass").each(function (idx, el) {
                            // Set new order by associating text input name using index (switch index)
                            $(this).attr('name', 'movingDatasets[' + idx + ']');
                        });
                    }
                }); // sortable()
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>