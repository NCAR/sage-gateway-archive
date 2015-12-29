<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="ESG-NCAR"/>
    <tiles:putAttribute type="string" name="pageTitle"
                        value="Climate Data at the National Center for Atmospheric Research"/>

    <tiles:putAttribute name="style">
        .main-link {
        text-align: left;
        font-size:12px;
        font-weight: bold;
        }
    </tiles:putAttribute>

    <tiles:putAttribute name="body-header">
        <div>
            <common:success-message message="${successMessage}"/>
            <common:info-message message="${infoMessage}"/>
        </div>
    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <h4>
            Find and download climate data and analysis tools
        </h4>
        <br>

        <div class="panel panel-default">
            <div class="panel-heading">Popular Global Climate Models</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2">
                        <a class="main-link" href='<c:url value="/project/ccsm.html"/>'>
                            <img src='<c:url value="/images/cesm.jpg"/>' width="140px"/>
                        </a>
                    </div>

                    <div class="col-md-8">
                        <a class="main-link"
                           href='<c:url value="/dataset/ucar.cgd.ccsm4.output.html"/>'>Community
                            Earth System Model (CESM/CCSM4)</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/ucar.cgd.ccsm4.CESM_CAM5_BGC_LE.html"/>'>CESM1
                            CAM5 BGC 20C + RCP8.5 Large Ensemble</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/ucar.cgd.ccsm4.CESM_CAM5_BGC_ME.html"/>'>CESM1
                            CAM5 BGC RCP4.5 Medium Ensemble</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/ucar.cgd.ccsm4.CESM_CAM5_LME.html"/>'>CESM1
                            Last Millennium Ensemble</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/ucar.cgd.asd.output.html"/>'>High-resolution
                            CESM simulation from the Accelerated Scientific Discovery
                            phase of Yellowstone</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/ucar.cgd.ccsm4.CCSM4_20C_LE.html"/>'>CCSM4
                            30-Member Ensemble of 20th Century (1970-2005)</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/project/trace.html"/>'>Simulation of the
                            Transient Climate of the Last 21,000 Years (TraCE-21ka)</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="https://www.earthsystemgrid.org/search.html?Project=NMME"/>'>
                            National Multi-Model Ensemble (NMME)
                        </a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/ucar.cgd.ccsm.output.html"/>'>CCSM
                            3.0 Model Output</a>
                    </div>
                </div> <!-- .row -->
            </div> <!-- .panel-body -->
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">Regional Climate Models</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2">
                        <a class="main-link" href='<c:url value="/project/narccap.html"/>'>
                            <img src='<c:url value="/images/narccap-logo-block.gif" />'/>
                        </a>
                    </div>

                    <div class="col-md-8">
                        <a class="main-link"
                           href='<c:url value="/project/narccap.html"/>'>NARCCAP: North
                            American Regional Climate Change Assessment Program</a>
                    </div>
                </div> <!-- .row -->
            </div> <!-- .panel-body -->
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">Analysis &amp; Visualization Software</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-2">
                        <a class="main-link" href='<c:url value="/project/ncl.html"/>'>
                            <img src='<c:url value="/images/NCL_Robinson.gif"/>'/>
                        </a>
                    </div>

                    <div class="col-md-8">
                        <a class="main-link" href='<c:url value="/dataset/ncl.html"/>'>NCL:
                            NCAR Command Language</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/pyngl.html"/>'>PyNGL: Python
                            Interface to the NCL Graphic Libraries</a>
                        <br>
                        <a class="main-link"
                           href='<c:url value="/dataset/pynio.html"/>'>PyNIO: Python
                            Interface for Multi-format Geoscientific Data I/O</a>
                    </div>
                </div> <!-- .row -->
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
