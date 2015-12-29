<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Gateway Scheduled Tasks"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Gateway Scheduled Tasks"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div>
            Scheduler Name: ${scheduler.schedulerName}<br>
            Currently Running: ${scheduler.started}<br>

            Number of Active Jobs: ${fn:length(scheduler.currentlyExecutingJobs)}<br>
            <c:forEach items="${scheduler.currentlyExecutingJobs}" var="jobContext">
                Job: ${jobContext.jobDetail.fullName}&nbsp;&nbsp; Fired At: ${jopContext.fireTime}&nbsp;&nbsp;Triggered By: ${jobContext.trigger.fullName}
                <br>
            </c:forEach>
        </div>
        <br>
        <p>${fn:length(triggers)} entries</p>
        <div>
            <table class="table table-condensed table-bordered table-hover table-striped">
                <thead>
                    <tr>
                        <th>Trigger Name</th>
                        <th>Previous Fire Time</th>
                        <th>Next Fire Time</th>
                        <th>Run Job Now</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="trigger" items="${triggers}">
                        <tr>
                            <td nowrap="nowrap">${trigger.key.name}</td>
                            <td nowrap="nowrap">${trigger.previousFireTime}</td>
                            <td nowrap="nowrap">${trigger.nextFireTime}</td>
                            <td nowrap="nowrap">
                                <c:url value="/root/triggerJob.html" var="triggerUrl">
                                    <c:param name="jobName" value="${trigger.jobKey.name}"/>
                                    <c:param name="jobGroupName" value="${trigger.jobKey.group}"/>
                                </c:url>
                                <a href="${triggerUrl}">Run Job Now</a><br>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
