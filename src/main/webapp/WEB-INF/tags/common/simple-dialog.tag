<%@tag body-content="empty"%>
<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<%@ attribute name="message" required="true" %>

<%--
    Modal for messages with Yes/No response

    To use, include the following line in the body element:
        <common:simple-dialog message="Message shown in the modal body"/>
    Include JavaScript to show the modal:
        $("#simpleDialogModal").modal('show');

    The .fade class on the .modal element adds a fading and sliding animation
    effect while showing and hiding the modal window.
--%>
<div class="modal fade" id="simpleDialogModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">     <!-- modal styling (border, background-color, etc.) -->
            <div class="modal-header">  <!-- header -->
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Confirmation Required</h4>
            </div>
            <div class="modal-body">    <!-- body -->
                <c:out value="${message}"/>
            </div>
            <div class="modal-footer">  <!-- footer -->
                <!--
                    The id's modalYes and modalNo can be used to attach actions to the buttons.
                    Example:
                    $("button#modalYes").on("click", doTheYesAction);
                    function doTheYesAction() {
                        console.log("yayyyyy");
                        $("#simpleDialogModal").modal("hide");
                    } // doTheYesAction()
                -->
                <button type="button" id="modalYes" class="btn btn-primary">Yes</button>
                <button type="button" id="modalNo" class="btn btn-default" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>

<%--
    Modal for messages with OK response

    To use, include the following line in the body element:
        <common:simple-dialog message="Message shown in the modal body"/>
    Include JavaScript to show the modal:
        $("#simpleWarningModal").modal('show');

    The .fade class on the .modal element adds a fading and sliding animation
    effect while showing and hiding the modal window.
--%>
<div class="modal fade" id="simpleWarningModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">     <!-- modal styling (border, background-color, etc.) -->
            <div class="modal-header">  <!-- header -->
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Warning</h4>
            </div>
            <div class="modal-body">    <!-- body -->
                <!-- text filled in by createWarningDialog() -->
            </div>
            <div class="modal-footer">  <!-- footer -->
                <!--
                    The id modalOk can be used to attach actions to the buttons.
                    Example:
                    $("button#modalOk").on("click", doTheOkAction);
                    function doTheOkAction() {
                        console.log("yayyyyy");
                        $("#simpleDialogModal").modal("hide");
                    } // doTheOkAction()
                -->
                <button type="button" id="modalOk" class="btn btn-default" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>

<script>
    /*
     * Update the warning dialog text and display the modal popup.
     */
    function createWarningDialog(dialogText) {
        $("#simpleWarningModal").find(".modal-body").html(dialogText);
        $("#simpleWarningModal").modal('show');
    } // createWarningDialog()
</script>