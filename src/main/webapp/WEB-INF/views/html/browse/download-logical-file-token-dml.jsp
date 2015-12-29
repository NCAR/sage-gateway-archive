<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Download Selected Data"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Download Selected Data"/>

    <tiles:putAttribute name="body">
        <c:choose>
            <c:when test="${massStoreListSize == 0 && dmlListSize == 0 && wgetListSize == 0}">
                <div>
                    <span style="font-size: 18px; font-weight: bold;">You have not selected any files to download.  Please go back to the previous page and make a selection.</span></p></td>
                </div>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${proxyRequired == true}">
                        <h2>Obtain Security Credentials</h2>
                        For Wget script download, you will first need a MyProxy credential.
                        To get one choose one of the following options:
                        <ul style="margin-left: 2em; margin-top: 0.5em;margin-bottom: 1em;line-height: 1.5em;">
                            <li style="list-style-type: disc;"><a
                                    href="<c:url value="/account/user/myProxyLogon.html"/>">Launch MyProxyLogon</a> via
                                Java Web Start.
                            </li>
                            <li style="list-style-type: disc;">
                                <a href="<c:url value="/help/download-help.htm#MyProxyLogon"/>">Other options...</a>
                            </li>
                        </ul>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <p>
                            Please select a download method below.
                        </p>

                        <p>
                            <a href="<c:url value="/help/download-help.htm"/>">Learn more about data download...</a>
                        </p>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>

        </c:choose>
        <!-- (token-dml) -->

        <!-- begin WGet section -->

        <c:if test="${wgetListSize > 0}">
            <div style="clear: both; padding-top: 25px;"></div>
            <div id="wget_download">
                <div id="wget_download_icon" style="float: left;">
                    <a href="javascript:submitWGet();"><img src='<c:url value="/images/bulk_download_blu.gif" />'/></a>
                </div>
                <div id="wget_download_text" style="float: left; padding-left: 25px; width: 400px;">
                    <div>
                        <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitWGet();">Wget Script
                            Download</a></span>
                    </div>
                    <div>
                        <span>Unix, Linux, and Mac OSX only</span>
                    </div>
                    <div style="padding-top: 10px;">
                        <p>
                            Download a Wget shell script that you can run from the command line on Unix, Linux, or Mac
                            OSX.
                        </p>
                    </div>
                    <c:if test="${proxyRequired == true}">
                        <div style="padding-top: 10px;">
                            <p>
                                <a href="<c:url value="/account/user/myProxyLogon.htm"/>">Launch MyProxyLogon</a> via
                                Java Web Start.
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
                <div id="dml_download_icon" style="float: left;">
                    <a href="javascript:submitDML();"><img src='<c:url value="/images/bulk_download_blu.gif" />'/></a>
                </div>
                <div id="dml_download_text" style="float: left; padding-left: 25px; width: 400px;">
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
                            <a href="http://sdm.lbl.gov/dml/">Learn more about DataMover-Lite</a>
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
                <div id="mass_store_transfer_icon" style="float: left;">
                    <a href="javascript:submitMassStore();"><img src='<c:url value="/images/bulk_transfer_blu.gif" />'/></a>
                </div>
                <div id="mass_store_transfer_text" style="float: left; padding-left: 25px; width: 400px;">
                    <div>
                        <span style="font-size: 18px; font-weight: bold;"><a href="javascript:submitMassStore();">Transfer
                            Files to Workspace</a></span>
                    </div>
                    <div style="padding-top: 10px;">
                        <p>
                            Some or all of the files you requested for download are stored on our archival storage unit.
                        </p>

                        <p style="padding-top: 10px;">
                            It takes time to retrieve this data and you will be notified via email when all your files
                            are ready for download.
                        </p>

                        <p style="padding-top: 10px;">
                            The transfered files will then be accessible from your 'Workspace'.
                        </p>
                    </div>
                </div>
                <div id="mass_store_transfer_size" style="float: left; padding-left: 25px;">
                    <browse:file-list-size fileCount="${massStoreListSize}" size="${massStoreDisplaySize}"
                                           unit="${massStoreDisplayUnit}"/>
                </div>
            </div>
        </c:if>

        <c:if test="${massStoreListSize == 0 && dmlListSize == 0 && wgetListSize == 0}">
            <div>
                <span style="font-size: 18px; font-weight: bold;">You have not selected any files to download.  Please go back to the previous page and make a selection.</span></p></td>
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
