<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${activity.name}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="${activity.name}"/>

    <tiles:putAttribute name="body">

        <ul class="nav nav-tabs">
            <li class="active" role="presentation"><a href="#tab1" data-toggle="tab"><em>${activity.activityType.description}</em></a></li>
        </ul>
        <div class="tab-content" style="line-height: 1.331;">
            <div class="tab-pane active" id="tab1">
                <browse:descriptive-row fieldName="Name" fieldValue="${activity.name}" showEmpty="true"/>
                <browse:descriptive-row fieldName="Description" fieldValue="${activity.description}"
                                        showEmpty="true"/>
                <browse:dataset-list-panel datasets="${gateway:filterPublished(activity.associatedDatasets)}"
                                           title="Associated Collections"/>
            </div>
        </div> <!-- .tab-content -->
        <br> <!-- visual whitespace -->

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
