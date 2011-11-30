var gmap;
   var markerBounds;
   
   //Initializes the map and performs a call to retrieve locations to be placed.
   function initialize()
   { 
	   	  var locationId = $("#city_map").attr("locid");

	   	 
		  var latlng = new google.maps.LatLng(-34.397, 150.644); 
		  var myOptions = {zoom: 8,center: latlng ,mapTypeId: google.maps.MapTypeId.ROADMAP}; 
	      gmap = new google.maps.Map(document.getElementById('city_map'), myOptions);
	      gmap.draggable = true;
	      gmap.panControl = false;
	      gmap.zoomControl = true;
	      gmap.scrollwheel = false;
	      gmap.disableDoubleClickZoom = true;
	      markerBounds = new google.maps.LatLngBounds();
	      
	      
		  //TODO: Need to put city as parameter to request
	      $.getJSON('/EventWebSite/GMEventLoc',{loc:locationId}, setEventLocations);
	    
	      gmap.fitBounds(markerBounds);

	   
   }
   
   //Callback for event location search, iterates through locations to produce markers.
   function setEventLocations(data)
	{
		for(var i=0; i < data.locations.length; i++)
		{
			var loc = data.locations[i];
			var name = loc.name;
			//get latitudde of location
			var lat = loc.lat;
			//get longitude of location
			var lng = loc.lng;
			//get id of location
			var id = loc.id + '';
			//add location to Google Maps
			addPlaces(name, lat, lng, id);
		}
		
	}
   
   //Adds event locations to the map by creating markers.
   function addPlaces(name,lat,lng,id)
   {
	   	  var infowindow = new google.maps.InfoWindow(); 
	   	  infowindow.setContent(name); 
	      var marker;      
	      
	    	  marker = new google.maps.Marker({ position: new google.maps.LatLng(lat,lng),map: gmap});
	    	  marker.EventID = id;
	    	  markerBounds.extend(new google.maps.LatLng(lat, lng));
	    	  
	    	  google.maps.event.addListener(marker, 'mouseover', (function(marker, id) {         
	    		  return function() {           
	    			      
	    			  infowindow.open(gmap, marker);         
	    			  };   
	    		  })(marker, id));
	    	  
	    	  google.maps.event.addListener(marker, 'mouseout', function() {
	    		    //setTimeout(function() { infowindow.close(); }, 3000);
	    		    infowindow.close(gmap, marker);
	    		});
	    	  
	    	  //TODO: redirect to the event page, Event id is in marker.EventID
	    	  google.maps.event.addListener(marker, 'click', function() {
	    		    var mainContent = $("#mainContent"); 
					mainContent.load("EventPage?eventID=" + marker.EventID);
	    		});
	    	  
	       
   }