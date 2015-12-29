<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Password Retrieval Complete"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Password Retrieval Complete"/>

    <tiles:putAttribute name="body">
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p>
                            A new Password has been generated and sent to the Email Address provided.
                        </p>

                        <p>
                            Thank you.
                        </p>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->
    </tiles:putAttribute>

</tiles:insertDefinition>
