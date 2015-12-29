<%-- ESG search page --%>

<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${title}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="${pageTitle}"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="searchForm"/>
        <div class="noFilesSelectedError" hidden>
                <%-- include the generic error handler --%>
            <common:generic-error message="No files selected for download."/>
        </div>

        <div class="search">

            <div class="selected_facets">

                <c:forEach var="selectedFacet" items="${selectedFacets}">
                    <div class="facet">
                        <h4>${selectedFacet.name}</h4>
                        <ul>
                            <c:forEach var="selectedConstraint" items="${selectedFacet.constraints}">
                                <li>${selectedConstraint.name} <a href="${selectedConstraint.removalUrl}"><span
                                        class="remove">remove</span></a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:forEach>
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

        </div>

        <div class="available_facets">
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
        </div>

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

                    <form id="downloadForm" action="<c:url value="/download/datasets.html"/>" method="get">

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

                        <c:set var="selectAll">
                            <div class="action">
                                <c:choose>
                                    <c:when test="${downloadableTargets}">
                                        <input type="checkbox" name="selectAll" class="selectAllCheckbox"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" name="selectAll" class="selectAllCheckbox" disabled="disabled"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:set>
                        <c:set var="downloadAll">
                            <div class="download">
                                <c:choose>
                                    <c:when test="${downloadableTargets}">
                                        <%-- Using a class instead of an id for downloadButton for consistency --%>
                                        <button type="button" class="btn btn-default downloadButton">Download Selected</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-default downloadButton" disabled="disabled">Download Selected</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:set>

                        <display:table name="pagedSearchResults" id="result" requestURI="${searchForm.searchURI}">

                            <display:column sortable="false" title="${selectAll}" class="action">
                                <div class="action">
                                    <c:choose>
                                        <c:when test="${result.type == 'Dataset' || result.type == 'Software'}">
                                            <input type="checkbox" name="datasetId" value="${result.shortName}"
                                                   class="downloadCheckbox"/>
                                        </c:when>
                                        <c:otherwise>
                                            &nbsp;
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </display:column>

                            <display:column sortable="false" class="result" title="${downloadAll}">
                                <div class="result">
                                    <div class="title">
                                        <a href="<c:url value="/dataset/${result.shortName}.html" />">${result.title}</a>
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
                        </ul>
                        </p>No Search results found.
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div> <!-- .results -->

        <script type="text/javascript">
            function submitDownloadForm() {
                // if files have been selected
                if ($(".downloadCheckbox:input:checked").length) {
                    $("#downloadForm").submit();
                } else {
                    // display the no files selected message
                    $(".noFilesSelectedError").show();
                } // if
            } // submitDownloadForm()

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
                    // if the select all checkbox is checked, select all of the individual files
                    if (this.checked) {
                        // hide the no files selected message
                        $(".noFilesSelectedError").hide();
                        $(".downloadCheckbox").prop("checked", true);
                    } else {
                        $(".downloadCheckbox").prop("checked", false);
                    } // if
                });

                // Start with all detail section divs hidden
                $('div.more').hide();

                // click handler for link shows/hides Constraint list
                // nitial text must match the link text
                $('.facetLink').click(function facetLinkClick() {
                    $(this).parent().find('div.more').toggle();
                    $(this).text($(this).text() == '...Show More' ? '...Show Fewer' : '...Show More');
                });

                // add click handling to the download buttos
                $(".downloadButton").on("click", submitDownloadForm);
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition> <%-- general-layout --%>
