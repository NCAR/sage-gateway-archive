<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${datasetMap.project.name}"/>
    <tiles:putAttribute type="string" name="pageTitle"
                        value="North American Regional Climate Change Assessment Program (NARCCAP)"/>

    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-xs-3">
                <img src='<c:url value="/images/narccap-domain.png"/>' align="left" valign="top" width="240px"
                     alt="NARCCAP domain" title="NARCCAP domain" style="padding-right: 12px;"/>
            </div> <!-- .col-xs-3 -->

            <div class="col-md-6">
                <p>
                    The NARCCAP dataset contains high-resolution climate change scenario simulation
                    output from multiple RCMs (regional climate models) nested within multiple
                    AOGCMs (atmosphere-ocean general circulation models) for 30-year current and
                    future periods.
                </p>

                <p>
                    The RCMs are run at 50-km spatial resolution over a domain
                    convering the conterminous United States and most of Canada; results are
                    recorded at 3-hourly intervals. The driving AOGCMs are forced with the A2 SRES
                    emissions scenario in the future period. This dataset also include output from
                    two timeslice experiments and a set of 25-year RCM simulations driven with
                    NCEP-2 reanalysis data. These simulation results are useful for impacts
                    analysis, further downscaling experiments, and analysis of model performancy
                    and uncertainty in regional scale projections of future climate.
                </p>

                <p>
                    When publishing research based on NARCCAP data, please include a
                    citation for the dataset itself, such as the following:
                </p>
                <blockquote>Mearns, L.O., et al., 2007, updated 2014. <i>The North
                    American Regional Climate Change Assessment Program dataset</i>,
                    National Center for Atmospheric Research Earth System Grid data
                    portal, Boulder, CO. Data downloaded
                        ${currentDate}. [<a href="http://dx.doi.org/10.5065/D6RN35ST">doi:10.5065/D6RN35ST</a>]
                </blockquote>
                </p>
                <p>
                    <c:url var="narccapUrl" value="/redirect.html">
                        <c:param name="link" value="http://www.narccap.ucar.edu/"/>
                    </c:url>
                    <a href="<c:url value="${narccapUrl}" />">NARCCAP Homepage</a>
                    <br/>
                    <c:url var="modelUrl" value="/redirect.html">
                        <c:param name="link" value="http://www.narccap.ucar.edu/data/model-info.html"/>
                    </c:url>
                    <a href="<c:url value="${modelUrl}" />">Model Information</a>
                </p>

                <p>
                    <c:url var="registerUrl" value="/redirect.html">
                        <c:param name="link" value="http://www.narccap.ucar.edu/cgi/register.pl"/>
                    </c:url>
                    This dataset is open access. Registration is not required, but we encourage NARCCAP data
                    users to share their research interests at the <a href="${registerUrl}">NARCCAP User
                    Directory</a>.
                </p>

                <div style="padding-top: 20px;">
                    <narccapProject:catalog-table/>
                </div>

                <div style="padding: 5px 0 0 5px;">
                    <b><a href="<c:url value="/dataset/id/${datasetMap.hadcm3BoundaryConditions.identifier}.html" />">Download
                        HadCM3 Boundary Condition Data</a></b>
                </div>
            </div> <!-- .col-md-6 -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>

