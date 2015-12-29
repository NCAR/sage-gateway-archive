<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Download Individual Files"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Download Individual Files"/>

    <tiles:putAttribute name="body">

        <div class="noFilesSelectedError" hidden>
                <%-- include the generic error handler --%>
            <common:generic-error message="No files selected for download."/>
        </div>

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-body">
                <p>
                    Files can be downloaded through a Web Browser, downloaded in bulk via a <a
                        href="http://www.gnu.org/s/wget/">WGET</a> script, or requested from our Deep Storage Archives
                    (SRM).
                </p>
            </div> <!-- .panel-body -->
        </div>

        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">Filter the File List</h4>
                    </div>
                    <div class="panel-body">
                        <form name="subselectForm" id="subselectForm" method="get" action="viewCollectionFilesFilter.html">
                            <input type="hidden" name="selectAll" value="${selectAll}"/>
                            <c:forEach var="datasetDownloadRow" items="${datasetDownloadRows}">
                                <input type="hidden" name="datasetId" value="${datasetDownloadRow.dataset.identifier}"/>
                            </c:forEach>

                            <div class="form-group">
                                <label for="filenamePattern">
                                    Filter with Filename
                                </label>
                                <input type="text" name="filenamePattern" path="filenamePattern" id="filenamePattern" class="form-control"
                                       value="${filenamePattern}" />
                            </div> <!-- .form-group -->

                            <p class="small">
                                Use * for a wildcard character.
                                <br>
                                Regular Expressions will not work at this time.
                            </p>

                            <!-- variable list dropdown -->
                            <c:if test="${not empty variableSet}">
                                <div class="dropdown">
                                    <button class="btn btn-default dropdown-toggle" type="button" id="variablesDropdown"
                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                        Filter with Variables
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" aria-labelledby="variablesDropdown">
                                        <c:set var="prevName" value=""/>
                                        <c:set var="varIdx" value="-1"/>
                                        <c:forEach var="variableSelectableElement" items="${variableSet}">
                                            <li>
                                                <a href="#" data-value="option${varIdx}">
                                                    <c:if test="${prevName != variableSelectableElement.element.name}">
                                                        <c:set var="prevName" value="${variableSelectableElement.element.name}"/>
                                                        <c:set var="varIdx" value="${varIdx + 1}"/>

                                                        <div class="checkbox">
                                                            <label>
                                                                <input type="checkbox" class="variableNameCheckbox"
                                                                       id="${variableSelectableElement.element.name}"
                                                                       name="variableName"
                                                                       value="${variableSelectableElement.element.identifier}"
                                                                       <c:if test="${variableSelectableElement.selected == true}">CHECKED</c:if> />
                                                                    ${gateway:formatWordWrap(variableSelectableElement.element.description)}
                                                                (${variableSelectableElement.element.name})

                                                            </label>
                                                        </div> <!-- .checkbox -->
                                                    </c:if>
                                                    <span class="hidden">
                                                        <input type="checkbox" class="${variableSelectableElement.element.name}"
                                                               id="${variableSelectableElement.element.identifier}"
                                                               name="variableId" value="${variableSelectableElement.element.identifier}"
                                                               <c:if test="${variableSelectableElement.selected == true}">CHECKED</c:if> />
                                                    </span>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <br>
                            <button type="button" class="btn btn-default" id="subselectButton">Apply</button>
                        </form>
                    </div>
                </div>
            </div> <!-- .col-md-4 -->

            <div class="col-md-8">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">File Download Selection</h4>
                    </div>
                    <div class="panel-body">
                        <form name="downloadForm" id="downloadForm" method="POST" action="downloadLogicalFiles.html">
                            <c:forEach var="datasetDownloadRow" items="${datasetDownloadRows}">
                                <input type="hidden" name="datasetId" value="${datasetDownloadRow.dataset.identifier}"/>
                                <div>
                                    <div>
                                        <div style="float: right;">
                                            <button type="button" class="btn btn-default downloadButton">Download Options For Selection</button>
                                        </div>
                                        <b>${datasetDownloadRow.dataset.title}</b>
                                    </div>
                                    <div>${fn:length(datasetDownloadRow.logicalFileDownloadRows)} entries</div>

                                    <table id="${datasetDownloadRow.dataset.identifier}" class="table table-condensed table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>
                                                    <input type="checkbox" class="selectAllCheckbox ${datasetDownloadRow.dataset.identifier}">
                                                </th>
                                                <th>File</th>
                                                <th>Size</th>
                                                <th>Format</th>
                                                <th>Location</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="logicalFileRow" items="${datasetDownloadRow.logicalFileDownloadRows}" varStatus="num">
                                                <tr>
                                                    <td>
                                                        <input type="checkbox" name="logicalFileId"
                                                               value="${logicalFileRow.identifier }"
                                                               class="downloadCheckbox ${datasetDownloadRow.dataset.identifier}"/>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${logicalFileRow.directDownload}">
                                                                <c:url value="/download/fileDownload.htm" var="downloadURL">
                                                                    <c:param name="logicalFileId">${logicalFileRow.identifier}</c:param>
                                                                </c:url>
                                                                <%-- display the value and don't escape formatting characters using c:out --%>
                                                                <a href="${downloadURL}">${gateway:formatWordWrap(logicalFileRow.name)}</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <%-- display the value and don't escape formatting characters using c:out --%>
                                                                ${gateway:formatWordWrap(logicalFileRow.name)}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <gateway:FileSizeUnitFormat
                                                                fileSize="${logicalFileRow.size}"/>
                                                    </td>
                                                    <td><c:out value="${logicalFileRow.format}"/></td>
                                                    <td><c:out value="${logicalFileRow.location}"/></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                            </c:forEach>

                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function submitDownloadForm() {
                // if files have been selected
                if ($(".downloadCheckbox:input:checked").length) {
                    $("#downloadForm").submit();
                } else {
                    // display the no files selected message
                    $(".noFilesSelectedError").show();
                } // if
            } // submitDownloadForm()

            function submitSubSelectForm() {
                $("#subselectForm").submit();
            }

            function init() {
                // add checkbox click handling
                $(".downloadCheckbox").on("click", function selectFileFunction() {
                    if (this.checked) {
                        // hide the no files selected message
                        $(".noFilesSelectedError").hide();
                    } // if
                    /*
                     * If ALL of the individual files are checked then the top checkbox is checked.
                     * Otherwise, it's unchecked.
                     */
                    if ($(".downloadCheckbox:input:checked").length ==
                            $(".downloadCheckbox:input").length) {
                        $(".selectAllCheckbox").prop("checked", true);
                    } else {
                        $(".selectAllCheckbox").prop("checked", false);
                    } // if
                });
                $(".selectAllCheckbox").on("click", function selectAllFilesFunction() {
                    // if the header checkbox is checked, select all of the individual files
                    if (this.checked) {
                        // hide the no files selected message
                        $(".noFilesSelectedError").hide();
                        $(".downloadCheckbox").prop("checked", true);
                    } else {
                        $(".downloadCheckbox").prop("checked", false);
                    } // if
                });

                <%-- Select all the checkboxes if the showAll request parameter is 'true'. --%>
                <c:if test="${selectAll}">
                $(".downloadCheckbox").prop("checked", true);
                </c:if>

                // add click handling to all download buttons
                $(".downloadButton").on("click", submitDownloadForm);
                $("#subselectButton").on("click", submitSubSelectForm);

                /*
                 * Suppress the default behavior to hide the dropdown menu once
                 * an item has been clicked.
                 * Code from:
                 * http://www.benknowscode.com/2014/09/option-picker-bootstrap-dropdown-checkbox.html
                 * Working demo:
                 * codepen.io/bseth99/pen/fboKH
                 */
                var options = [];
                $('.dropdown-menu a').on("click", function(event) {
                    var $target = $(event.currentTarget),
                        val = $target.attr('data-value'),
                        $inp = $target.find('input'),
                        idx;

                    if ((idx = options.indexOf(val)) > -1) {
                        options.splice(idx, 1);
                        // uncheck after all events are done
                        setTimeout(function() {
                            $inp.prop('checked', false)
                        }, 0);
                    } else {
                        options.push(val);
                        // check after all events are done
                        setTimeout(function() {
                            $inp.prop('checked', true)
                        }, 0);
                    } // if

                    $(event.target).blur();

                    //console.log(options);

                    // return false to prevent the dropdown from closing
                    return false;
                });
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>