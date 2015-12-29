<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${project.name}"/>
    <tiles:putAttribute type="string" name="pageTitle"
                        value="Simulation of the Transient Climate of the Last 21,000 Years (TraCE-21ka)"/>

    <tiles:putAttribute name="body">

        <div class="row">
            <div class="col-xs-3">
                <img src='<c:url value="/images/ice5G-LGM.png"/>' align="left" valign="top" width="220px"
                     alt="traCE domain" title="traCE domain" style="padding-right: 12px;"/>
            </div> <!-- .col-xs-3 -->

            <div class="col-md-6">
                <p>
                    This TraCE-21ka dataset contains output from the full TraCE simulation from 22,000 years
                    before present
                    (22ka) to 1990 CE as well as single-forcing sensitivity simulations of varying lengths.
                    These results are
                    from a fully-coupled, non-accelerated atmosphere-ocean-sea ice-land surface CCSM3 simulation
                    at the
                    T31_gx3 resolution. For the full TraCE simulation, the model is forced with transient
                    greenhouse gas
                    concentrations and orbitally-driven insolation changes. Transient boundary conditions
                    include the ICE-5G
                    ice sheets - extent and topography, and changing paleogeography as sea level rises from its
                    Last Glacial
                    Maximum low stand to modern levels. We also prescribe a transient scenario of meltwater
                    forcing to the
                    oceans from the retreating ice sheets. Vegetation is prognostic.
                </p>

                <p>
                    The following CCSM3 simulations are available.
                </p>

                <p>
                    [TraCE] full TraCE simulation, with transient forcing changes in greenhouse gases,
                    orbitally-driven insolation variations, ice sheets and meltwater fluxes.
                </p>

                <p>
                    [TraCE-ORB] with only transient orbital forcing, all other forcings and boundary conditions
                    remain at the full TraCE state of 22ka.
                </p>

                <p>
                    [TraCE-GHG] with only transient greenhouse gas forcing, all other forcings and boundary
                    conditions remain at the full TraCE state of 22ka.
                </p>

                <p>
                    [TraCE-ICE] with only changing continental ice sheets, all other forcings and boundary
                    conditions remain at the full TraCE state of 19ka.
                </p>

                <p>
                    [TraCE-MWF] with only transient Northern Hemisphere meltwater fluxes, all other forcings and
                    boundary conditions remain at the full TraCE state of 19ka.
                </p>

                <p>
                    [TraCE-ORB-17ka] with only transient orbital forcing, all other forcings and boundary
                    conditions remain at the full TraCE state of 17ka.
                </p>

                <p>
                    [TraCE-GHG-17ka] with only transient greenhouse gas forcing, all other forcings and boundary
                    conditions remain at the full TraCE state of 17ka.
                </p>


                <p>
                    More details of the boundary conditions can be found on the TraCE homepage.
                </p>

                <p>
                    <a href="<c:url value="http://www.cgd.ucar.edu/ccr/TraCE/"/>">TraCE-21ka Homepage</a>
                </p>

                <p>
                    Specific details on ice sheets, timing and location of meltwater forcing, and opening of
                    ocean gateways can be found here:
                </p>
                <a href="<c:url value="http://www.cgd.ucar.edu/ccr/TraCE/TraCE.22,000.to.1950.overview.v2.htm"/>">TraCE-21ka
                    Detailed Overview</a>


                <h3>Publication acknowledgement:</h3>

                TraCE-21ka was made possible by the DOE INCITE computing program, and supported by NCAR, the NSF
                P2C2 program,
                and the DOE Abrupt Change and EaSM programs.

                <h3>Download ${project.name} Data</h3>

                <p>
                    <a href="/dataset/ucar.cgd.ccsm3.trace.html">Main TraCE dataset</a>
                </p>

                <p>
                    <a href="/dataset/ucar.cgd.ccsm.trace_orbital.html">TraCE-ORB</a>
                </p>

                <p>
                    <a href="/dataset/ucar.cgd.ccsm.trace_ghg.html">TraCE-GHG</a>
                </p>

                <p>
                    <a href="/dataset/ucar.cgd.ccsm.trace_landice.html">TraCE-ICE</a>
                </p>

                <p>
                    <a href="/dataset/ucar.cgd.ccsm.trace_meltwater.html">TraCE-MWF</a>
                </p>

                <p>
                    <a href="/dataset/ucar.cgd.ccsm.trace_orbital17ka.html">TraCE-ORB-17ka</a>
                </p>

                <p>
                    <a href="/dataset/ucar.cgd.ccsm.trace_ghg17ka.html">TraCE-GHG-17ka</a>
                </p>
            </div> <!-- .col-md-6 -->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
