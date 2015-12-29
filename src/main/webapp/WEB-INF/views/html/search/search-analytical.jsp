<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Analytical Search" />
    <tiles:putAttribute type="string" name="pageTitle" value="Analytical Search" />

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command" />

        <springForm:form method="get" action="analyticResult.html" commandName="command" class="form-inline">
            <div class="form-group">
                <label class="sr-only" for="queryText">Search</label>
                <!-- width applied as style to override width:auto (http://getbootstrap.com/css/#forms-inline) -->
                <springForm:input path="queryText" id="queryText" class="form-control" style="width: 400px;"/>
                <button type="submit" class="btn btn-default" id="searchButton">Search</button>
                <button type="reset" class="btn btn-default" id="clearSearchButton">Clear Search</button>
            </div>
        </springForm:form>

        <c:if test="${searchResult != null}">
            <c:choose>
                <c:when test="${searchResult.resultCount > 0}">
                    <div>
                        Result count: ${searchResult.resultCount}
                    </div>
                    <div>
                        <p></p>
                        <c:forEach var="result" items="${searchResult.results}">
                            <div>
                                <p>
                                <c:choose>
                                    <c:when test="${result.remoteIndexable}">
                                        <c:url var="redirectURL" value="/redirect.html" >
                                            <c:param name="prv" value="mlt" />
                                            <c:param name="link" value="${result.detailsURI}" />
                                        </c:url>
                                        <b><a href="${redirectURL}">${result.title}</a></b> <a href="${redirectURL}"><img src="<c:url value="/images/icons/external-link-icon.svg" />" ></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="gatewayURL" value="/${result.type.toLowerCase()}/${result.shortName}.html" />
                                        <b><a href="${gatewayURL}">${result.title}</a></b>
                                    </c:otherwise>
                                </c:choose>
                                <br/>
                                ${result.type}
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    No results found!
                </c:otherwise>
            </c:choose>
        </c:if>

    </tiles:putAttribute>

</tiles:insertDefinition>
