<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Confirm Broadcast Message"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Confirm Broadcast Message"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div>
            Here is a preview of the new broadcast message:
            <br>
            <c:choose>
                <c:when test="${not empty command.messageText }">
                    <div class="broadcast-message-banner" style="background-color: ${command.bannerColor};">
                            ${gateway:formatReturnToBreak(command.messageText)}
                    </div>
                    <br>
                </c:when>
                <c:otherwise>
                    <i>(No message)</i>
                    <br>
                </c:otherwise>
            </c:choose>

            To continue editing, use the browser Back button.
            <br>
            <c:url var="setBanner" value="/administration/broadcastMessage.html"/>
            <br>
        </div>

        <span>
            <springForm:form method="POST" commandName="command" action="${setBanner}"
                     style="float:left; padding: 0px 10px 0px 0px">
                <input type="hidden" name="bannerConfirmed" value="true"/>
                <button type="submit" class="btn btn-default" id="confirmBannerButton">Accept</button>

                <input type="hidden" name="messageText" value="${fn:escapeXml(command.messageText)}"/>
                <input type="hidden" name="bannerColor" value="${command.bannerColor}"/>
            </springForm:form>

            <c:url var="cancelBanner" value="/administration/broadcastMessage.html"/>
            <form action="${cancelBanner}" method="GET">
                <button type="submit" class="btn btn-default" id="cancelButton">Cancel</button>
            </form>
        </span>

    </tiles:putAttribute>

</tiles:insertDefinition>