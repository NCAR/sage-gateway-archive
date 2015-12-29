<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Contact Us"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Contact Us"/>

    <tiles:putAttribute name="body">

        <p>
            If you have any questions or problems with this site, please send email to: <a
                href="mailto:<spring:message code="contactus.mailaddress"/>"><spring:message
                code="contactus.mailaddress"/></a>
        </p>

        <p>
            Please include the following if possible:
            <ol>
                <li>Your OpenID.</li>
                <li>A brief description.</li>
            </ol>
        </p>
        <p>
            Thank you!
        </p>

    </tiles:putAttribute>

</tiles:insertDefinition>