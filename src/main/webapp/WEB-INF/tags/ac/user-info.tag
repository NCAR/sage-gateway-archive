<%@tag body-content="empty"%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="user" required="true" type="sgf.gateway.model.security.User"  %>

<div class="row">
    <div class="col-xs-8">
        <div class="descriptive_row_table descriptive_row_table_bottom_row">
            <span class="descriptive_row_name">
                <span>User Name</span>
            </span>
            <span class="descriptive_row_value">
                <span><c:out value="${user.userName}" /></span>
            </span>
        </div>

        <div class="descriptive_row_table descriptive_row_table_bottom_row">
            <span class="descriptive_row_name">
                <span>First Name</span>
            </span>
            <span class="descriptive_row_value">
                <span><c:out value="${user.firstName}" /></span>
            </span>
        </div>

        <div class="descriptive_row_table descriptive_row_table_bottom_row">
            <span class="descriptive_row_name">
                <span>Last Name</span>
            </span>
            <span class="descriptive_row_value">
                <span><c:out value="${user.lastName}" /></span>
            </span>
        </div>

        <div class="descriptive_row_table descriptive_row_table_bottom_row">
            <span class="descriptive_row_name">
                <span>Email</span>
            </span>
            <span class="descriptive_row_value">
                <span>
                    <a href="mailto:<c:out value="${user.email}"/>">
                        <c:out value="${user.email}" />
                    </a>
                </span>
            </span>
        </div>

        <div class="descriptive_row_table descriptive_row_table_bottom_row">
            <span class="descriptive_row_name">
                <span>Status</span>
            </span>
            <span class="descriptive_row_value">
                <span><c:out value="${user.status}" /></span>
            </span>
        </div>

        <div class="descriptive_row_table descriptive_row_table_bottom_row">
            <span class="descriptive_row_name">
                <span>OpenIDx</span>
            </span>
            <span class="descriptive_row_value">
                <span><c:out value="${user.openid}" /></span>
            </span>
        </div>
    </div>
</div> <!-- .row -->