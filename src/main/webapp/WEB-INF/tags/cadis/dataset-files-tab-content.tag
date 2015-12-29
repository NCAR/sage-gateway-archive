<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="dataset" required="true" type="sgf.gateway.model.metadata.Dataset" %>

<c:if test="${dataset.isDownloadable() && not dataset.retracted}">
    <div>
        <c:if test="${dataset.descriptiveMetadata.distributionURI != null}">
            <c:url var="distributionRedirectURI" value="/redirect.html">
                <c:param name="link" value="${dataset.descriptiveMetadata.distributionURI}"/>
            </c:url>
            <a href="${distributionRedirectURI}">${dataset.descriptiveMetadata.distributionURIText}</a>
        </c:if>

        <c:if test="${dataset.logicalFileCount > 0}">
            <p>
                <c:url value="/dataset/${dataset.shortName}/file.html" var="datasetFiles"/>
                <a href="${datasetFiles}">Download Files</a> - View and download individual files for this
                Dataset. ${dataset.logicalFileCount} Files
            </p>

            <p>
                <c:url value="/dataset/${dataset.shortName}/file.zip" var="datasetZip"/>
                <a href="${datasetZip}">Download Zip Archive</a> - Download all the files from this dataset in a Zip
                archive. ${dataset.logicalFileCount} Files
            </p>

            <p>
                <c:url value="/dataset/${dataset.shortName}/file.wget" var="datasetWget"/>
                <c:url var="wgetRedirectURL" value="/redirect.html">
                    <c:param name="link" value="http://www.gnu.org/software/wget/"/>
                </c:url>
                <a href="${datasetWget}">Download Wget Script</a> - A <a href="${wgetRedirectURL}">Wget</a> shell
                script that will download all files for this dataset. ${dataset.logicalFileCount} Files
            </p>
        </c:if>
    </div>
</c:if>
