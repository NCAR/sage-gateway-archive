
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%-- Insert text into metadata.broadcast_message to show up in the banner on all pages.
Your text from here will appear in a yellow banner at the top of the page. 
For example:
The system will be down on May 5 for maintenance.

Sample links:
Check out the <a href="/search.html">Search</a> capabilities.
</p>
This is a link to <a href="http://www.earthsystemgrid.org/">production</a>.
--%>
<c:if test="${not empty message.messageText}">

<div class="broadcast-message-banner" style="background-color: ${message.bannerColor};">

<p>
${gateway:formatReturnToBreak(message.messageText)}
</p>

</div>
</c:if>

