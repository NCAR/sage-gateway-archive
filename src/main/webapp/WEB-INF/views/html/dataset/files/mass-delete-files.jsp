<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Delete Files"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Delete Files"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        Delete files for ${dataset.title}
        <br><br/>

        <div>
            <button type="button" id="btn-check-all" class="btn btn-primary">Check All</button> <span
                style="padding: 15px;"></span>
            <button type="button" id="btn-uncheck-all" class="btn btn-primary">Un-Check All</button> <span
                style="padding: 15px;"></span>
            <button type="button" id="btn-invert" class="btn btn-primary">Invert Selection</button><br>
        </div>

        <c:url var="massDeleteFile" value="/dataset/${dataset.shortName}/confirmMassDeleteFileForm.html"/>

        <springForm:form method="POST" commandName="command" action="${massDeleteFile}">
            <div><br>${fn:length(files)} entries</div>

            <div class="row">
                <div class="col-md-6">
                    <table id="users-table" class="table table-condensed table-bordered table-hover table-striped">
                        <thead>
                            <tr>
                                <th></th>
                                <th>File Name</th>
                                <th>Size</th>
                                <th>Date Modified</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="logicalFileRow" items="${files}" varStatus="num">
                                <tr>
                                    <td>
                                        <input type="checkbox" class="filesToDelete" name="filesToDelete" value="${logicalFileRow.name}">
                                    </td>
                                    <td>
                                        ${gateway:formatWordWrap(logicalFileRow.name)}
                                    </td>
                                    <td>
                                        <gateway:FileSizeUnitFormat fileSize="${logicalFileRow.size}"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate pattern="yyyy-MM-dd H:mm:ss"
                                                        value="${logicalFileRow.dateUpdated}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${!empty files}">
                        <button type="submit" class="btn btn-default" id="submit-button">Delete Files</button>
                    </c:if>
                </div> <!-- .col-md-6 -->
            </div> <!-- .row -->

        </springForm:form>
        <br>

        <script type="text/javascript">
            function init() {
                $('#btn-check-all').on('click', function buttonCheckAllClick() {
                    $(".filesToDelete").prop("checked", true);
                });

                $('#btn-uncheck-all').on('click', function buttonUncheckAllClick() {
                    $(".filesToDelete").prop("checked", false);
                });

                $('#btn-invert').on('click', function buttonInvertClick() {
                    $(".filesToDelete").each(function () {
                        if ($(this).is(":checked")) {
                            $(this).prop("checked", false);
                        } else {
                            $(this).prop("checked", true);
                        }
                    });
                });
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
