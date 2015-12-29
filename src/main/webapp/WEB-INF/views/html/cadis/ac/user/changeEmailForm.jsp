<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Change Email Address Form"/>
    <tiles:putAttribute name="pageTitle" value="Change Email Address"/>

    <tiles:putAttribute name="body">

        <!-- display form validation errors -->
        <common:form-errors commandName="command" />

        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Options</h4>
                    </div>
                    <div class="panel-body">
                        <cadis:account-navigator selected_page="CHANGE_EMAIL"/>
                    </div>
                </div>
            </div> <!-- .col-md-4 -->

            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>

                        <springForm:form method="post">
                            <div class="form-group">
                                <label for="email">
                                    New Email Address *
                                </label>
                                <springForm:input type="email" path="email" id="email" class="form-control"/>
                            </div> <!-- .form-group -->
                            <button type="submit" class="btn btn-default" id="submit-button">Update</button>
                        </springForm:form>
                    </div> <!-- .panel-body -->
                </div>
            </div> <!-- .col-md-6 -->
        </div> <!-- .row -->
    </tiles:putAttribute>

</tiles:insertDefinition>
