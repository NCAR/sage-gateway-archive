function showMapEdit() {
	
	//center: new google.maps.LatLng(66, 0),
	var centerLat = 66.0;
	var centerLong = 0.0;
	
	var maxLat = document.getElementById('northernLatitude').value;
	var minLat = document.getElementById('southernLatitude').value;
	var minLong = document.getElementById('westernLongitude').value;
	var maxLong = document.getElementById('easternLongitude').value;
	
//		alert ("minLat = " + minLat);
//		alert ("maxLat = " + maxLat);
//		alert ("minLong = " + minLong);
//		alert ("maxLong = " + maxLong);
		

	//alert ("centerLat = " + centerLat);
	//alert ("centerLong = " + centerLong);

	var mapDiv = document.getElementById('map-canvas-edit');
	
	var centerPoint = new google.maps.LatLng(centerLat, centerLong);
	//var centerPoint = new google.maps.LatLng(56.064, -4.896);
	
	var mapOptions = {
			center: centerPoint,
			zoom: 1,
			mapTypeId: google.maps.MapTypeId.MAP,
			//disableDefaultUI: true
			streetViewControl: false
	};
	
	var datasetMap = new google.maps.Map(mapDiv, mapOptions);

	// Bounding Box points
//	var paths = [
//		    new google.maps.LatLng(minLat, minLong),
//		    new google.maps.LatLng(maxLat, minLong),
//		    new google.maps.LatLng(maxLat, maxLong),
//		    new google.maps.LatLng(minLat, maxLong)
//		    ];

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
		  	
	// Center of Map Point
//	var centerMarker = new google.maps.Marker({
//	            position: centerPoint,
//	           // map: map,
//	            title: "Center Marker"
//	        });
//	centerMarker.setMap(map);
	
	var avgLat = ((Number(minLat) + Number(maxLat))/2).toFixed(3);
	var avgLong = ((Number(minLong) + Number(maxLong))/2).toFixed(3);
	
    var avgpoint = new google.maps.LatLng(avgLat, avgLong);
    
    var latDiff = Math.abs(Number(maxLat)) - Math.abs(Number(minLat));
    var longDiff = Math.abs(Number(maxLong)) - Math.abs(Number(minLong));
    
   // alert ("latDiff=" + latDiff);
   // alert ("longDiff=" + longDiff);
    
    var showMarker = false;
    
    // Decide if point is needed as bb is too small to see on map
    if ((latDiff <= 3)  &&  (longDiff <= 3)) {
    	showMarker = true;
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
