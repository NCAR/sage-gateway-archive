<%@ include file="/WEB-INF/views/html/common/include.jsp"%>

<tiles:insertDefinition name="general-layout">

<tiles:putAttribute type="string" name="title" value="Contact Us" />
<tiles:putAttribute type="string" name="pageTitle" value="Contact Us" />

<tiles:putAttribute name="body">

	<p>
	For help or assistance, please contact ACADIS Community Support.
	</p>

    <p>
        Email:&nbsp;
        <a href="mailto:support@aoncadis.org?subject=Comment from ACADIS gateway.">support@aoncadis.org</a>
    </p>

    <p>
        Phone:&nbsp;
        720-443-1409
        <br>
        <span class="small">
            Monday - Friday, 10 am - 5 pm MT
        </span>
    </p>

</tiles:putAttribute>

</tiles:insertDefinition>
