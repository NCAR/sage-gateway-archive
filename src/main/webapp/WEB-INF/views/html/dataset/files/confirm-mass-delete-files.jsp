<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">
    <tiles:putAttribute type="string" name="title" value="Confirm Mass Delete Files"/>


    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Mass Delete Files"/>
    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                <c:url var="deleteFiles" value="/dataset/${dataset.shortName}/file.html"/>

                <springForm:form style="display: inline;" action="${deleteFiles}" method="POST" commandName="command">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="deleteConfirmed" value="true"/>

                    <%-- list files needed for form input --%>
                    <c:forEach var="file" items="${command.filesToDelete}">
                        <input type="hidden" name="filesToDelete" value="${file}"/>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${!empty command.filesToDelete}">
                            Are you sure you want to delete these files?
                            <%-- display file list for user --%>
                            <ul>
                                <c:forEach var="file" items="${command.filesToDelete}">
                                    <li>${file}</li>
                                </c:forEach>
                            </ul>
                            Note: Delete cannot be undone.
                        </c:when>

                        <c:otherwise>
                            No files selected.
                            <br/>
                        </c:otherwise>
                    </c:choose>

                    <br></br>

                    <button type="submit" class="btn btn-default" id="confirmMassDeleteButton">Confirm Delete</button>
                    <c:url var="cancelDeleteFile" value="/dataset/${dataset.shortName}/editfiles.html"/>
                    <a class="btn btn-default" href="<c:url value="${cancelDeleteFile}"/>">Cancel</a>
                </springForm:form>

            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
