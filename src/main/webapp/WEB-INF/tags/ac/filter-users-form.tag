<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<springForm:form method="GET" class="form-inline">
    <c:if test="${not empty status}">
        <springForm:hidden path="status"/>
    </c:if>
    <c:if test="${not empty groupName}">
        <springForm:hidden path="groupName"/>
    </c:if>

    <div class="row">
        <div class="col-xs-4">
            <div class="form-group">
                <label class="sr-only" for="text">Search</label>
                <springForm:input path="text" id="text" size="25" class="form-control"
                                  placeholder="Search for users"/>&nbsp;
                <button type="submit" class="btn btn-default" id="submit-simple-query">Search</button>
            </div>
        </div>
    </div>

    <p class="small">
        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
        Search tip: You can search using any portion of a user's First Name, Last Name, Username, or Email Address.
    </p>
</springForm:form>