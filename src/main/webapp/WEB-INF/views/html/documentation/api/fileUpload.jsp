<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="File Upload API"/>
    <tiles:putAttribute type="string" name="pageTitle" value="File Upload API"/>

    <tiles:putAttribute name="body">

        <p>
            This REST API is for data providers who wish to script interactions to manage dataset files.
        </p>
        <p>
            Structure of File Upload API Rest URLs<br/>
            https://www.aoncadis.org/api/v1/dataset/&lt;dataset-short-name&gt;/file/&lt;file-name&gt;.&lt;extension&gt;
        </p>

        <p>
            The &lt;dataset-short-name&gt; can be found from the dataset HTML URL.  For example, the dataset found at the URL below has a short name of "CPL_MAR":<br/>
            https://www.aoncadis.org/dataset/CPL_MAR.html
        </p>

        <h3>Pre Requisites</h3>

        <p>
            You must have a dataset to upload files to as well as a user account with permissions to write to the dataset.
            Authentication
        </p>

        <p>
            The API supports HTTP Basic Authentication over SSL.
            Methods
        </p>

        <p>
            The API supports the following HTTP methods:  POST, PUT, and DELETE
        </p>

        <p>
            At this time, the PUT and POST methods do the same exact behavior.
        </p>

        <p>
            To replace a file, you must first DELETE it, then PUT it back.
        </p>

        <h3>Put/Post Examples</h3>

        <p>
            Below is an example curl command to push a file into a dataset with short name "CPL_MAR".  You will need to substitute your username and password and identify the file you are working with.
            <pre>
$ curl -v --user &lt;your_username&gt;:&lt;your_password&gt; -X PUT -F files=@"./&lt;file_to_upload.ext&gt;" https://www.aoncadis.org/api/v1/dataset/CPL_MAR/file/&lt;target_filename&gt;.&lt;extension&gt;
            </pre>
        </p>

        <p>
        If you were to upload a local file named "readme.txt" to your dataset with user: jdoe, pw: foo the command would be:
            <pre>
$ curl -v --user jdoe:foo -X PUT -F files=@"./readme.txt" https://www.aoncadis.org/api/v1/dataset/CPL_MAR/file/readme.txt
            </pre>
        </p>

        <p>
        For files that have spaces in their names, you will have to percent encode the spaces by hand:
            <pre>
$ curl -v --user jdoe:foo -X PUT -F files=@"./readme.txt" https://www.aoncadis.org/api/v1/dataset/CPL_MAR/file/file%20with%20spaces.txt
            </pre>
        </p>

        <p>
            A successful upload will show "201 Created" in the response:  HTTP/1.1 201 Created
        </p>

        <h3>Delete Example</h3>

        <p>
            You can also delete a file via this interface, for example, if you wanted to delete the readme.txt file:
            <pre>
$ curl -v --user jdoe:foo -X DELETE https://www.aoncadis.org/api/v1/dataset/CPL_MAR/file/readme.txt
            </pre>
        </p>

        <h3>Debugging</h3>

        <c:url var="curlLink" value="/redirect.html">
            <c:param name="link" value="http://curl.haxx.se/"/>
        </c:url>
        <c:url var="verboseLink" value="/redirect.html">
            <c:param name="link" value="http://curl.haxx.se/docs/manpage.html"/>
        </c:url>
        <p>
            You will get a pile of other logging if using the <a href="${curlLink}">curl</a> verbose option "<a href="verboseLink">-v</a>".
        </p>

    </tiles:putAttribute>

</tiles:insertDefinition>