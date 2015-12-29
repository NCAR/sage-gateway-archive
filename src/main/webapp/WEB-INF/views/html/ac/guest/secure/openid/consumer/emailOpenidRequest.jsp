<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout" >

<tiles:putAttribute type="string" name="title" value="OpenID Retrieval" />
<tiles:putAttribute type="string" name="pageTitle" value="OpenID Retrieval" />

<tiles:putAttribute name="body">

	<!-- Submission errors -->
	<common:form-errors commandName="command" />
	
	<!-- Instructions -->
    <p>Your OpenID is a unique URL that was assigned to you when you created your account.</p>
    <p>Using your OpenID you can login to any site within the Earth System Grid Federation.</p>
    <p>If you have ever logged in to this site, you may enter your email address and an email that contains your OpenID will be sent to you.</p>

	<!-- Form -->
	<br>
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-body">
                    <p class="small">
                        Required fields are marked with an asterisk <b>*</b>.
                    </p>

                    <springForm:form  method="post">
                        <browse:input-row fieldPath="email" fieldName="Email Address" isRequired="true" />
                        <div style="padding: 10px 0 0 0;">
                            <button type="submit" class="btn btn-default" id="submit-button">Submit</button>
                        </div>
                    </springForm:form>
                </div> <!-- .panel-body -->
            </div>
        </div> <!-- .col-md-6 -->
    </div> <!-- .row -->
	
</tiles:putAttribute>
</tiles:insertDefinition>
