<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Basic File Upload"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Basic File Upload"/>

    <tiles:putAttribute name="style">
        .descriptive_row_table .descriptive_row_name {
        width: 100px;
        }
    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <common:file-upload-errors commandName="command"/>

        <div style="padding-bottom: 30px">
            <c:url var="datasetLink" value="/dataset/${command.datasetIdentifier}.html"></c:url>
            <b><a href="${datasetLink}">${command.datasetTitle}</a></b><br>
        </div>

        <springForm:form method="post" commandName="command" enctype="multipart/form-data">
            <browse:input-file-upload fieldPath="files[0]" fieldName="File 1"/>
            <browse:input-file-upload fieldPath="files[1]" fieldName="File 2"/>
            <browse:input-file-upload fieldPath="files[2]" fieldName="File 3"/>
            <browse:input-file-upload fieldPath="files[3]" fieldName="File 4"/>
            <browse:input-file-upload fieldPath="files[4]" fieldName="File 5"/>

            <div style="padding-top: 10px;">
                <button type="submit" class="btn btn-default" id="uploadFiles">Upload Files</button>
            </div>
        </springForm:form>

        <div>
            <br>
            <c:url var="multiUploadFilesUrl" value="/dataset/${command.datasetIdentifier}/bulkFileUploadForm.html"/>
            <a href="${multiUploadFilesUrl}">Bulk File Upload</a>
            <br>
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>