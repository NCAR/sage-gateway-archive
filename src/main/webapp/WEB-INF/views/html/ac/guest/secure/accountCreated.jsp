<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Account Created"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Account Created"/>

    <tiles:putAttribute name="body">

        <div class="panel panel-default">
            <div class="panel-body">
                <p>Welcome, you have successfully created a new account!</p>

                <p>&nbsp;</p>

                <p>IMPORTANT: You have been sent a welcome email containing important information that you will need
                    prior to logging into this website.</p>

                <p>&nbsp;</p>

                <p>Please check your Spam or Junk folders if you do not see our welcome email in your Inbox.</p>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>