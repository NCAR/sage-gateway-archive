function showMap() {
	
	var minLat = document.getElementById('southLat').innerHTML;
	var maxLat = document.getElementById('northLat').innerHTML;
	var minLong = document.getElementById('eastLong').innerHTML;
	var maxLong = document.getElementById('westLong').innerHTML;
	
//		alert ("minLat = " + minLat);
//		alert ("maxLat = " + maxLat);
//		alert ("minLong = " + minLong);
//		alert ("maxLong = " + maxLong);
		
	var avgLat = ((Number(minLat) + Number(maxLat))/2).toFixed(3);
	var avgLong = ((Number(minLong) + Number(maxLong))/2).toFixed(3);
	
	//center: new google.maps.LatLng(72.886436490787712, 10.2685546875),
	var centerLat = 66.0;
	var centerLong = 0.0;
	
	//alert ("centerLat = " + centerLat);
	//alert ("centerLong = " + centerLong);

	var mapDiv = document.getElementById('map-canvas');
	
	var centerPoint = new google.maps.LatLng(centerLat, centerLong);
	//var centerPoint = new google.maps.LatLng(56.064, -4.896);
	
	var mapOptions = {
			center: centerPoint,
			zoom: 0,
			mapTypeId: google.maps.MapTypeId.MAP,
			disableDefaultUI: true
			//streetViewControl: false
	};
	
	// Google maps only goes to 85.05115 N Lat
	var strictBounds = new google.maps.LatLngBounds(
		    new google.maps.LatLng(85, -180),           // top left corner of map
		    new google.maps.LatLng(-85, 180)            // bottom right corner
	);
	
	var datasetMap = new google.maps.Map(mapDiv, mapOptions);

	// Bounding Box points
	var paths = [
		    new google.maps.LatLng(minLat, minLong),
		    new google.maps.LatLng(maxLat, minLong),
		    new google.maps.LatLng(maxLat, maxLong),
		    new google.maps.LatLng(minLat, maxLong)
		    ];

	var shape = new google.maps.Polygon({
		    paths: paths,
		    strokeColor: '#ff0000',
		    strokeOpacity: 0.8,
		    strokeWeight: 2,
		    fillColor: '#ff0000',
		    fillOpacity: 0.35
		  });
		  	
	// Center of Map Point
//	var centerMarker = new google.maps.Marker({
//	            position: centerPoint,
//	           // map: map,
//	            title: "Center Marker"
//	        });
//	centerMarker.setMap(map);
	
    var avgpoint = new google.maps.LatLng(avgLat, avgLong);
    
    var latDiff = Math.abs(Number(maxLat)) - Math.abs(Number(minLat));
    var longDiff = Math.abs(Number(maxLong)) - Math.abs(Number(minLong));
    
    // Decide if point is needed as bb is too small to see on map
    if ((latDiff <= 3)  &&  (longDiff <= 3)) {
    	var showMarker = true;
    }
    
    if (showMarker == true ) {
    	
    	// Center of BBox 
        var marker = new google.maps.Marker({
                position: avgpoint,
                map: datasetMap,
                title: "Dataset Location"
        });	
    }
    
	shape.setMap(datasetMap);

}
