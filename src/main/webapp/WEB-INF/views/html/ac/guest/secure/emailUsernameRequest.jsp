<%--
   Per Nathan H on 2 Dec 15: We need to keep this file, but there is no way to
   currently get to this file, which is a bug.
--%>

<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Username Retrieval"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Username Retrieval"/>

    <tiles:putAttribute name="body">

        <!-- Submission errors -->
        <common:form-errors commandName="command"/>

        <div>
            Please enter your Email Address and your Username will be sent to you.
        </div>

        <br>
        <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <p class="small">
                            Required fields are marked with an asterisk <b>*</b>.
                        </p>
                        <springForm:form method="post">
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
