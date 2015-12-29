<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout" >
	
	<tiles:putAttribute type="string" name="title" value="${project.name}" />
	<tiles:putAttribute type="string" name="pageTitle" >
        ${project.description}
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">

        <ul class="nav nav-tabs">
            <li class="active" role="presentation"><a href="#tab1" data-toggle="tab"><em>Summary</em></a></li>
            <li role="presentation"><a href="#tab2" data-toggle="tab"><em>Edit</em></a></li>
            <li role="presentation"><a href="#tab3" data-toggle="tab"><em>Metrics</em></a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tab1">
                <p>National Center for Atmospheric Research, Boulder Colorado</p>
                <p>June 1-13, 2008</p>
                <p><a href="https://wiki.ucar.edu/display/dycores/Home" />Dycore Project Home Page</a></p>
                <p>Peter H. Lauritzen (CGD, NCAR), Christiane Jablonowski (University of Michigan)<br> Mark Taylor (Sandia National Laboratories), Ramachandran D. Nair (IMAGe, NCAR)</p>
                <p>
                    <c:if test="${project.associatedDatasetCount > 0}">
                        <browse:dataset-list-panel datasets="${gateway:filterPublished(project.associatedDatasets)}" title="Nested Collections" />
                    </c:if>
                </p>
            </div>

            <div class="tab-pane" id="tab2">
                <p>Administrative Functions - Only if memberships permit</p>
            </div>
            <div class="tab-pane" id="tab3">
                <p>Metrics -  Only if memberships permit</p>
            </div>
        </div> <!-- .tab-content -->

	</tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>
