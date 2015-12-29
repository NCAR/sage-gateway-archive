<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Change Password"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Change Password"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <c:choose>
            <c:when test="${not user.remoteUser}">
                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <p>Password must be at least 6 characters in length.</p>
                                <p class="small">
                                    Required fields are marked with an asterisk <b>*</b>.
                                </p>
                                <springForm:form method="post">
                                    <div class="form-group">
                                        <label for="oldPassword">
                                            Current Password *
                                        </label>
                                        <springForm:password path="oldPassword" id="oldPassword" class="form-control"/>
                                    </div> <!-- .form-group -->

                                    <div class="form-group">
                                        <label for="password">
                                            New Password *
                                        </label>
                                        <springForm:password path="password" id="password" class="form-control"/>
                                    </div> <!-- .form-group -->

                                    <div class="form-group">
                                        <label for="confirmNewPassword">
                                            Confirm New Password *
                                        </label>
                                        <springForm:password path="confirmNewPassword" id="confirmNewPassword" class="form-control"/>
                                    </div> <!-- .form-group -->

                                    <button type="submit" class="btn btn-default" id="submit-button">Submit</button>
                                </springForm:form>
                            </div> <!-- .panel-body -->
                        </div>
                    </div> <!-- .col-md-6 -->
                </div> <!-- .row -->

            </c:when>

            <c:otherwise>
                <p>
                    Your account does not have a password associated with it.
                </p>
                <p>
                    Please return to your home Gateway (where you originally created your account) and change your
                    password there.
                </p>
            </c:otherwise>
        </c:choose>

    </tiles:putAttribute>

</tiles:insertDefinition>
