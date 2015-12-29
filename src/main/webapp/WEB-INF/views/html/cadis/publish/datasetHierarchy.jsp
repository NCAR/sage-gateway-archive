<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Dataset Hierarchy"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Dataset Hierarchy"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <div class="panel panel-default">
            <div class="panel-heading">Hierarchy for Dataset: ${dataset.title}</div>
            <div class="panel-body">
                <c:url value="/dataset/${rootDataset.shortName}.html" var="datasetURL"></c:url>
                <ul>
                    <li>
                        <c:choose>
                            <c:when test="${rootDataset.shortName == dataset.shortName}">
                                <a href="${datasetURL}"
                                   style="font-size: large; color: blue; font-weight: bold;">${rootDataset.title}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${datasetURL}">${rootDataset.title}</a>
                            </c:otherwise>
                        </c:choose>

                        <br/>
				<span style="padding-left: 10px; font-size: 85%; color: grey;">
					Last Updated: <fmt:formatDate value="${rootDataset.dateUpdated}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</span>
                    </li>
                    <cadis:dataset-tree originalDataset="${dataset}" dataset="${rootDataset}"/>
                </ul>
            </div> <!-- .panel-body -->
        </div>

    </tiles:putAttribute>

</tiles:insertDefinition>
