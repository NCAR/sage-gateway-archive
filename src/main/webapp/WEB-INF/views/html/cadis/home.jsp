<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute name="title" value="ACADIS Gateway"/>
    <tiles:putAttribute name="pageTitle" value=""/>

<%--
    <tiles:putAttribute name="links">
        <link rel="alternate" type="application/atom+xml" title="ACADIS Dataset Feed"
              href="<c:url value="/dataset.atom?offset=0&rpp=30" />">
    </tiles:putAttribute>
--%>
    <tiles:putAttribute name="body-header">
        <div>
            <common:success-message message="${successMessage}"/>
            <common:info-message message="${infoMessage}"/>
        </div>
    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <h4>
            Find, Download and Contribute Arctic Science Data
        </h4>
        <br>

        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Find and Download</h4>
                    </div>
                    <div class="panel-body">
                        <h4>Browse</h4>
                        <ul>
                            <li><a href="<c:url value="/contact.html"/>">Principal Investigators</a></li>
                            <li><a href="<c:url value="/project.html"/>">Projects</a></li>
                            <li><a href="<c:url value="/cadis/viewProjectsGeographically.html"/>">Geographically</a></li>
                        </ul>
                        <h4>Science Keyword Topics</h4>
                        <ul>
                            <c:forEach var="topic" items="${topics}">
                                <li><a href="<c:url value="/scienceKeywordTopic/${topic.displayText}.html"/>">${topic.displayText}</a>
                                </li>
                            </c:forEach>
                        </ul>

                        <h4>Additional Arctic Data Resources</h4>
                        <ul>
                            <c:url var="adeLink" value="/redirect.html">
                                <c:param name="link" value="http://nsidc.org/acadis/search/"/>
                            </c:url>
                            <li><a href="${adeLink}">Arctic Data Explorer</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Contribute Data</h4>
                    </div>
                    <div class="panel-body">
                        <p><a href="<c:url value="/cadis/howToContribute.html"/>">How to Contribute</a></p>

                        <p><a href="<c:url value="/ac/guest/secure/registration.html"/>">Register</a></p>

                        <p><a href="<c:url value="/ac/guest/secure/login.html"/>">Login</a></p>

                        <p><a href="<c:url value="/user/publish/contributeData.html"/>">Start Contributing Now</a></p>
                        <c:url var="submitPublicationLink" value="/redirect.html">
                            <c:param name="link"
                                     value="https://docs.google.com/a/ucar.edu/forms/d/1hN1hQqhJRV2VPdawSBkUNro6KsQJ9b4wfPiUv8rv3PU/viewform"/>
                        </c:url>
                        <p><a href="${submitPublicationLink}">Submit Publications Using ACADIS Data</a></p>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Information</h4>
                    </div>
                    <div class="panel-body">
                        <c:url var="aaLink" value="/redirect.html">
                            <c:param name="link" value="https://www.eol.ucar.edu/node/5796"/>
                        </c:url>
                        <p><a href="${aaLink}">Arctic Data Related Websites and Archives</a></p>

                        <p><a href="<c:url value="/dataset.atom?offset=0&rpp=30"/>">Recent Dataset Updates</a> <a
                                href="<c:url value="/dataset.atom?offset=0&rpp=30"/>"><img
                                src="<c:url value="/images/icons/feed-icon-14x14.png" />"/></a></p>

                        <c:url var="redirectURLplan" value="/redirect.html">
                            <c:param name="link" value="http://nsidc.org/acadis/docs/ACADIS_NSF_DMP_Template.docx"/>
                        </c:url>
                        <p><a href="<c:out value="${redirectURLplan}"/>">Data Management Plan Template (docx)</a></p>

                        <p><a href="<c:url value="/media/ACADIS_doc_template.docx"/>">Documentation Template (docx)</a></p>

                        <p><a href="<c:url value="/contactUs.htm"/>">Contact Us</a></p>

                        <p><a href="<c:url value="/cadis/about/aboutUs.htm"/>">About Us</a></p>

                        <c:url var="publicationLink" value="/redirect.html">
                            <c:param name="link" value="https://www.eol.ucar.edu/node/5279"/>
                        </c:url>

                        <p><a href="${publicationLink}">Publications</a></p>

                        <p><a href="<c:url value="metrics/summaryStatistics.html"/>">ACADIS Summary Statistics</a></p>

                        <p><a href="<c:url value="/documentation/index.html"/>">API Documentation</a></p>

                        <c:url var="rosettaDemoLink" value="/redirect.html">
                            <c:param name="link" value="https://rosetta.unidata.ucar.edu/createAcadis"/>
                        </c:url>

                        <p><a href="${rosettaDemoLink}">Rosetta Data Format Translation Tool Demo</a></p>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>

</tiles:insertDefinition>
