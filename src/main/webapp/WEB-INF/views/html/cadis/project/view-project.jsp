<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Project: ${dataset.title}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Project"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>

        <div class="dataset_name">
            <h1>${dataset.title}</h1>
        </div>

        <ul class="nav nav-tabs">
            <li class="active" role="presentation"><a href="#summaryTab" data-toggle="tab"><em>Metadata</em></a></li>
            <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')">
                <li role="presentation"><a href="#adminTab" data-toggle="tab"><em>Edit</em></a></li>
            </authz:authorize>
            <c:if test="${fn:length(gateway:filterPublished(dataset.directlyNestedDatasets)) > 0}">
                <li role="presentation"><a href="#datasetsTab" data-toggle="tab"><em>Datasets</em></a></li>
            </c:if>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="summaryTab">

                <c:forEach var="role" items="${partiesByRole.keySet()}">
                    <c:set var="partiesString">
                        <c:forEach var="party" items="${partiesByRole.get(role)}">
                            ${party}
                            <br/>
                        </c:forEach>
                    </c:set>
                    <browse:descriptive-row fieldName="${role}" fieldValue="${partiesString}"
                                            showEmpty="false"/>
                </c:forEach>

                <browse:descriptive-row fieldName="Description"
                                        fieldValue="${gateway:formatReturnToBreak(dataset.description)}"
                                        showEmpty="false"/>

                <browse:descriptive-row fieldName="Project Group"
                                        fieldValue="${gateway:formatReturnToBreak(dataset.projectGroup)}"
                                        showEmpty="false"/>

                <c:set var="timeString">
                    <fmt:formatDate value="${dataset.descriptiveMetadata.timePeriod.begin}"/> - <fmt:formatDate
                        value="${dataset.descriptiveMetadata.timePeriod.end}"/>
                </c:set>
                <browse:descriptive-row fieldName="Period of Performance" fieldValue="${timeString}"
                                        showEmpty="false"/>

                <c:if test="${not empty predecessorProject}">
                    <c:set var="predecessorProjectLink">
                        <a href="<c:url value="/project/${predecessorProject.shortName}.html" />">${predecessorProject.title}</a>
                    </c:set>
                    <browse:descriptive-row fieldName="Preceding Project" fieldValue="${predecessorProjectLink}"
                                            showEmpty="false"/>
                </c:if>

                <c:if test="${not empty successorProject}">
                    <c:set var="successorProjectLink">
                        <a href="<c:url value="/project/${successorProject.shortName}.html" />">${successorProject.title}</a>
                    </c:set>
                    <browse:descriptive-row fieldName="Continuing Project" fieldValue="${successorProjectLink}"
                                            showEmpty="false"/>
                </c:if>

                <c:if test="${not empty dataset.descriptiveMetadata.geographicBoundingBox }">
                    <c:set var="maxLatitudeString">
                        <fmt:formatNumber type="number"
                                          value="${dataset.descriptiveMetadata.geographicBoundingBox.northBoundLatitude}"
                                          maxFractionDigits="3"/> degrees
                    </c:set>
                    <browse:descriptive-row showEmpty="false" fieldValue="${maxLatitudeString}"
                                            fieldName="Northernmost Latitude"/>

                    <c:set var="minLatitudeString">
                        <fmt:formatNumber type="number"
                                          value="${dataset.descriptiveMetadata.geographicBoundingBox.southBoundLatitude}"
                                          maxFractionDigits="3"/> degrees
                    </c:set>
                    <browse:descriptive-row showEmpty="false" fieldValue="${minLatitudeString}"
                                            fieldName="Southernmost Latitude"/>

                    <c:set var="minLongitudeString">
                        <fmt:formatNumber type="number"
                                          value="${dataset.descriptiveMetadata.geographicBoundingBox.westBoundLongitude}"
                                          maxFractionDigits="3"/> degrees
                    </c:set>
                    <browse:descriptive-row showEmpty="false" fieldValue="${minLongitudeString}"
                                            fieldName="Westernmost Longitude"/>

                    <c:set var="maxLongitudeString">
                        <fmt:formatNumber type="number"
                                          value="${dataset.descriptiveMetadata.geographicBoundingBox.eastBoundLongitude}"
                                          maxFractionDigits="3"/> degrees
                    </c:set>
                    <browse:descriptive-row showEmpty="false" fieldValue="${maxLongitudeString}"
                                            fieldName="Easternmost Longitude"/>
                </c:if>

                <c:set var="awardsString">
                    <c:forEach var="award" items="${dataset.awards}">
                        <div style="padding-top: 0px;">
                            <c:url var="awardLink" value="/redirect.html">
                                <c:param name="link"
                                         value="http://www.nsf.gov/awardsearch/showAward?AWD_ID=${award.awardNumber}"/>
                            </c:url>
                            <a href="${awardLink}"><c:out value="${award.awardNumber}"/></a>
                        </div>
                    </c:forEach>
                </c:set>
                <browse:descriptive-row fieldName="Award Numbers" fieldValue="${awardsString}"
                                        showEmpty="false"/>
            </div> <!-- summaryTab -->

            <c:if test="${fn:length(gateway:filterPublished(dataset.directlyNestedDatasets)) > 0}">
                <div class="tab-pane" id="datasetsTab">
                    <cadis:dataset-list-panel
                            datasets="${gateway:filterPublished(dataset.directlyNestedDatasets)}"/>
                </div> <!-- datasetsTab -->
            </c:if>

            <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')">
                <div class="tab-pane" id="adminTab">
                    <cadis:project-admin-tab-content dataset="${dataset}"/>
                </div> <!-- adminTab -->
            </authz:authorize>
        </div> <!-- .tab-content -->
        <br> <!-- visual whitespace -->

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
