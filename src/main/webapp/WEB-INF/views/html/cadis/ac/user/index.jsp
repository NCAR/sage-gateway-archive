<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="User Account Summary"/>
    <tiles:putAttribute type="string" name="pageTitle" value="User Account Summary"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Options</h4>
                    </div>
                    <div class="panel-body">
                        <cadis:account-navigator selected_page="SUMMARY"/>
                    </div>
                </div>
            </div>

            <div class="col-md-8" align="center">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">User Information</h4>
                    </div>
                    <div class="panel-body">
                        <ac:user-info user="${user}"/>
                    </div>
                </div>
            </div>
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
