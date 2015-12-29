<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<!-- hide the image on extra small devices -->
<div class="pageFooterColumn hidden-xs">
    <c:url var="nsfUrl" value="/redirect.html" >
        <c:param name="link" value="http://www.nsf.gov/" />
    </c:url>
    <a href="${nsfUrl}"><img src="<c:url value="/themes/cadis/images/nsf_logo.gif" />"
         height="75" width="75" alt="National Science Foundation" />
    </a>
</div>
<div class="pageFooterColumn">
    <div>
        <a href="<c:url value="/" />"> Home</a> |
        <a href="<c:url value="/legal/privacy_policy.htm" />">Privacy Policy</a> |
        <a href="<c:url value="/legal/terms_of_use.htm" />">Terms of Use</a> |
        <a href="<c:url value="/contactUs.htm" />">Contact</a>
    </div>
    <div class="pageFooterText">
        Gateway Portal Software version <c:out value="${gateway.versionStr}" />
    </div>
    <div class="pageFooterText">
        <c:url var="ucarUrl" value="/redirect.html" >
            <c:param name="link" value="http://www.ucar.edu/" />
        </c:url>
        Copyright &copy; ${copyrightDate} <a href="${ucarUrl}">UCAR</a>.  All Rights Reserved.
    </div>
</div>
<!-- hide the images on extra small and small devices -->
<div class="pageFooterColumn hidden-xs hidden-sm">
    <div>ACADIS Collaborators</div>
    <span class="pageFooterImage">
        <c:url var="eolUrl" value="/redirect.html" >
            <c:param name="link" value="https://www.eol.ucar.edu/" />
        </c:url>
        <a href="${eolUrl}"><img src="<c:url value="/themes/cadis/images/EOL_logo50_trans.gif" />"
                                 height="55" width="118" alt="Earth Observing Laboratory" /></a>
    </span>
    <span class="pageFooterImage">
        <c:url var="nsidcUrl" value="/redirect.html" >
            <c:param name="link" value="http://nsidc.org/" />
        </c:url>
        <a href="${nsidcUrl}"><img src="<c:url value="/themes/cadis/images/nsidc_trans_notext.gif" />"
                                   height="55" width="71" alt="National Snow and Ice Data Center" /></a>
    </span>
    <span class="pageFooterImage">
        <c:url var="unidataUrl" value="/redirect.html" >
            <c:param name="link" value="http://www.unidata.ucar.edu/" />
        </c:url>
        <a href="${unidataUrl}"><img src="<c:url value="/themes/cadis/images/unidata_logo.jpg" />"
                                     height="55" width="55" alt="Unidata" /></a>
    </span>
    <span class="pageFooterImage">
        <c:url var="cislUrl" value="/redirect.html" >
            <c:param name="link" value="http://www.cisl.ucar.edu/" />
        </c:url>
        <a href="${cislUrl}"><img src="<c:url value="/themes/cadis/images/roundCISL.jpg" />"
                                  height="55" width="55" alt="Computational and Information Systems Lab" /></a>
    </span>
</div>
