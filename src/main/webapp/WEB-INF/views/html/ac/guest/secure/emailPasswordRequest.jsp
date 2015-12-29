<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Password Retrieval"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Password Retrieval"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div>
            Please enter your Username and Email Address and a <strong>New Password</strong> will
            be generated and sent to you.
        </div>

        <br>
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>
                        <springForm:form method="post" commandName="command">
                            <div class="form-group">
                                <label for="userName">
                                    Username *
                                </label>
                                <springForm:input path="userName" id="userName" class="form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="email">
                                    Email Address *
                                </label>
                                <springForm:input type="email" path="email" id="email" class="form-control"/>
                            </div> <!-- .form-group -->

                            <button type="submit" class="btn btn-default" id="submit-button">Submit</button>
                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->

    </tiles:putAttribute>

</tiles:insertDefinition>
