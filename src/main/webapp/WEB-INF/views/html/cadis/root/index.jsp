<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Gateway Root Administrator Index"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Gateway Root Administrator Index"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div class="admin_actions">

            <h4>
                <legend>Users &amp; Groups Administration</legend>
            </h4>

            <ul>
                <li><a href="<c:url value="/ac/root/listAllUsers.html"/>">Manage User Accounts</a></li>
                <li class="desc">Inspect, disable or re-enable a user's account on this Gateway.</li>
                <li><a href="<c:url value="/ac/admin/listGroupUsers.html?status=0&groupName=ACADIS"/>">Add New User to
                    ACADIS Group</a></li>
                <li class="desc">Enroll a new user into the ACADIS group.</li>
                <li><a href="<c:url value="/ac/admin/listGroupUsers.html?status=3&groupName=ACADIS"/>">Manage Current
                    ACADIS Users</a></li>
                <li class="desc">Modify roles for a member of the ACADIS group, or remove the user from the group.</li>
            </ul>

            <h4>
                <legend>Gateway Metadata</legend>
            </h4>
            <ul>
                <li><a href="<c:url value="/project/form/create.html"/>">Create New Project</a></li>
                <li class="desc">Create a new ACADIS Project.</li>
                <li><a href="<c:url value="/observing/platform.html"/>">Create Platform Type</a>
                <li class="desc">Add a new Platform Type.</li>
            </ul>

            <h4>
                <legend>Search Provider</legend>
            </h4>
            <ul>
                <li><a href="<c:url value="/root/search/reindex.html"/>">Reindex</a> (Root Admin Only)</li>
                <li class="desc">Reindex the SOLR Search Index</li>
                <li><a href="<c:url value="/root/search/analytic.html"/>">Analytical Search</a> (Root Admin Only)</li>
                <li class="desc">Access the Analytical Search page</li>
            </ul>

            <h4>
                <legend>Gateway Scheduled Tasks</legend>
            </h4>
            <ul>
                <li><a href="<c:url value="/root/scheduled-tasks.html"/>">Run Tasks</a> (Root Admin Only)</li>
            </ul>

            <h4>
                <legend>Integration</legend>
            </h4>
            <ul>
                <li><a href="<c:url value="/admin/catalogJob.html"/>">Run EOL Catalog Job</a> (Root Admin Only)</li>
                <li class="desc">Run job to publish all EOL Catalogs</li>
                <li><a href="<c:url value="/harvest/root/ADEDatasets.html"/>">Harvest ADE Datasets</a> (Root Admin Only)
                </li>
                <li class="desc">Run job to harvest ADE Datasets into Search</li>
                <li><a href="<c:url value="/delete/root/ADEDatasets.html"/>">Remove ADE Datasets</a> (Root Admin Only)
                </li>
                <li class="desc">Run job to remove ADE Datasets from Search</li>
            </ul>

            <h4>
                <legend>Broadcast Message</legend>
            </h4>
            <ul>
                <li><a href="<c:url value="/administration/broadcastMessage.html"/>">Update Broadcast Banner Message</a>
                    (Root Admin Only)
                </li>
            </ul>

            <h4>
                <legend>Feature Toggles</legend>
            </h4>
            <ul>
                <li><a href="<c:url value="/togglz/index"/>">Toggle Features ON or OFF</a> (Root Admin Only)</li>
            </ul>

        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
