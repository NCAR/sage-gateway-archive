<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Feedback"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Feedback"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    Please help us improve our website by giving us feedback!
                </p>

                <p>
                    If you have a question about data or need assistance with managing your data please email: <a
                        href="mailto:<spring:message code="contactus.mailaddress"/>"><spring:message
                        code="contactus.mailaddress"/></a>
                </p>
            </div> <!-- .panel-body -->
        </div>

        <br/>

        <p class="small">
            Required fields are marked with an asterisk <b>*</b>.
        </p>

        <springForm:form commandName="command" method="post">
            <browse:input-row fieldPath="name" fieldName="Name"/>
            <browse:input-row fieldPath="email" fieldName="Email Address"/>
            <browse:input-textarea fieldPath="feedback" fieldName="Feedback" isRequired="true"/>
            <%-- Phone is never used; It's a honeypot field to stop spambots --%>
            <div class="hidden">
                <label for="phone">Phone Number: </label>
                <springForm:input path="phone" title="Leave Blank"/>
            </div>
            <button type="submit" class="btn btn-default" id="submit-button">Submit Feedback</button>
        </springForm:form>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>