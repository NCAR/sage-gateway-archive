<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Group Memebership Removal"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Removal of Group Membership Completed"/>

    <tiles:putAttribute name="body">
        <br>
        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Options</h4>
                    </div>
                    <div class="panel-body">
                        <cadis:account-navigator selected_page="CURRENT_MEMBERSHIPS"/>
                    </div>
                </div>
            </div>

            <div class="col-md-8" align="center">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Current Group Memberships</h4>
                    </div>
                    <div class="panel-body">
                        Your membership with group <span class="highlight"><c:out value='${data.name}'/></span> has been
                        canceled.
                    </div>
                </div>
            </div>
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
