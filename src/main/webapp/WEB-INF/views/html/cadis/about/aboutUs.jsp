<%@ include file="/WEB-INF/views/html/common/include.jsp"%>


<tiles:insertDefinition name="general-layout">
    <!-- title for the browser tab -->
	<tiles:putAttribute type="string" name="title" value="About Us" />
    <!-- title for the page -->
    <tiles:putAttribute type="string" name="pageTitle" value="About Us" />

<tiles:putAttribute name="body">

	<p>
		The Advanced Cooperative Arctic Data and Information Service (ACADIS)
		is a joint effort by the National Snow and Ice Data Center (NSIDC),
		the University Corporation for Atmospheric Research (UCAR), UNIDATA,
		and the National Center for Atmospheric Research (NCAR) to provide data archival,
		preservation and access for all projects funded by NSF's Arctic Science Program (ARC).
		ACADIS builds on the CADIS project that supported the Arctic Observing Network (AON). 
		This portal will continue to be a gateway for AON data and is being expanded to include all NSF ARC data.
	</p>

	<h3>ACADIS PIs and Co-PIs</h3>
	<p>
		<ul>
			<li>Jim Moore, NCAR EOL</li>
			<li>Don Middleton, NCAR CISL</li>
			<li>Mohan Ramamurthy, UCAR Unidata</li>
			<li>Mark Serreze, CU CIRES NSIDC</li>
			<li>Lynn Yarmey, CU CIRES NSIDC</li>
		</ul>
	</p>

	<h3>Related Resources</h3>
	<p>
		<ul>
			<li><c:url var="adeLink" value="/redirect.html" >
				<c:param name="link" value="http://nsidc.org/acadis/search/" />
			</c:url> 
			<a href="${adeLink}">Arctic Data Explorer</a> - Search multiple repositories for Arctic research data.</li>
		</ul>
	</p>
	<h3>Photo Credits</h3>
	<p>
		<ul>
			<li>Caribou Banner Image, original image courtesy of Andrew Slater, National Snow and Ice Data Center.</li>
		</ul>
	</p>
</tiles:putAttribute>

</tiles:insertDefinition>