<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="OpenID Retrieval Complete"/>
    <tiles:putAttribute type="string" name="pageTitle" value="OpenID Retrieval Complete"/>

    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p>Your OpenID has been sent to the email address provided.</p>

                        <p>Thank you.</p>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->
    </tiles:putAttribute>

</tiles:insertDefinition>
