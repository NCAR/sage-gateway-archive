<%@ include file="/WEB-INF/views/html/common/include.jsp" %>
<%@ include file="/WEB-INF/views/html/ac/builtin_objects.jsp" %>

<tiles:insertDefinition name="general-layout" >

    <tiles:putAttribute type="string" name="title" value="Edit User Information" />
    <tiles:putAttribute type="string" name="pageTitle" value="Edit User Information" />

    <tiles:putAttribute name="body">

        <div class="return_link">
            <a href="<c:url value="/ac/root/listAllUsers.html"/>">Manage All Users</a>
        </div>
        <br>

        <!-- display form validation errors -->
        <common:form-errors commandName="command"/>

        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>
                        <form action="<c:url value="/ac/root/editAccountSummaryInfo.html" />" method="POST" >

                            <input type="hidden" value="${command.identifier}" name="identifier" />

                            <div class="descriptive_row_table descriptive_row_table_bottom_row">
                                <span class="descriptive_row_name">
                                    <span>Username</span>
                                </span>
                                <span class="descriptive_row_value">
                                    <span>${command.userName}</span>
                                </span>
                            </div>

                            <div class="descriptive_row_table descriptive_row_table_bottom_row">
                                <span class="descriptive_row_name">
                                    <span>Status</span>
                                </span>
                                <span class="descriptive_row_value">
                                    <span>${command.status}</span>
                                </span>
                            </div>

                            <div class="descriptive_row_table descriptive_row_table_bottom_row">
                                <span class="descriptive_row_name">
                                    <span>OpenID</span>
                                </span>
                                <span class="descriptive_row_value">
                                    <span>${command.openId}</span>
                                </span>
                            </div>

                            <div class="form-group">
                                <label for="firstName">
                                    First Name *
                                </label>
                                <input type="text" value="${command.firstName}" name="firstName" id="firstName" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="lastName">
                                    Last Name *
                                </label>
                                <input type="text" value="${command.lastName}" name="lastName" id="lastName" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="email">
                                    Email Address *
                                </label>
                                <input type="text" value="${command.email}" name="email" id="email" class="form-control"/>
                            </div> <!-- .form-group -->

                            <button type="submit" class="btn btn-default" id="save-button">Save</button>
                        </form>
                    </div> <!-- .panel-body -->
                </div>
            </div>
        </div>


    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
