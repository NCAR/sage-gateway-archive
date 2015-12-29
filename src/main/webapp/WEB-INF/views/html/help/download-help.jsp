<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Help"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Help"/>

    <tiles:putAttribute name="body">

        <h3>Overview</h3>

        <p>
            The Earth System Grid (ESG) provides a range of web sites and services enabling scientific data discovery
            and access.
            The ESG is organized into Gateways, sites where you can register for access and search for data and Data
            Nodes, sites which host the data itself.
            Once registered at a Gateway, you have potential access to the entire system of Gateways and Data Node
            services.
            The entire system is named the Earth System Grid federation.
        </p>

        <h3>Registration</h3>

        <p>
            Downloading data may require registration at one of the ESG federation gateways.
        </p>

        <p>
            To register click on the <i>Login</i> tab at the top of the page and then click <i>Create new Account</i>
            link and follow the steps.
        </p>

        <p>
            You will receive an email regarding confirming your account. Please follow the instructions in order to
            activate your ESG federation account. Please be aware that your "spam" filter may intercept these messages.
        </p>

        <h3>Groups</h3>

        <p>
            Downloading data may require membership in an appropriate group.
        </p>

        <p>
            You will be automatically presented a group membership questionnaire when required.
        </p>
        Certain membership requests may take some time to process. You will be contacted by email regarding the status of new account registration and group membership requests. Please be aware that your "spam" filter may intercept these messages.
        </p>

        <h3>Login</h3>

        <p>
            You are assigned an <i>OpenID</i> when you register. Please use only <b>one OpenID to login to all ESGF
            Gateways</b>.
        </p>

        <p>
            This OpenID is your identity which allows you access to the many ESG federation gateways and services.
        </p>

        <p>
            Your registration email contains your OpenID. You are strongly urged to record you OpenID when you receive
            it.
        </p>

        <p>
            Use your assigned OpenID to login to ESG Gateways. Please use only <b>one OpenID to login to all ESGF
            Gateways</b>.
        </p>

        <p>
            Enter your OpenID when you see an <i>OpenID</i> login box and click the <i>Login</i> button.
            Next enter your password.
        </p>

        <p>
            You may be asked to enter your OpenID in order to re-identify with the ESG federation periodically when
            accessing services such as data servers for file downloads.
        </p>

        <p>
            Please Use one OpenID to login to all ESGF Gateways.
            If you already have an OpenID at another Gateway there is no need to create a new one for this portal.
            Using multiple OpenIDs can cause system inconsistencies.
        </p>

        <h3>Data Download</h3>

        <p>
            Data may be accessed by direct link for individual files, Wget script and Data Mover Lite (DML) for multiple
            files.
        </p>

        <p>
            When you have selected a dataset of interest, click the <i>Download files for this collection</i> link to
            show the <i>Download Data</i> page.
            The <i>Download Data</i> page is where you can chose which files to download.
        </p>

        <h3>Wget Script</h3>

        <p>
            Wget is a command line tool available on most Unix systems.
            We recommend <b>Linux</b> for this download option.<br/>
        </p>

        <p>
            Wget lets you download multiple files selected in the <i>Download Data</i> page.
        </p>

        <p>
            On the <i>Download Data</i> page you may select files by checkbox.
            You can download all the files by checking the small box on the left in the first line at the top of the
            table(s) or you can select individual files by clicking on the boxes in the left column of the table(s).
            The file listing can be filtered with the <i>Sub Select File Results</i> tool on the left side of the page.
        </p>

        <p>
            After selecting all the files you want, click on the <i>Download All Selected Files</i> button at the top
            right of the window.
            You'll then be shown the number of files and the volume of the data you have requested.
            When you click on <i>Download</i> you'll get a Wget script you can run to retrieve the data files
            themselves.
        </p>

        <p>
            Certain data systems require a MyProxy certificate for access. The Wget script will generate a MyProxy
            certificate.
        </p>

        <p>
            <i>Wget version 1.13 is recommended. Wget version 1.12 or greater is required. Earlier versions may not
                download files exceeding 2Gb in size.</i>
            <br/>
            <i>Mac OSX users may experience difficulty with WGet due to version incompatibilities.</i>
        </p>


        <h3>Direct link</h3>

        <p>
            You can download individual files by clicking on the blue <i>download</i> links in the far right column of
            the table in the <i>Download Data</i> page.
            You may need to re-enter your OpenID when downloading in this way.
        </p>

        <p><b>Further Information</b></p>

        <p>
            Any questions, comments, or concerns may be emailed to: <a
                href="mailto:<spring:message code="contactus.mailaddress"/>"><spring:message
                code="contactus.mailaddress"/></a>
        </p>

        <p>
            <a href="http://www.gnu.org/software/wget/">Wget Help</a>
        </p>

        <p>
            <a href="http://grid.ncsa.illinois.edu/myproxy/protocol/">MyProxy Help</a>
        </p>
    </tiles:putAttribute>
</tiles:insertDefinition>