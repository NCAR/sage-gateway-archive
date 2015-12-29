<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="How to Contribute"/>
    <tiles:putAttribute type="string" name="pageTitle" value="How to Contribute"/>

    <tiles:putAttribute name="body">

        In order to contribute data, you will need to:
        <ul>
            <li><a href="<c:url value="/ac/guest/secure/registration.html"/>">Register</a> to create a Workspace.
            </li>
            <li><a href="<c:url value="/ac/guest/secure/login.html"/>">Login</a> to your existing Workspace.</li>
        </ul>
        <p>
            Your Workspace contains the projects, datasets, and files you have contributed. You may create and edit
            datasets and upload files once you log in to your Workspace.
        </p>

        <h2>Who can contribute?</h2>
        <ul style="list-style: none;">
            <li>If you are an NSF PLR-ARC Investigator, please <a
                    href="<c:url value="/user/publish/contributeData.html"/>">contribute</a> your data and
                metadata.
            </li>
            <li>If you are another Arctic investigator who would like to contribute data not funded by ARC, please
                contact <a href="mailto:support@aoncadis.org">support@aoncadis.org</a></li>
        </ul>

        <h2>Templates</h2>

        <div>
            <ul class="tool_list">
                <c:url var="redirectURLplan" value="/redirect.html">
                    <c:param name="link" value="http://nsidc.org/acadis/docs/ACADIS_NSF_DMP_Template.docx"/>
                </c:url>
                <li><a href="<c:out value="${redirectURLplan}"/>">Data Management Plan Template (docx)</a></li>
                <div class="tool_list_text">ACADIS provides a template to assist investigators in developing the Data
                    Management Plan required for all NSF proposals.
                </div>
                <li><a href="<c:url value="/media/ACADIS_doc_template.docx"/>">Documentation Template (docx)</a></li>
                <div class="tool_list_text">ACADIS Dataset Documentation Template in outline format.</div>
                <li><a href="<c:url value="/about/cadis/netcdf-conversions.htm"/>">Data Conversion Tools</a></li>
                <div class="tool_list_text">ASCII to netCDF conversions guide and references.</div>
            </ul>
        </div>

        <h2>Guides</h2>

        <div>
            <ul class="tool_list">
                <c:url var="redirectURLsub" value="/redirect.html">
                    <c:param name="link" value="http://nsidc.org/acadis/docs/ACADIS_Gateway_data_submission_inst.pdf"/>
                </c:url>
                <li><a href="<c:out value="${redirectURLsub}"/>">Submission Instructions (pdf)</a></li>
                <div class="tool_list_text">Brief outline on how to submit data using the ACADIS Gateway.</div>
                <li><a href="<c:url value="/media/ProvidersGuide.pdf"/>">Data Provider's Guide (pdf)</a></li>
                <div class="tool_list_text">Detailed reference on how to contribute your data.</div>
                <c:url var="redirectURLsub" value="/redirect.html">
                    <c:param name="link" value="https://www.youtube.com/watch?v=G5pLIjjnK00"/>
                </c:url>
                <li><a href="<c:url value="${redirectURLsub}"/>">Rosetta Data Translation Tool Demo Video</a></li>
                <div class="tool_list_text">Demonstration video showing how to translate your data files into Climate
                    and Forecast (CF) compliant NetCDF files.
                </div>
            </ul>
        </div>
        <br>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
