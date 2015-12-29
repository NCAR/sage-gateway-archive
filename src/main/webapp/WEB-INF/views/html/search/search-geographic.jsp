<%-- ACADIS search page --%>

<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${title}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="${pageTitle}"/>

    <tiles:putAttribute name="style">
        .coordinate {
        padding: 7px;
        }
    </tiles:putAttribute>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="searchForm"/>

        <div class="search">

            <div class="selected_facets">

                <ul>
                    <c:if test="${not empty spatialFacetModel}">

                        <li class="facet">
                            <h4>Spatial</h4>

                            <div style="display: inline; float: left; text-align: center;">${spatialFacetModel.northernLatitude}<br/>${spatialFacetModel.westernLongitude}&nbsp;&nbsp;${spatialFacetModel.easternLongitude}<br/>${spatialFacetModel.southernLatitude}
                            </div>
                            <div style="display: inline; float: left; padding: 0 0 0 5px;"><a
                                    href="${spatialFacetModel.removalUrl }"><span class="remove">remove</span></a>
                            </div>
                        </li>

                    </c:if>

                    <c:if test="${not empty temporalFacetModel }">

                        <li class="facet">
                            <h4>Temporal</h4>
                            <ul>
                                <div style="display: inline; float: left;">
                                    <c:if test="${false eq temporalFacetModel.startDateEmpty }">
                                        Start Date: ${temporalFacetModel.startDate }<br/>
                                    </c:if>
                                    <c:if test="${false eq temporalFacetModel.endDateEmpty }">
                                        End Date: ${temporalFacetModel.endDate }
                                    </c:if>
                                </div>
                                <div style="display: inline; float: left; padding: 0 0 0 5px;"><a
                                        href="${temporalFacetModel.removalUrl }"><span class="remove">remove</span></a>
                                </div>
                            </ul>
                        </li>

                    </c:if>

                    <c:forEach var="selectedFacet" items="${selectedFacets}">
                        <li class="facet">
                            <h4>${selectedFacet.name}</h4>
                            <ul>
                                <c:forEach var="selectedConstraint" items="${selectedFacet.constraints}">
                                    <li>${selectedConstraint.name} <a href="${selectedConstraint.removalUrl}"><span
                                            class="remove">remove</span></a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>

                </ul>

            </div>

            <springForm:form method="get" commandName="searchForm" class="form-inline">
                <c:forEach var="hiddenParam" items="${searchForm.hiddenSearchFormParameters}">
                    <input type="hidden" name="${hiddenParam.name}" value="${hiddenParam.value}">
                </c:forEach>
                <div class="form-group">
                    <label class="sr-only" for="freeText">Search</label>
                    <!-- width applied as style to override width:auto (http://getbootstrap.com/css/#forms-inline) -->
                    <springForm:input path="freeText" id="freeText" class="form-control" style="width: 400px;"/>
                    <button type="submit" class="btn btn-default" id="searchButton">Search</button>
                    <c:url var="searchUrl" value="/search.html"></c:url>
                    <form method="get" action="${searchUrl}">
                        <button type="reset" class="btn btn-default" id="clearSearchButton">Clear Search</button>
                    </form>
                    <a href="<c:url value="/search/search-help.html"/>">Search Help</a>
                </div>
            </springForm:form>

            <c:if test="${pagedSearchResults.pageNumber == 0}">
                <c:set var="firstview" value="true"/>
            </c:if>

        </div> <!-- .search -->

        <div class="available_facets">

                <%-- We only want to show the add/edit spatial links if there are still other available facets or if the spatial constraint has already been applied. --%>
            <c:if test="${fn:length(availableFacets) > 0}">
                <div class="facet">
                    <h4>Spatial</h4>
                    <ul>
                        <c:choose>
                            <c:when test="${empty spatialFacetModel}">
                                <li><a onclick="spatialModalPanel();">Add Spatial Constraint...</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a onclick="editSpatialModalPanel();">Edit Spatial Constraint...</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </c:if>
            <c:if test="${fn:length(availableFacets) == 0 && not empty spatialFacetModel}">
                <div class="facet">
                    <h4>Spatial</h4>
                    <ul>
                        <li><a onclick="editSpatialModalPanel();">Edit Spatial Constraint...</a></li>
                    </ul>
                </div>
            </c:if>

                <%-- We only want to show the add/edit temporal links if there are still other available facets or if the spatial constraint has already been applied. --%>
            <c:if test="${fn:length(availableFacets) > 0}">
                <div class="facet">
                    <h4>Temporal</h4>
                    <ul>
                        <c:choose>
                            <c:when test="${empty temporalFacetModel}">
                                <li><a onclick="temporalModalPanel();">Add Temporal Constraint...</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a onclick="editTemporalModalPanel();">Edit Temporal Constraint...</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </c:if>
            <c:if test="${fn:length(availableFacets) == 0 && not empty temporalFacetModel}">
                <div class="facet">
                    <h4>Temporal</h4>
                    <ul>
                        <li><a onclick="editTemporalModalPanel();">Edit Temporal Constraint...</a></li>
                    </ul>
                </div>
            </c:if>


            <c:choose>
                <c:when test="${fn:length(availableFacets) > 0}">
                    <c:forEach var="facet" items="${availableFacets}">

                        <div class="facet">

                            <h4>${facet.name}</h4>

                                <%--Constraints 1-5 --%>
                            <ul>
                                <c:forEach var="constraint" items="${facet.constraints}" end="4">
                                    <li>
                                        <a href="${constraint.addUrl}">${gateway:formatWordWrap(constraint.name)}</a>&nbsp;(${constraint.count})
                                    </li>
                                </c:forEach>
                            </ul>

                                <%--Constraints 6+ --%>
                            <c:if test="${fn:length(facet.constraints) > 5}">
                                <div class="more">
                                    <ul>
                                        <c:forEach var="constraint" items="${facet.constraints}" begin="5">
                                            <li>
                                                <a href="${constraint.addUrl}">${gateway:formatWordWrap(constraint.name)}</a>&nbsp;(${constraint.count})
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <c:if test="${fn:length(facet.constraints) > 5}">
                                <span class="facetLink">...Show More</span>
                            </c:if>

                        </div>

                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <i>No Search Facets Available.</i>
                </c:otherwise>
            </c:choose>
        </div> <!-- .available_facets -->

        <c:if test="${firstview != true && fn:length(pagedSearchResults.list) > 0 && searchForm.resultSize > 0}">
            <div class="stats">
                <div class="count">

                    <c:set var="resultEnd" value="${searchForm.startIndex + searchForm.resultSize}"/>

                    <c:if test="${resultEnd >= searchForm.resultSize}">
                        <c:set var="resultEnd"
                               value="${searchForm.startIndex + fn:length(pagedSearchResults.list)}"/>
                    </c:if>

                        ${searchForm.startIndex + 1} - ${resultEnd} of ${searchResults.resultCount} results
                </div>

                <div class="resultsPerPage">
                    Show:
                    <c:forEach items="${resultsPerPageOptions}" var="option">
                        <c:choose>
                            <c:when test="${option.value == searchResults.criteria.resultSize}">
                                ${option.value}
                            </c:when>
                            <c:otherwise>
                                <a href="${option.searchURI}">${option.value}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <div class="results">
            <c:choose>
                <c:when test="${fn:length(pagedSearchResults.list) > 0 && searchForm.resultSize > 0}">

                    <form action="<c:url value="/download/datasets.html"/>" method="get">

                        <c:if test="${searchForm.variableNameFacet != null}">
                            <c:forEach items="${searchForm.variableNameFacet.constraints}" var="constraint">
                                <input type="hidden" name="variableName" value="${constraint.name}">
                            </c:forEach>
                        </c:if>
                        <c:if test="${searchForm.variableStandardNameFacet != null}">
                            <c:forEach items="${searchForm.variableStandardNameFacet.constraints}" var="constraint">
                                <input type="hidden" name="standardVariableName" value="${constraint.name}">
                            </c:forEach>
                        </c:if>


                        <display:table name="pagedSearchResults" id="result" requestURI="${searchForm.searchURI}">

                            <display:column sortable="false" class="result" title="${downloadAll}">
                                <div class="result">
                                    <div class="title">
                                        <c:choose>
                                            <c:when test="${result.type == 'Dataset' || result.type == 'Software'}">
                                                <a href="<c:url value="/dataset/${result.shortName}.html" />">${result.title}</a>
                                            </c:when>
                                            <c:when test="${result.type == 'Project'}">
                                                <a href="<c:url value="/project/${result.shortName}.html" />">${result.title}</a>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                    <div class="metadata">
                                        <c:choose>
                                            <c:when test="${result.type == 'Dataset' || result.type == 'Software'}">
                                                ${result.type}
                                            </c:when>
                                            <c:when test="${result.type == 'Project'}">
                                                ${result.type}
                                            </c:when>
                                        </c:choose>
                                    </div>
                                    <div class="description">
                                        <gateway:Abbreviate maxWidth="250" value="${result.description}"/>
                                    </div>
                                </div>
                            </display:column>


                            <display:setProperty name="paging.banner.placement" value="bottom"/>
                            <display:setProperty name="paging.banner.group_size" value="10"/>
                            <display:setProperty name="paging.banner.page.separator" value=" "/>

                            <display:setProperty name="paging.banner.some_items_found">
                                <span class="pagination"></span>
                            </display:setProperty>

                            <display:setProperty name="paging.banner.all_items_found">
                                <span class="pagination"></span>
                            </display:setProperty>

                            <display:setProperty name="paging.banner.onepage">
                                <span class="pagination"></span>
                            </display:setProperty>

                            <display:setProperty name="paging.banner.first">
                                <div class="pagination">
                                    {0} <span class="next"><a href="{3}">Next</a></span>
                                </div>
                            </display:setProperty>

                            <display:setProperty name="paging.banner.full">
                                <div class="pagination">
                                    <span class="previous"><a href="{2}">Previous</a></span> {0} <span class="next"><a
                                        href="{3}">Next</a></span>
                                </div>
                            </display:setProperty>

                            <display:setProperty name="paging.banner.last">
                                <div class="pagination">
                                    <span class="previous"><a href="{2}">Previous</a></span> {0}
                                </div>
                            </display:setProperty>

                        </display:table>
                    </form>
                </c:when>
                <c:otherwise>
                    <c:if test="${firstview != true}">
                        <p>
                            Sorry, no Search results found.
                        </p>

                        <p>
                            Search Suggestions:
                        <ul>
                            <li>Check your spelling.</li>
                            <li>Try more general words.</li>
                            <li>Try different words that mean the same thing.</li>
                            <c:url var="adeLink" value="/redirect.html">
                                <c:param name="link" value="http://nsidc.org/acadis/search/"/>
                            </c:url>
                            <li>Try the <a href="${adeLink}">Arctic Data Explorer</a></li>
                        </ul>
                        </p>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div> <!-- .results -->

        <div id="spatialOverlayPanel" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Spatial Constraints</h4>
                    </div>
                    <div class="modal-body">
                        <div class="text_bold, coordinate" align="center">
                            <div align="center" style="padding-bottom: 2px;">North</div>
                            <input type="text" id="north" name="hasSouthLimitLessOrEqual" size="4" value='90'/>

                            <div>
                                <span class="text_bold, coordinate" align="right">
                                    West <input type="text" id="west" name="hasEastLimitGreaterOrEqual" size="4" value='-180'/>
                                </span>
                                <span align="center" style="width: 100px;">
                                    &nbsp;
                                </span>
                                <span class="text_bold, coordinate" align="left">
                                    <input type="text" id="east" name="hasWestLimitLessOrEqual" size="4" value='180'/> East
                                </span>
                            </div>

                            <input type="text" id="south" name="hasNorthLimitGreaterOrEqual" size="4"
                                   value='60'/>

                            <div>South</div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="spatialReturnButton">Accept Spatial Constraint</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal" id="spatialCancelButton">Cancel</button>
                    </div>
                </div> <!-- /.modal-content -->
            </div> <!-- /.modal-dialog -->
        </div> <!-- /.modal -->

        <div id="temporalOverlayPanel" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Temporal Constraints</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-4">
                                <div class="form-group">
                                    <label for="startDateIn">
                                        Start Date
                                    </label>
                                    <input type="text" id="startDateIn" name="startDate" size="15" value="" class="form-control"/>
                                    <span class="small">YYYY-MM-DD</span>
                                </div> <!-- .form-group -->
                            </div>

                            <div class="col-xs-4">
                                <div class="form-group">
                                    <label for="endDateIn">
                                        End Date
                                    </label>
                                    <input type="text" id="endDateIn" name="endDate" size="15" value="" class="form-control"/>
                                    <span class="small">YYYY-MM-DD</span>
                                </div> <!-- .form-group -->
                            </div>
                        </div> <!-- .row -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="temporalReturnButton">Accept Temporal Constraint</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal" id="temporalCancelButton">Cancel</button>
                    </div>
                </div> <!-- /.modal-content -->
            </div> <!-- /.modal-dialog -->
        </div> <!-- /.modal -->

        <script type="text/javascript">
            var spatialOverlay;
            var temporalOverlay;

            function clearSearchButtonHandler() {

                <c:url var="clearSearchLink" value="/search.html" />
                var url = "${clearSearchLink}";
                window.location = url;
            }

            function init() {
                $("#clearSearchButton").on("click", clearSearchButtonHandler);
                $("#spatialReturnButton").on("click", spatialReturnButtonHandler);
                $("#temporalReturnButton").on("click", temporalReturnButtonHandler);
                $("#temporalCancelButton").on("click", temporalCancelButtonHandler);
                $("#spatialCancelButton").on("click", spatialCancelButtonHandler);

                // find the Spatial Constraints panel and hide it
                spatialOverlay = $("#spatialOverlayPanel");
                spatialOverlay.modal("hide");

                // find the Temporal Constraints panel and hide it
                temporalOverlay = $("#temporalOverlayPanel");
                temporalOverlay.modal("hide");

                <%-- Start with all detail section divs hidden --%>
                $('div.more').hide();

                <%-- click handler for link shows/hides Constraint list --%>
                <%-- Initial text must match the link text --%>
                $('.facetLink').click(function () {
                    $(this).parent().find('div.more').toggle();
                    $(this).text($(this).text() == '...Show More' ? '...Show Fewer' : '...Show More');
                });
            } // init()

            var editSpatialModalPanel = function () {

                var n = '${spatialFacetModel.northernLatitude}';
                var s = '${spatialFacetModel.southernLatitude}';
                var e = '${spatialFacetModel.easternLongitude}';
                var w = '${spatialFacetModel.westernLongitude}';
                document.getElementById("north").value = n;
                document.getElementById("south").value = s;
                document.getElementById("east").value = e;
                document.getElementById("west").value = w;

                spatialOverlay.modal("show");
            }

            var spatialModalPanel = function () {
                spatialOverlay.modal("show");
            }

            var editTemporalModalPanel = function () {

                var startDate = '${temporalFacetModel.startDate}';
                var endDate = '${temporalFacetModel.endDate}';

                document.getElementById("startDateIn").value = startDate;
                document.getElementById("endDateIn").value = endDate;

                temporalOverlay.modal("show");
            }

            var temporalModalPanel = function () {
                temporalOverlay.modal("show");
            }

            function spatialReturnButtonHandler() {

                var n = document.getElementById("north").value;
                var s = document.getElementById("south").value;
                var e = document.getElementById("east").value;
                var w = document.getElementById("west").value;

                <%-- If there are no values entered into the spacial dialog box, do submit the form. --%>
                if ((n == null || n.length == 0) && (s == null || s.length == 0) && (e == null || e.length == 0) && (w == null || w.length == 0)) {

                    alert("No spatial parameters were entered, so no spatial constraints will be applied");
                    spatialOverlay.modal("hide");

                } else {

                    var searchForm = document.getElementById("searchForm");
                    var freeText = document.getElementById("freeText");

                    if (freeText == "" || freeText == null || freeText.value == "" || freeText.value == null) {

                        freeText.parentNode.removeChild(freeText);
                    }

                    <%-- Make sure to remove the northLatitude, easternLongitude, southernLatitude, and westernLongitude from the form as well.   we don't want to submit them twice. --%>
                    var hiddenNorth = document.getElementById("northernLatitude");

                    if (hiddenNorth == "" || hiddenNorth == null) {

                        hiddenNorth = document.createElement("input");
                        hiddenNorth.type = "hidden";
                        hiddenNorth.name = "northernLatitude";
                        searchForm.appendChild(hiddenNorth);
                    }
                    hiddenNorth.value = n;


                    var hiddenEast = document.getElementById("easternLongitude");

                    if (hiddenEast == "" || hiddenEast == null) {

                        hiddenEast = document.createElement("input");
                        hiddenEast.type = "hidden";
                        hiddenEast.name = "easternLongitude";
                        searchForm.appendChild(hiddenEast);
                    }

                    hiddenEast.value = document.getElementById("east").value;


                    var hiddenSouth = document.getElementById("southernLatitude");

                    if (hiddenSouth == "" || hiddenSouth == null) {

                        hiddenSouth = document.createElement("input");
                        hiddenSouth.type = "hidden";
                        hiddenSouth.name = "southernLatitude";
                        searchForm.appendChild(hiddenSouth);
                    }

                    hiddenSouth.value = document.getElementById("south").value;


                    var hiddenWest = document.getElementById("westernLongitude");

                    if (hiddenWest == "" || hiddenWest == null) {

                        hiddenWest = document.createElement("input");
                        hiddenWest.type = "hidden";
                        hiddenWest.name = "westernLongitude";
                        searchForm.appendChild(hiddenWest);
                    }

                    hiddenWest.value = document.getElementById("west").value;

                    searchForm.submit();

                    spatialOverlay.modal("hide");
                }
            }

            function temporalReturnButtonHandler() {


                var startDateIn = document.getElementById("startDateIn").value.toString();
                var endDateIn = document.getElementById("endDateIn").value.toString();

                <%-- If there are no values entered into the temporal dialog box, do submit the form. --%>
                if ((startDateIn == null || startDateIn == "") && (endDateIn == null || endDateIn == "")) {

                    alert("No Dates were entered, so no temporal constraints will be applied.");
                    temporalOverlay.modal("hide");

                } else {

                    var freeText = document.getElementById("freeText");

                    if (freeText == "" || freeText == null || freeText.value == "" || freeText.value == null) {

                        freeText.parentNode.removeChild(freeText);
                    }

                    var searchForm = document.getElementById("searchForm");
                    var startDate = document.getElementById("startDate");

                    if (startDate == null) {

                        startDate = document.createElement("input");
                        startDate.type = "hidden";
                        startDate.name = "startDate";
                        searchForm.appendChild(startDate);
                    }
                    startDate.value = startDateIn;

                    var endDate = document.getElementById("endDate");

                    if (endDate == null) {
                        endDate = document.createElement("input");
                        endDate.type = "hidden";
                        endDate.name = "endDate";
                        searchForm.appendChild(endDate);
                    }
                    endDate.value = endDateIn;
                    searchForm.submit();

                    temporalOverlay.modal("hide");
                }
            }

            function temporalCancelButtonHandler() {
                temporalOverlay.modal("hide");

            }

            function spatialCancelButtonHandler() {
                spatialOverlay.modal("hide");
            }

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition> <%-- general-layout --%>
