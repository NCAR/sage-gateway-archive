<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="Contribute Data"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Contribute Data"/>

    <tiles:putAttribute name="body">

        <common:success-message message="${successMessage}"/>
        <common:info-message message="${infoMessage}"/>

        <c:choose>
            <c:when test="${!(empty writableDatasets)}">

                <div style="padding-bottom: 15px;">
                    <div style="display: table; width: 100%;">
                        <div style="display: table-row;">
                            <div style="display: table-cell;">
                                Create, Edit, and Upload Files to a Dataset.
                            </div>
                            <div style="display:table-cell; float: right;">
                                <a href="<c:url value="/help/UserGuide.html" />">User Guide Help</a>
                            </div>
                        </div>
                    </div>
                </div>
                <p>${fn:length(writableDatasets)} entries</p>

                <div style="padding-bottom: 20px;">
                    <form role="form">
                        <div class="btn-group" data-toggle="buttons">
                            <label class="btn btn-default active">
                                <input type="radio" name="datasetToggle" checked="checked">Show Projects Only
                            </label>
                            <label class="btn btn-default">
                                <input type="radio" name="datasetToggle">Show Datasets
                            </label>
                        </div>
                    </form>
                </div>

                <c:forEach var='nestedDataset' items="${writableDatasets}">

                    <div class="alternate_row" style="padding: 5px;">
                            ${nestedDataset.title}<br>
						<span style="padding-left: 0px; font-size: 85%; color: grey;">
							Last Updated: <fmt:formatDate value="${nestedDataset.dateUpdated}"
                                                          pattern="yyyy-MM-dd HH:mm:ss"/>
						</span>

                        <div style="font-size: 85%; color: grey;">
                            <div style="display: table;">
                                <div style="display: table-row;">
                                    <div style="display: table-cell;">
                                        Award Numbers:
                                    </div>
                                    <div style="display:table-cell;">
                                        <c:forEach var='award' items="${nestedDataset.awards}">
                                            <div style="padding-left: 10px;">
                                                <c:out value="${award.awardNumber}"/><br>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <c:url var="viewDataset" value="/project/${nestedDataset.rootParentDataset.shortName}.html"/>
                        <c:url var="editDataset"
                               value="/project/${nestedDataset.rootParentDataset.shortName}/form/edit.html"/>
                        <c:url var="createDataset"
                               value="/dataset/${nestedDataset.rootParentDataset.shortName}/createNewDataset1.html"/>
                        <div style="padding-top: 2px;">
                            <a href="${viewDataset}">View</a> <authz:authorize
                                access="hasAnyAuthority('group_Root_role_admin')">| <a
                                href="${editDataset}">Edit</a> </authz:authorize>| <a href="${createDataset}">Create New
                            Dataset </a>
                        </div>

                    </div>

                    <div style="padding-top: 7px">
                        <div class="more">
                            <cadis:contribute-data-tree originalDataset="${rootParentDataset}"
                                                        dataset="${nestedDataset}"/>
                        </div>
                    </div>
                </c:forEach>

            </c:when>
            <c:otherwise>
                <p>You are not authorized to create datasets under any projects.

                <p/>

                <p>Please <a href="mailto:support@aoncadis.org?subject=ACADIS data set access request.">contact user
                    support</a> to setup access.</p>
                <br/>
            </c:otherwise>
        </c:choose>

        <script type="text/javascript">
            function init() {
                // Start with all detail section divs hidden /
                $('div.more').hide();

                /*
                 * Show or hide the dataset list. change() used instead of click() to avoid
                 * problems when using keyboard navigation.
                 */
                $("input[name='datasetToggle']").change(function () {
                    $("div.more").toggle();
                });
            } // init()

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>
