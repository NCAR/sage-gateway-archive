<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tiles:insertDefinition name="general-layout">

<tiles:putAttribute type="string" name="title" value="Gateway Administrator Home"/>
<tiles:putAttribute type="string" name="pageTitle" value="Gateway Administrator Home"/>

<tiles:putAttribute name="body">

    <common:success-message message="${successMessage}"/>

    <div class="admin_actions">

        <h4><legend>Users &amp; Groups Administration</legend></h4>

        <ul>
            <li><a href="<c:url value="/ac/root/listAllUsers.html"/>">Manage All Users</a></li>
            <li class="desc" >Inspect, disable or re-enable a user's account.</li>
            <li><a href="<c:url value="/ac/root/createGroup.html"/>">Create New Group</a></li>
            <li class="desc" >Create a new access control group associated with this Gateway.</li>
        </ul>

        <h4><legend>Gateway Metadata</legend></h4>
        <ul>
            <li><a href="<c:url value="/createProject.html"/>">Create New Project</a></li>
            <li class="desc">Create a new Project, which can then be associated with any top-level Dataset.</li>
            <li><a href="<c:url value="/publish/createDefaultDataset.html"/>">Create New Top-Level Dataset</a></li>
            <li class="desc">Create the root of a new dataset hierarchy, enter the minimal required metadata.</li>
        </ul>

        <h4><legend>Gateway Scheduled Tasks</legend></h4>
        <ul>
            <li><a href="<c:url value="/root/scheduled-tasks.html"/>">Run Tasks</a> (Root Admin Only)</li>
        </ul>

        <h4><legend>Search Provider</legend></h4>
        <ul>
            <li><a href="<c:url value="/root/search/reindex.html"/>">Reindex</a> (Root Admin Only)</li>
        </ul>

        <h4><legend>Analytical Search</legend></h4>
        <ul>
            <li><a href="<c:url value="/root/search/analytic.html"/>" >Analytical Search</a> (Root Admin Only)</li>
        </ul>

        <h4><legend>Publishing</legend></h4>
        <ul>
            <li><a href="<c:url value="/publish/publishThreddsCatalog.html"/>" >Publish THREDDS Catalog</a> (Root Admin Only)</li>
        </ul>

        <h4><legend>Broadcast Message</legend></h4>
        <ul>
            <li><a href="<c:url value="/administration/broadcastMessage.html"/>">Update Broadcast Banner Message</a> (Root Admin Only)</li>
        </ul>

    </div>

</tiles:putAttribute>

</tiles:insertDefinition>
