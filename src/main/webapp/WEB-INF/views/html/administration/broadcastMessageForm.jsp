<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Broadcast Message"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Broadcast Message"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <c:url var="confirmBannerLink" value="/administration/broadcastMessageConfirmation.html"/>

        <springForm:form method="get" action="${confirmBannerLink}">

            The current Message Text is:
            <br>

            <c:choose>
                <c:when test="${not empty currentMessage.messageText}">
                    <div style="background-color: LightSteelBlue; padding: 10px;">
                        <c:out value="${gateway:formatReturnToBreak(currentMessage.messageText)}" escapeXml="true"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <i>(No Message Text)</i>
                    <br>
                </c:otherwise>
            </c:choose>

            <br>
            Which displays as:
            <br>

            <c:choose>
                <c:when test="${not empty currentMessage.messageText}">
                    <div class="broadcast-message-banner" style="background-color: ${currentMessage.bannerColor};">
                            ${gateway:formatReturnToBreak(currentMessage.messageText)}
                    </div>
                    <br>
                </c:when>

                <c:otherwise>
                    <i>(No banner)</i>
                    <br>
                </c:otherwise>
            </c:choose>

            <br/>
            Examples of Message Text:<br>
            <code>
                <div style="margin-left: 40px;">
                    The system will be down on &lt;strong&gt;May 5th&lt;/strong&gt; for maintenance.
                    <br>
                    <!-- 		Check out the &lt;a href="/contactUs.htm"&gt;Contact Us&lt;/a&gt; page. -->
                    <!-- 		</p> -->
                    There will be an outage on &lt;a href="http://www.earthsystemgrid.org/"&gt;ESG Production&lt;/a&gt;
                </div>
            </code>
            <br>

            Message Text:<br>
            <textarea id="messageText" name="messageText" rows="10" cols="80">${currentMessage.messageText}</textarea>
            <br><br>

            <span>
                HTML hex color code for banner:&nbsp;
                <springForm:input path="bannerColor" name="HTML Banner Color Code" value="${currentMessage.bannerColor}"/>
            </span>
            <br><br>
            <button type="submit" class="btn btn-default" id="addBroadcastMessage">Preview Broadcast Message</button>
            <spring:message code="messages.gateway.administration.link" var="gatewayAdministrationLink"/>
            <a class="btn btn-default" href="<c:url value="${gatewayAdministrationLink }"/>">Cancel</a>

        </springForm:form>
        <br>

    </tiles:putAttribute>

</tiles:insertDefinition>