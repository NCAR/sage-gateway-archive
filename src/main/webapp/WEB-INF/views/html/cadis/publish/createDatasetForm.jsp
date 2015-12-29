<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">
    <tiles:putAttribute type="string" name="title" value="Publish"/>
    <tiles:putAttribute type="string" name="pageTitle" value="Edit Metadata"/>

    <tiles:putAttribute name="body">

        <common:form-errors commandName="command"/>

        <c:if test="${showWizardComponent}">
            <div class="wizardTable">
                <div class="wizardRowGroup">
                    <div class="wizardCell">Step 1: Select Enclosing Container</div>
                    <div class="wizardCell">Step 2: Select Metadata Source</div>
                    <div class="wizardCell wizardSelected">Step 3: Edit Metadata</div>
                </div>
            </div>
        </c:if>
        <br>
        <div class="panel panel-default">
            <div class="panel-heading">Metadata for Collection</div>
            <div class="panel-body">
                <springForm:form method="post" commandName="command">

                    <p>
                        It is strongly recommended that you complete this form in less than 90 minutes.
                    </p>

                    <p class="small">
                        Required fields are marked with an asterisk <b>*</b>.
                        Click <span class="glyphicon glyphicon-info-sign"
                                        aria-hidden="true"></span>
                        for more information about a field.
                    </p>
                    <springForm:hidden value="${identifier}" path="identifier"/>

                    <div class="form-group">
                        <label for="title">
                            Title
                            <a href="<c:url value="/help/UserGuide.html#Title" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:input size="63" value="${title}"
                                          path="title" id="title" class="form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="shortName">
                            Short Name
                            <a href="<c:url value="/help/UserGuide.html#ShortName" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:input size="63" value="${shortName}"
                                          path="shortName" id="shortName" class="form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="description">
                            Description
                            <a href="<c:url value="/help/UserGuide.html#Description" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:textarea value="${description}" rows="5" cols="65"
                                          path="description" id="description" class="form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="locationIds">
                            Location Keyword(s)
                            <a href="<c:url value="/help/UserGuide.html#LocationKeyword" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:select path="locationIds" items="${locations}"
                                           itemLabel="name" itemValue="identifier" multiple="true"
                                           size="10" data-placeholder="Select Location Keywords"
                                           id="locationIds" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="platformTypeIds">
                            Platform Keyword(s)
                            <a href="<c:url value="/help/UserGuide.html#PlatformKeyword" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:select path="platformTypeIds" items="${platformTypes}"
                                           itemLabel="shortName" itemValue="identifier" multiple="true"
                                           size="10" data-placeholder="Select Platform Keywords"
                                           id="platformTypeIds" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="instrumentKeywordIds">
                            Instrument Name(s)
                            <a href="<c:url value="/help/UserGuide.html#InstrumentName" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                        </label>
                        <springForm:select path="instrumentKeywordIds" items="${instrumentKeywords}"
                                           itemLabel="displayText" itemValue="identifier" multiple="true"
                                           size="10" data-placeholder="Select Instrument Types"
                                           id="instrumentKeywordIds" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="scienceKeywordIds">
                            Science Keyword(s)
                            <a href="<c:url value="/help/UserGuide.html#ScienceKeyword" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:select path="scienceKeywordIds" items="${scienceKeywords}"
                                           itemLabel="displayText" itemValue="identifier" multiple="true"
                                           size="10" data-placeholder="Select Science Keywords"
                                           id="scienceKeywordIds" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="isoTopicIds">
                            ISO Topic(s)
                            <a href="<c:url value="/help/UserGuide.html#ISOTopic" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:select path="isoTopicIds" items="${isoTopics}"
                                           itemLabel="name" itemValue="identifier" multiple="true"
                                           data-placeholder="Select ISO Topics"
                                           id="isoTopicIds" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="distributionDataFormatIds">
                            Distribution Format(s)
                            <a href="<c:url value="/help/UserGuide.html#DistributionFormat" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                            *
                        </label>
                        <springForm:select path="distributionDataFormatIds" items="${distributionFormats}"
                                           itemLabel="name" itemValue="identifier" multiple="true"
                                           size="10" data-placeholder="Select Distribution Formats"
                                           id="distributionDataFormatIds" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="col-xs-4">
                        <div class="form-group">
                            <label for="northernLatitude">
                                Northernmost Latitude
                                <a href="<c:url value="/help/UserGuide.html#MinMaxLatLong" />" target="new">
                                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                </a>
                                *
                            </label>
                            <springForm:input onChange="showMapEdit();" onkeyup="this.onchange();"
                                              path="northernLatitude" id="northernLatitude" class="form-control"/>
                        </div> <!-- .form-group -->
                        <div class="form-group">
                            <label for="westernLongitude">
                                Westernmost Longitude
                                <a href="<c:url value="/help/UserGuide.html#MinMaxLatLong" />" target="new">
                                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                </a>
                                *
                            </label>
                            <springForm:input onChange="showMapEdit();" onkeyup="this.onchange();"
                                              path="westernLongitude" id="westernLongitude" class="form-control"/>
                        </div> <!-- .form-group -->
                    </div> <!-- .col-xs-4 -->
                    <div class="col-xs-4">
                        <div class="form-group">
                            <label for="southernLatitude">
                                Southernmost Latitude
                                <a href="<c:url value="/help/UserGuide.html#MinMaxLatLong" />" target="new">
                                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                </a>
                                *
                            </label>
                            <springForm:input onChange="showMapEdit();" onkeyup="this.onchange();"
                                              path="southernLatitude" id="southernLatitude" class="form-control"/>
                        </div> <!-- .form-group -->
                        <div class="form-group">
                            <label for="easternLongitude">
                                Easternmost Longitude
                                <a href="<c:url value="/help/UserGuide.html#MinMaxLatLong" />" target="new">
                                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                </a>
                                *
                            </label>
                            <springForm:input onChange="showMapEdit();" onkeyup="this.onchange();"
                                              path="easternLongitude" id="easternLongitude" class="form-control"/>
                        </div> <!-- .form-group -->
                    </div>

                    <div style="width:520px; height:252px; float:left; padding-bottom: 15px; padding-left: 10px;">
                        <div id="map-canvas-edit" style="width: 100%; height: 100%;"></div>
                    </div>
                    <div style="padding-top: 20px; clear: both;"></div>

                    <div class="row">
                        <div class="col-xs-4">
                            <div class="form-group">
                                <label for="datasetProgress">
                                    Progress
                                    <a href="<c:url value="/help/UserGuide.html#Progress" />" target="new">
                                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                    </a>
                                    *
                                </label>
                                <springForm:select path="datasetProgress" items="${datasetProgress}"
                                                   itemLabel="name"
                                                   id="datasetProgress" class="selectize-select form-control"/>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="startDate">
                                    Begin Date
                                    <a href="<c:url value="/help/UserGuide.html#BeginAndEndDate" />" target="new">
                                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                    </a>
                                </label>
                                <springForm:input path="startDate" id="startDate" class="form-control"/>
                                <div class="small">Format: YYYY-MM-DD</div>
                            </div> <!-- .form-group -->

                            <div class="form-group">
                                <label for="endDate">
                                    End Date
                                    <a href="<c:url value="/help/UserGuide.html#BeginAndEndDate" />" target="new">
                                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                    </a>
                                </label>
                                <springForm:input path="endDate" id="endDate" class="form-control"/>
                                <div class="small">Format: YYYY-MM-DD</div>
                            </div> <!-- .form-group -->
                        </div>
                    </div> <!-- .row -->

                    <div class="form-group">
                        <label for="timeFrequencyIds">
                            Frequency(ies)
                            <a href="<c:url value="/help/UserGuide.html#Frequency" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                        </label>
                        <springForm:select path="timeFrequencyIds" items="${frequencies}"
                                           itemLabel="name" itemValue="identifier" multiple="true"
                                           data-placeholder="Select Frequencies"
                                           id="timeFrequencyIds" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="spatialDataTypes">
                            Spatial Type(s)
                            <a href="<c:url value="/help/UserGuide.html#SpatialType" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                        </label>
                        <springForm:select path="spatialDataTypes" items="${spatialTypes}"
                                           itemLabel="longName" size="5"
                                           data-placeholder="Select Spatial Types"
                                           id="spatialDataTypes" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="form-group">
                        <label for="resolutionTypes">
                            Resolution(s)
                            <a href="<c:url value="/help/UserGuide.html#Resolution" />" target="new">
                                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                            </a>
                        </label>
                        <springForm:select path="resolutionTypes" items="${resolutions}"
                                           itemLabel="longName"
                                           data-placeholder="Select Resolutions"
                                           id="resolutionTypes" class="selectize-select form-control"/>
                    </div> <!-- .form-group -->

                    <div class="row">
                        <div class="col-xs-4">
                            <div class="form-group">
                                <label for="language">
                                    Dataset Language
                                    <a href="<c:url value="/help/UserGuide.html#DatasetLanguage" />" target="new">
                                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                                    </a>
                                    *
                                </label>
                                <springForm:input path="language" id="language" class="form-control"/>
                            </div> <!-- .form-group -->
                        </div>
                    </div> <!-- .row -->

                    <button type="submit" class="btn btn-default" id="save">Save Metadata</button>
                </springForm:form>
            </div> <!-- .panel-body -->
        </div>

        <%--Google Maps API --%>
        <script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false"></script>

        <%-- Map widget --%>
        <script type="text/javascript" src="<c:url value="/js/maps/showMapEdit.js"/>"></script>

        <script type="text/javascript">
            function init() {
                $('.selectize-select').selectize({
                    create: false,
                    hideSelected: true,
                    plugins: ['remove_button'],
                    maxOptions: 4000
                });
            } // init()

            google.maps.event.addDomListener(window, 'load', showMapEdit);

            window.setInterval(function () {
                $.get("<c:url value="/ping.html" />");
            }, 300000);

            // run the script after page load
            window.onload = init;
        </script>

    </tiles:putAttribute> <%-- body --%>
</tiles:insertDefinition>

