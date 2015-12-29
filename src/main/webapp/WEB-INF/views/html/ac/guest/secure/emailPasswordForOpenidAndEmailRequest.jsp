<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout" >
	
<tiles:putAttribute type="string" name="title" value="Password Retrieval" />
<tiles:putAttribute type="string" name="pageTitle" value="Password Retrieval" />

<tiles:putAttribute name="body">

	<common:form-errors commandName="command" />
	
	<div>
        Please enter your OpenID and Email Address and a <span style="font-weight: bold;">New Password</span> will be generated and sent to you by email.
	</div>

	<br>
    <p class="small">
        Required fields are marked with an asterisk <b>*</b>.
    </p>
    <springForm:form method="post">
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <browse:input-row fieldPath="openid" fieldName="OpenID" isRequired="true"/>
                        <browse:input-row fieldPath="email" fieldName="Email Address" isRequired="true"/>
                        <div style="text-align: center; padding: 10px 0 0 0;">
                            <button type="submit" class="btn btn-default" id="submit-button">Submit</button>
                        </div>
                    </div>
                    <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->
    </springForm:form>

</tiles:putAttribute>
</tiles:insertDefinition>
