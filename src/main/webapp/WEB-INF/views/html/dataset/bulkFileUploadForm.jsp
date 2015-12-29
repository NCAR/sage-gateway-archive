<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Bulk File Upload"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Bulk File Upload"/>

    <tiles:putAttribute name="style">
        .descriptive_row_table .descriptive_row_name {
        width: 0px;
        }
    </tiles:putAttribute>

    <tiles:putAttribute name="body">
        <common:file-upload-errors commandName="command"/>

        <div style="padding-bottom: 30px">
            <c:url var="datasetLink" value="/dataset/${dataset.shortName}.html"></c:url>
            <b><a href="${datasetLink}">${dataset.title}</a></b>
        </div>

        <div class="row">
            <div class="col-md-8">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p>
                            <b>NOTE:</b> Bulk file upload only works with HTML5-enabled browsers.
                        </p>

                        <p>
                            The following browsers should work (but, your experience may vary based on your particular system):
                            Internet Explorer 10.0+, Safari 6.0+, Firefox 3.6+, Chrome 3.0+, Opera 4.0+.
                        </p>

                        <p>
                            <c:url var="basicFileUpload" value="/dataset/${dataset.shortName}/basicFileUploadForm.html"/>
                            If needed, our <a href="${basicFileUpload}">Basic File Upload</a> is still available for use.
                        <p>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

        <springForm:form method="post" commandName="command" enctype="multipart/form-data">
            <browse:input-file-upload id="fileinput" fieldPath="files" fieldName=""
                                      fieldExample="Select multiple files using CTRL-click or Shift-click"
                                      multiple="true"/>
            <br>
            <button type="submit" class="btn btn-default" id="uploadFiles">Upload Files</button>
        </springForm:form>

        <div id="filesList" style="padding-top: 30px;">
        </div>

        <script type="text/javascript">
            function init() {

                // After selecting files, show list of files in a div
                $("#fileinput").change(function () {

                    _displaySelectedFilesTable();
                });

                _displaySelectedFilesTable();

            } // init()

            function _displaySelectedFilesTable() {

                var file_size_table_html = "";
                var files = $('#fileinput').prop("files");  //FileList object

                if (files.length != 0) {
                    file_size_table_html +=
                        '<table id="filesTable" class="table table-condensed table-bordered table-hover table-striped">' +
                            '<thead>' +
                                '<tr>' +
                                    '<th>Files to Upload</th>' +
                                    '<th>Size</th>' +
                                '</tr>' +
                            '</thead>' +
                            '<tbody>';

                    $.each(files, function (key, value) {

                        // alert("Name: " + value.name + " Size: " + value.size);
                        var formatedSize = _formatFileSize(value.size);
                        file_size_table_html +=
                                '<tr>' +
                                    '<td>' + value.name + '</td>' +
                                    '<td>' + formatedSize + '</td>' +
                                '</tr>';
                    });

                    file_size_table_html +=
                            '<tbody>' +
                        '</table>';
                }

                // stick built html into div
                $("#filesList").html(file_size_table_html);
            }

            function _formatFileSize(bytes) {
                if (typeof bytes !== 'number') {
                    return '';
                }

                if (bytes >= 1099511627776) {
                    return (bytes / 1099511627776).toFixed(2) + ' TB';
                } else if (bytes >= 1073741824) {
                    return (bytes / 1073741824).toFixed(2) + ' GB';
                } else if (bytes >= 1048576) {
                    return (bytes / 1048576).toFixed(2) + ' MB';
                } else {
                    return (bytes / 1024).toFixed(2) + ' KB';
                }
            }

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
