<%@ include file="/WEB-INF/views/html/common/include.jsp" %>

<tiles:insertDefinition name="general-layout">

    <tiles:putAttribute type="string" name="title" value="${dataset.title}"/>
    <tiles:putAttribute type="string" name="pageTitle" value="${dataset.containerType}"/>

    <tiles:putAttribute name="links">
        <link rel="self" type="text/html" href="<c:url value="/dataset/id/${dataset.identifier}.html"/>"/>
        <link rel="self" type="text/dif" href="<c:url value="/dataset/id/${dataset.identifier}.dif"/>"/>
        <link rel="self" type="text/iso19139" href="<c:url value="/dataset/id/${dataset.identifier}.iso19139"/>"/>
        <link rel="self" type="text/plain" href="<c:url value="/dataset/id/${dataset.identifier}.txt"/>"/>
    </tiles:putAttribute>

    <tiles:putAttribute name="body">
        <common:success-message message="${successMessage}"/>
        <common:info-message message="${infoMessage}"/>

        <div class="dataset_parent_hierarchy">
            <gateway:datasetParentHierarchy parentList="${parentList}"/>
        </div>

        <div class="dataset_name">
            <h1>${dataset.title}</h1>
        </div>

        <c:if test="${dataset.retracted}">
            <div class="retracted_dataset">
                <span>COLLECTION RETRACTED</span>
            </div>
        </c:if>

        <ul class="nav nav-tabs">
            <li class="active" role="presentation"><a href="#summaryTab" data-toggle="tab"><em>Metadata</em></a></li>

            <c:if test="${dataset.downloadable}">
                <li role="presentation"><a href="#filesTab" data-toggle="tab"><em>Download Data</em></a></li>
            </c:if>

            <c:if test="${not empty dataset.currentDatasetVersion.variables }">
                <li role="presentation"><a href="#variablesTab" data-toggle="tab"><em>Variables</em></a></li>
            </c:if>

            <authz:authorize access="hasAnyAuthority('${dataset.writeGrantingAuthorities}')">
                <li role="presentation"><a href="#adminTab" data-toggle="tab"><em>Edit</em></a></li>
            </authz:authorize>
        </ul>

        <div class="tab-content">
            <div class="tab-pane active" id="summaryTab">
                <cadis:dataset-summary-tab-content dataset="${dataset}" discipline="${param.topicId}"
                                                   pi="${param.contactId}" aonProject="${param.activityId}"/>
            </div>

            <c:if test="${dataset.downloadable}">
                <div class="tab-pane" id="filesTab">
                    <cadis:dataset-files-tab-content dataset="${dataset}"/>
                </div>
            </c:if>

            <authz:authorize
                    access="!hasAnyAuthority('group_Root_role_admin') and hasAnyAuthority('${dataset.writeGrantingAuthorities}')">
                <c:if test="${dataset.containerType == 'DATASET'}">
                    <div class="tab-pane" id="adminTab">
                        <cadis:dataset-admin-tab-content dataset="${dataset}"/>
                    </div>
                </c:if>
                <c:if test="${dataset.containerType == 'PROJECT'}">
                    <div class="tab-pane" id="adminTab">
                        You are not authorized to edit projects.
                    </div>
                </c:if>
            </authz:authorize>
            <authz:authorize access="hasAnyAuthority('group_Root_role_admin')">
                <div class="tab-pane" id="adminTab">
                    <cadis:dataset-admin-tab-content dataset="${dataset}"/>
                </div>
            </authz:authorize>
        </div> <!-- .tab-content -->
        <br> <!-- visual whitespace -->

        <%--Google Maps API --%>
        <script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false"></script>

        <script type="text/javascript">

            <c:if test="${not empty dataset.descriptiveMetadata.geographicBoundingBox }">

            function showMap() {

                var minLat = ${dataset.descriptiveMetadata.geographicBoundingBox.southBoundLatitude};
                var maxLat = ${dataset.descriptiveMetadata.geographicBoundingBox.northBoundLatitude};
                var maxLong = ${dataset.descriptiveMetadata.geographicBoundingBox.eastBoundLongitude};
                var minLong = ${dataset.descriptiveMetadata.geographicBoundingBox.westBoundLongitude};

                var avgLat = ((Number(minLat) + Number(maxLat)) / 2).toFixed(3);
                var avgLong = ((Number(minLong) + Number(maxLong)) / 2).toFixed(3);

                var centerLat = 66.0;
                var centerLong = 0.0;

                var mapDiv = document.getElementById('map-canvas');

                var centerPoint = new google.maps.LatLng(centerLat, centerLong);

                var mapOptions = {
                    center: centerPoint,
                    zoom: 0,
                    mapTypeId: google.maps.MapTypeId.MAP,
                    disableDefaultUI: true
                };

                var datasetMap = new google.maps.Map(mapDiv, mapOptions);

                var shape = new google.maps.Rectangle({
                    bounds: new google.maps.LatLngBounds(
                            new google.maps.LatLng(minLat, minLong),
                            new google.maps.LatLng(maxLat, maxLong)),
                    strokeColor: '#ff0000',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#ff0000',
                    fillOpacity: 0.35
                });

                var avgpoint = new google.maps.LatLng(avgLat, avgLong);

                var latDiff = Math.abs(Number(maxLat)) - Math.abs(Number(minLat));
                var longDiff = Math.abs(Number(maxLong)) - Math.abs(Number(minLong));

                // Decide if point is needed as bb is too small to see on map
                if ((latDiff <= 3) && (longDiff <= 3)) {
                    var showMarker = true;
                }

                if (showMarker == true) {

                    // Center of BBox
                    var marker = new google.maps.Marker({
                        position: avgpoint,
                        map: datasetMap,
                        title: "Dataset Location"
                    });
                }

                shape.setMap(datasetMap);
            } // showMap()
            </c:if>

            google.maps.event.addDomListener(window, 'load', showMap);

        </script>

    </tiles:putAttribute> <%-- body --%>

</tiles:insertDefinition>


