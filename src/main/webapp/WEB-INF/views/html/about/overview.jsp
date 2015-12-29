<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">
    <tiles:putAttribute type="string" name="title" value="About the Earth System Grid"/>
    <tiles:putAttribute type="string" name="pageTitle" value="About the Earth System Grid"/>

    <tiles:putAttribute name="body">
        <p>
            The Earth System Grid Federation (ESGF) is an international collaboration with a current
            focus on serving the World Climate Research Programme's (WCRP) Coupled Model Intercomparison
            Project (CMIP) and supporting climate and environmental science in general.
            The ESGF grew out of the larger Global Organization for Earth System Science Portals
            (GO-ESSP) community, and reflects a broad array of contributions from the collaborating
            partners listed below.
        </p>
        <p>
            This web site is a Gateway to scientific data collections, which may be hosted at sites
            around the globe.
            Gateways are web portals that allow you to register for access and discover data, and they
            work in conjunction with Data Nodes, which host data collections and provide services.
            Once registered at a Gateway, you have potential access to the entire ESGF network of
            Gateways and Data Nodes.
        </p>

        <c:url var="doeRedirectURL" value="/redirect.html">
            <c:param name="link" value="http://www.energy.gov"/>
        </c:url>

        <c:url var="scidacRedirectURL" value="/redirect.html">
            <c:param name="link" value="http://www.scidac.gov"/>
        </c:url>

        <c:url var="nsfRedirectURL" value="/redirect.html">
            <c:param name="link" value="http://www.nsf.gov"/>
        </c:url>

        <c:url var="europaRedirectURL" value="/redirect.html">
            <c:param name="link" value="http://cordis.europa.eu/fp7/ict/e-infrastructure/home_en.html"/>
        </c:url>


        <p>
            The U.S. Earth System Grid Center for Enabling Technologies (ESG-CET) project is a primary
            contributor of cyberinfrastructure for the ESGF effort, especially the gateways and nodes
            described above.
            ESG-CET's primary sponsor is the <a href="${doeRedirectURL}">Department of Energy's</a> <a
                href="${scidacRedirectURL}">Scientific Discovery through Advanced Computing program
            (SciDAC2)</a>,
            with co-sponsorship by the <a href="${nsfRedirectURL}">U.S. National Science Foundation
            (NSF)</a>.
        </p>

        <p>
            The Gateway effort, led by the The National Center for Atmospheric Research (NCAR), reflects
            important contributions from several sponsors.
            In addition to ESG-CET, the U.S. NSF contributed substantially to its development through
            NCAR's core program, the Cooperative Arctic Data and Information Service (CADIS), and the
            NSF TeraGrid Science Gateways program.
        </p>

        <p>
            The Earth System Curator project has led an effort to radically advance the richness of
            model metadata and the integration of simulations and data in the gateway.
            Currently sponsored by NOAA's Global Interoperability Program (GIP), Curator has also been
            generously supported by NSF and NASA.
        </p>

        <p>
            The Curator team collaborated closely with the EU METAFOR (Common Metadata for Climate
            Modelling Digital Repositories) project, which developed the Common Information Model (CIM)
            and related technologies.
            METAFOR is sponsored by the <a href="${europaRedirectURL}">EU Framework Programme 7
            (FP7)</a>.
        </p>

        <p>
            <a href='<c:url value="/help/download-help.htm"/>'>Need help using ESG?</a>
        </p>

        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <strong>ESG-CET/ESGF Collaborators</strong>
                    </div>
                    <div class="panel-body">
                        <jsp:useBean id="CollabMap" class="java.util.LinkedHashMap" scope="request"/>

                        <c:set target="${CollabMap}" value="http://www.anl.gov" property="Argonne National Laboratory"/>
                        <c:set target="${CollabMap}" value="http://www.lbl.gov"
                               property="Lawrence Berkeley National Laboratory"/>
                        <c:set target="${CollabMap}" value="http://www.llnl.gov"
                               property="Lawrence Livermore National Laboratory"/>
                        <c:set target="${CollabMap}" value="http://www.lanl.gov/worldview"
                               property="Los Alamos National Laboratory"/>
                        <c:set target="${CollabMap}" value="http://www.ncar.ucar.edu"
                               property="National Center for Atmospheric Research"/>
                        <c:set target="${CollabMap}" value="http://www.ornl.gov"
                               property="Oak Ridge National Laboratory"/>
                        <c:set target="${CollabMap}" value="http://www.pmel.noaa.gov"
                               property="Pacific Marine Environmental Laboratory"/>
                        <c:set target="${CollabMap}" value="http://www.isi.edu"
                               property="University of Southern California/Information Sciences Institute"/>

                        <ul>
                            <c:forEach items="${CollabMap}" var="entry" varStatus="loop">

                                <c:url var="redirectURL" value="/redirect.html">
                                    <c:param name="link" value="${entry.value}"/>
                                </c:url>
                                <li><a href="${redirectURL}">${entry.key}</a></li>

                            </c:forEach>
                        </ul>
                    </div> <!-- .panel-body -->
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <strong>Additional ESGF Collaborators</strong>
                    </div>
                    <div class="panel-body">
                        <jsp:useBean id="AddCollabMap" class="java.util.LinkedHashMap" scope="request"/>

                        <c:set target="${AddCollabMap}" value="http://nci.org.au"
                               property="Australian National University/NCI"/>
                        <c:set target="${AddCollabMap}" value="http://badc.nerc.ac.uk/home/index.html"
                               property="British Atmospheric Data Center"/>
                        <c:set target="${AddCollabMap}" value="http://www.earthsystemcurator.org"
                               property="Earth System Curator"/>
                        <c:set target="${AddCollabMap}" value="http://www.gfdl.noaa.gov"
                               property="Geophysical Fluid Dynamics Laboratory"/>
                        <c:set target="${AddCollabMap}" value="http://www.dkrz.de"
                               property="German Climate Computing Centre (DKRZ)"/>
                        <c:set target="${AddCollabMap}" value="http://www.jpl.nasa.gov"
                               property="Jet Propulsion Laboratory"/>
                        <c:set target="${AddCollabMap}" value="http://ccsr.aori.u-tokyo.ac.jp/index-e.html"
                               property="University of Tokyo Center for Climate System Research"/>

                        <ul>
                            <c:forEach items="${AddCollabMap}" var="entry" varStatus="loop">
                            <c:url var="redirectURL" value="/redirect.html">
                                <c:param name="link" value="${entry.value}"/>
                            </c:url>
                            <li><a href="${redirectURL}">${entry.key}</a></li>

                            </c:forEach>
                    </div> <!-- .panel-body -->
                </div>
            </div>
        </div> <!-- .row -->

    </tiles:putAttribute>
</tiles:insertDefinition>
