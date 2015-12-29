<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Download Selected Files"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Download Selected Files"/>

    <tiles:putAttribute type="string" name="pageTitle" value="Download Selected Data"/>

    <tiles:putAttribute name="body">

        <c:if test="${massStoreListSize == 0 && dmlListSize == 0 && wgetListSize == 0}">
            <div>
                <span style="font-size: 18px; font-weight: bold;">You have not selected any files to download.  Please go back to the previous page and make a selection.</span></p></td>
            </div>
        </c:if>

        <!-- begin WGet section -->
        <c:if test="${wgetListSize > 0}">
            <div style="clear: both; padding-top: 25px;"></div>
            <div id="wget_download">
                <div id="wget_download_text" style="float: left; width: 400px;">
                    <div>
                        <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitWGet();">Wget Script
                            Download</a></span>
                    </div>
                    <div>
                        <span>Unix, Linux, and Mac OSX only</span>
                    </div>
                    <div style="padding-top: 10px;">
                        <p>
                            Download a Wget shell script that you can run from the command line.
                        </p>
                    </div>
                    <c:if test="${true}">
                        <div style="padding-top: 10px;">
                            <p>
                                For Wget script download, Wget version 1.12 (or later) is required, and Oracle's version
                                of Java 1.7 (or later) is required.
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
                    <browse:file-list-size fileCount="${wgetListSize}" size="${wgetDisplaySize}"
                                           unit="${wgetDisplayUnit}"/>
                </div>
            </div>
        </c:if>


        <!-- begin DML section -->
        <c:if test="${dmlListSize > 0}">
            <div style="clear: both; padding-top: 25px;"></div>
            <div id="dml_download">
                <div id="dml_download_text" style="float: left; width: 400px;">
                    <div>
                        <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitDML();">DML Script
                            Download</a></span>
                    </div>
                    <div>
                        <span style="font-style: italic;">Java enabled systems only</span>
                    </div>
                    <div style="padding-top: 10px;">
                        <p>
                            Download a script that you can run with the DataMover-Lite (DML) file transfer tool on Java
                            enabled systems.
                        </p>
                    </div>
                    <div style="padding-top: 10px;">
                        <p>
                            <a href="http://datagrid.lbl.gov/dml4/jnlp/dml.html">Launch DataMover-Lite</a> via Java Web
                            Start.
                        </p>
                    </div>
                    <div style="padding-top: 10px;">
                        <p>
                            <a href="http://sdm.lbl.gov/dml/">Learn more about DataMover-Lite...</a>
                        </p>
                    </div>
                </div>
                <div id="dml_download_size" style="float: left; padding-left: 25px;">
                    <browse:file-list-size fileCount="${dmlListSize}" size="${dmlDisplaySize}"
                                           unit="${dmlDisplayUnit}"/>
                </div>
            </div>
        </c:if>


        <!-- begin data transfer section -->

        <c:if test="${massStoreListSize > 0}">
            <div style="clear: both; padding-top: 25px;"></div>
            <div id="mass_store_transfer">
                <div id="mass_store_transfer_text" style="float: left; width: 400px;">
                    <div>
                        <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitMassStore();">Request
                            File Transfer from Archive</a></span>
                    </div>
                    <div style="padding-top: 10px;">
                        <p>
                            Click the link above to request transfer of archived files. These files are not immediately
                            available for download.
                        </p>

                        <p style="padding-top: 10px;">
                            It takes time to retrieve these files from the archive and you will be notified via email
                            when they are ready.
                        </p>

                        <p style="padding-top: 10px;">

                            <c:url var="summaryRequestUrl" value="/workspace/user/summaryRequest.htm"/>
                            Once all the files are transferred, you may download them by visiting your <a
                                href="${summaryRequestUrl}">Data Transfer Requests</a>.
                        </p>
                    </div>
                </div>
                <div id="mass_store_transfer_size" style="float: left; padding-left: 25px;">
                    <browse:file-list-size fileCount="${massStoreListSize}" size="${massStoreDisplaySize}"
                                           unit="${massStoreDisplayUnit}"/>
                </div>
            </div>
        </c:if>


        <form action="../download/generateWGetScript.wget" method="post" name="WGetForm">
            <c:forEach var="logicalFile" items="${wgetList}">
                <input type="hidden" name="logicalFileId" value="${logicalFile.identifier}"/>
            </c:forEach>
        </form>

        <form action="../download/dataTransfer.htm" method="post" name="MassStoreForm">
            <c:forEach var="logicalFile" items="${massStoreList}">
                <input type="hidden" name="logicalFileId" value="${logicalFile.identifier}"/>
            </c:forEach>
        </form>

        <form action="../download/generateDMLScript.dml" method="post" name="DMLForm">
            <c:forEach var="logicalFile" items="${dmlList}">
                <input type="hidden" name="logicalFileId" value="${logicalFile.identifier}"/>
            </c:forEach>
        </form>

        <script>
            function submitWGet() {
                document.WGetForm.submit();
            }
            function submitDML() {
                document.DMLForm.submit();
            }
            function submitMassStore() {
                document.MassStoreForm.submit();
            }
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
