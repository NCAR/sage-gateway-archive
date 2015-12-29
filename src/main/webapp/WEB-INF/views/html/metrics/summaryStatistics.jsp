<%@ include file="/WEB-INF/views/html/common/include.jsp"%>

<tiles:insertDefinition name="general-layout">

	<tiles:putAttribute name="title" value="ACADIS Summary Statistics" />
	<tiles:putAttribute name="pageTitle" value="ACADIS Summary Statistics" />

	<tiles:putAttribute name="body">

		<div>
			<p>This is a summary of the ACADIS data archive.  Displayed are the number of datasets and the number of files for those datasets.  Also included is a list of most common data formats in the data archive.</p>

			<p>Questions? contact <a href="mailto:support@aoncadis.org">support@aoncadis.org</a> or <a href="mailto:jmoore@ucar.edu">jmoore@ucar.edu</a></p>
			<p>Total Datasets: <fmt:formatNumber type="number" value="${statistics.totalDatasets}"/></p>
			<p>Total Files: <fmt:formatNumber type="number" maxFractionDigits="3" value="${statistics.totalFiles}" /></p>
			<p>Total File Size: <gateway:FileSizeUnitFormat fileSize="${statistics.totalBytes}" precision="2" /></p>
			<p>Most Common File Formats:
				<ul>
					<li>JPEG</li>
					<li>GIF</li>
					<li>PNG</li>
					<li>Audio/WAV</li>
					<li>NetCDF</li>
					<li>HDF</li>
					<li>ASCII</li>
					<li>CSV</li>
					<li>XLS</li>
					<li>XLSX</li>
					<li>TAR</li>
					<li>BMP</li>
					<li>BIN</li>
					<li>ZIP</li>
					<li>PKZIP</li>
					<li>PDF</li>
					<li>CDF</li>
					<li>HEX</li>
					<li>MAT</li>
				</ul>
			</p>
		</div>

	</tiles:putAttribute>

</tiles:insertDefinition>