<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Download Datasets"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Download Datasets"/>

    <tiles:putAttribute name="style">

        .unauthorizedDatasetList {
        border: 2px solid red;
        padding: 0 20px 10px 20px;
        margin: 0 0 20px 0;
        }

        table.unaccessableDatasets td {
        padding: 0 10px 0 0;
        text-align: left;
        }

        .datasetList {
        display: inline;
        float: left;
        }

        .variableList {
        display: inline;
        float: left;
        margin: 0 0 0 20px;
        }

        .moreLink {
        color: blue;
        cursor: pointer;
        margin: 0 0 0 5px;
        text-decoration: underline;
        }

    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="fileFilterDefinition"/>

        <c:if test="${!empty unauthorizedDatasets}">
            <div class="unauthorizedDatasetList">
                <h3>Authorization Required</h3>

                <p>
                    The following is a list of Datasets from which you requested to download Files, but you do not have
                    permission to access.
                </p>

                <p>
                    If there are Datasets below to which you have access and which have Files available to download, you
                    may proceed to download the Datasets as usual.
                </p>

                <display:table name="unauthorizedDatasets" uid="dataset" class="unaccessableDatasets">
                    <display:column sortable="false">
                        <a href="<c:url value="/dataset/${dataset.shortName}.html" />">${dataset.title}</a>
                    </display:column>
                    <display:column sortable="false">
                        <c:url var="accessURL" value="/ac/accessDenied.html">
                            <c:param name="operation">READ</c:param>
                            <c:param name="datasetId">${dataset.identifier}</c:param>
                        </c:url>
                        <a href="${accessURL}">Request Access</a>
                    </display:column>
                </display:table>
            </div>
        </c:if>


        <c:choose>
            <c:when test="${httpFileTransferModel.fileCount == 0 && gridftpFileTransferModel.FileCount == 0 && srmFileTransferModel.fileCount == 0}">
                <div class="noFilesAvailable">
                    <p>
                        There are no Files available to download for the Datasets chosen.
                    </p>
                </div>
            </c:when>

            <c:otherwise>

                <div class="stats">

                </div>

                <div class="datasetList">
                    <h4>Selected Datasets</h4>

                    <ul>
                        <c:forEach var="dataset" items="${datasets}" end="4">
                            <li><a href="<c:url value="/dataset/${dataset.shortName}.html" />">${dataset.shortName}</a>
                            </li>
                        </c:forEach>
                    </ul>
                    <c:if test="${fn:length(datasets) > 5}">
                        <div class="more">
                            <ul>
                                <c:forEach var="dataset" items="${datasets}" begin="5">
                                    <li>
                                        <a href="<c:url value="/dataset/${dataset.shortName}.html" />">${dataset.shortName}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <span class="moreLink">...Show More</span>
                    </c:if>
                </div>

                <c:if test="${!empty selectedVariableNames}">
                    <div class="variableList">
                        <h4>Selected Variables</h4>
                        <ul>
                            <c:forEach items="${selectedVariableNames}" var="variable" end="4">
                                <li>${variable}</li>
                            </c:forEach>
                        </ul>
                        <c:if test="${fn:length(selectedVariableNames) > 5}">
                            <div class="more">
                                <ul>
                                    <c:forEach items="${selectedVariableNames}" var="variable" begin="5">
                                        <li>${variable}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <span class="moreLink">...Show More</span>
                        </c:if>
                    </div>
                </c:if>


                <c:if test="${httpFileTransferModel.fileCount > 0}">

                    <div id="wget_download" style="clear: both; padding-top: 25px;">

                        <c:if test="${totalFileCount > httpFileTransferModel.fileCount}">
                            <div style="border: 1px solid red; padding: 10px 20px 10px 20px; margin: 0 0 10px 0;">
                                Only ${httpFileTransferModel.fileCount} of ${totalFileCount} total files selected are
                                available for download via Wget.
                            </div>
                        </c:if>

                        <div id="wget_download_text" style="float: left; width: 400px;">
                            <div>
                                <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitWget();">Download
                                    Wget Script</a></span>
                            </div>
                            <div>
                                <span>Unix, Linux, and Mac OSX only</span>
                            </div>
                            <div style="padding-top: 10px;">
                                <p>
                                    Download a Wget shell script that you can run from the command line on Unix, Linux,
                                    or Mac OSX.
                                </p>
                            </div>

                            <c:if test="${certificateRequired == true}">
                                <div style="padding-top: 10px;">
                                    <p>
                                        For Wget script download, Wget version 1.12 (or later) is required, and Oracle's
                                        version of Java 1.7 (or later) is required.
                                    </p>
                                </div>
                            </c:if>

                            <div style="padding-top: 10px;">
                                <p>
                                    <a href="http://www.gnu.org/software/wget/">Learn more about Wget...</a>
                                </p>
                            </div>
                        </div>
                        <div id="wget_download_size" style="float: left; padding-left: 25px;">
                            <browse:file-list-size fileCount="${httpFileTransferModel.fileCount}"
                                                   size="${httpFileTransferModel.fileSize}"
                                                   unit="${httpFileTransferModel.fileSizeUnit}"/>
                        </div>
                    </div>
                </c:if>

                <c:if test="${dmlFileTransferModel.fileCount > 0}">
                    <div id="dml_download" style="clear: both; padding-top: 25px;">

                        <c:if test="${totalFileCount > dmlFileTransferModel.fileCount}">
                            <div style="border: 1px solid red; padding: 10px 20px 10px 20px; margin: 0 0 10px 0;">
                                Only ${dmlFileTransferModel.fileCount} of ${totalFileCount} total files selected are
                                available for download via DML.
                            </div>
                        </c:if>


                        <div id="dml_download_text" style="float: left; width: 400px;">
                            <div>
                                <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitDML();">Download
                                    DML Script</a></span>
                            </div>
                            <div>
                                <span style="font-style: italic;">Java enabled systems only</span>
                            </div>
                            <div style="padding-top: 10px;">
                                <p>
                                    Download a script that you can run with the DataMover-Lite (DML) file transfer tool
                                    on Java enabled systems.
                                </p>
                            </div>
                            <div style="padding-top: 10px;">
                                <p>
                                    <a href="http://datagrid.lbl.gov/dml4/jnlp/dml.html">Launch DataMover-Lite...</a>
                                    via Java Web Start.
                                </p>
                            </div>
                            <div style="padding-top: 10px;">
                                <p>
                                    <a href="http://sdm.lbl.gov/dml/">Learn more about DataMover-Lite...</a>
                                </p>
                            </div>
                        </div>
                        <div id="dml_download_size" style="float: left; padding-left: 25px;">
                            <browse:file-list-size fileCount="${dmlFileTransferModel.fileCount}"
                                                   size="${dmlFileTransferModel.fileSize}"
                                                   unit="${dmlFileTransferModel.fileSizeUnit}"/>
                        </div>
                    </div>
                </c:if>

                <c:if test="${srmFileTransferModel.fileCount > 0}">
                    <div id="mass_store_transfer" style="clear: both; padding-top: 25px;">

                        <c:if test="${totalFileCount > srmFileTransferModel.fileCount}">
                            <div style="border: 1px solid red; padding: 10px 20px 10px 20px; margin: 0 0 10px 0;">
                                Only ${srmFileTransferModel.fileCount} of ${totalFileCount} total files selected are
                                available from our archival storage unit.
                            </div>
                        </c:if>


                        <div id="mass_store_transfer_text" style="float: left; width: 400px;">
                            <div>
                                <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitSRM();">Request
                                    Files from Archival Storage</a></span>
                            </div>
                            <div style="padding-top: 10px;">
                                <p>
                                    Some or all of the files you requested for download are stored on our archival
                                    storage unit.
                                </p>

                                <p style="padding-top: 10px;">
                                    Once you submit your transfer request, it takes (some) time to retrieve this data.
                                    You will be notified via email when all your files are ready for download.
                                </p>

                                <p style="padding-top: 10px;">
                                    The transfered files will then be accessible from your <a
                                        href="<c:url value="/workspace/user/summaryRequest.htm" />">Workspace</a>.
                                </p>
                            </div>
                        </div>
                        <div id="mass_store_transfer_size" style="float: left; padding-left: 25px;">
                            <browse:file-list-size fileCount="${srmFileTransferModel.fileCount}"
                                                   size="${srmFileTransferModel.fileSize}"
                                                   unit="${srmFileTransferModel.fileSizeUnit}"/>
                        </div>
                    </div>
                </c:if>


                <form action="../download/script.wget" method="post" name="wgetForm">
                    <c:forEach var="dataset" items="${datasets}">
                        <input type="hidden" name="datasetId" value="${dataset.shortName}"/>
                    </c:forEach>

                    <c:forEach var="variableName" items="${variableNames}">
                        <input type="hidden" name="variableName" value="${variableName}"/>
                    </c:forEach>

                    <c:forEach var="standardVariableName" items="${standardVariableNames}">
                        <input type="hidden" name="standardVariableName" value="${standardVariableName}"/>
                    </c:forEach>
                </form>

                <form action="../download/script.dml" method="post" name="dmlForm">
                    <c:forEach var="dataset" items="${datasets}">
                        <input type="hidden" name="datasetId" value="${dataset.shortName}"/>
                    </c:forEach>

                    <c:forEach var="variableName" items="${variableNames}">
                        <input type="hidden" name="variableName" value="${variableName}"/>
                    </c:forEach>

                    <c:forEach var="standardVariableName" items="${standardVariableNames}">
                        <input type="hidden" name="standardVariableName" value="${standardVariableName}"/>
                    </c:forEach>
                </form>

                <form action="../download/dataTransfer.htm" method="post" name="srmForm">
                    <c:forEach var="dataset" items="${datasets}">
                        <input type="hidden" name="datasetId" value="${dataset.shortName}"/>
                    </c:forEach>

                    <c:forEach var="variableName" items="${variableNames}">
                        <input type="hidden" name="variableName" value="${variableName}"/>
                    </c:forEach>

                    <c:forEach var="standardVariableName" items="${standardVariableNames}">
                        <input type="hidden" name="standardVariableName" value="${standardVariableName}"/>
                    </c:forEach>
                </form>

            </c:otherwise>
        </c:choose>

        <script type="text/javascript">
            function init() {
                <%-- Start with all detail section divs hidden --%>
                $('div.more').hide();

                <%-- click handler for link shows/hides Constraint list --%>
                <%-- Initial text must match the link text --%>
                $('.moreLink').click(function () {
                    $(this).parent().find('div.more').toggle();
                    $(this).text($(this).text() == '...Show More' ? '...Show Fewer' : '...Show More');
                });
            } // init()

            function submitWget() {
                document.wgetForm.submit();
            }
            function submitDML() {
                document.dmlForm.submit();
            }
            function submitSRM() {
                document.srmForm.submit();
            }

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
