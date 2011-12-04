var gEMap;
var markerBoundsEvent;
   
   //Initializes the map and performs a call to retrieve locations to be placed.
  
   
   function initializeEMap()
   { 
	   	  var locationId = $("#event_map").attr("locid");
	   	  var keyword = $("#event_map").attr("title");
	   	 // alert("test");
	   	 
		  var latlng = new google.maps.LatLng(-34.397, 150.644); 
		  var myOptions = {zoom: 8,center: latlng ,mapTypeId: google.maps.MapTypeId.ROADMAP};
		  
		  gEMap = new google.maps.Map(document.getElementById('event_map'), myOptions);
		  gEMap.draggable = true;
		  gEMap.panControl = false;
		  gEMap.zoomControl = true;
		  gEMap.scrollwheel = false;
		  gEMap.disableDoubleClickZoom = true;
		  markerBoundsEvent = new google.maps.LatLngBounds();
	      
	      
		  //TODO: Need to put city as parameter to request
	      
		  $.ajax({
			  url: 'GMEventLoc',
			  async: false,
			  data: {loc:locationId,keyword:keyword},
			  dataType: 'json',
			  success: function (json) 
			  {
				  setEventMapLocations(json);
			  }
			});
		  
	      gEMap.fitBounds(markerBoundsEvent);
   }
   
   //Callback for event location search, iterates through locations to produce markers.
   function setEventMapLocations(data)
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
			addEventPlaces(name, lat, lng, id);
		}
		
	}
   
   //Adds event locations to the map by creating markers.
   function addEventPlaces(name,lat,lng,id)
   {
	   	  var infowindow = new google.maps.InfoWindow(); 
	   	  infowindow.setContent(name); 
	      var marker;      
	      
	    	  marker = new google.maps.Marker({ position: new google.maps.LatLng(lat,lng),map: gEMap});
	    	  marker.EventID = id;
	    	  markerBoundsEvent.extend(new google.maps.LatLng(lat, lng));
	    	  
	    	  google.maps.event.addListener(marker, 'mouseover', (function(marker, id) {         
	    		  return function() {           
	    			      
	    			  infowindow.open(gEMap, marker);         
	    			  };   
	    		  })(marker, id));
	    	  
	    	  google.maps.event.addListener(marker, 'mouseout', function() {
	    		    //setTimeout(function() { infowindow.close(); }, 3000);
	    		    infowindow.close(gEMap, marker);
	    		});
	    	  
	    	  //TODO: redirect to the event page, Event id is in marker.EventID
	    	  google.maps.event.addListener(marker, 'click', function() {
	    		 
	    		    var mainContent = $("#mainContent"); 
					mainContent.load("EventPage?eventID=" + marker.EventID);
	    		});
	    	  
	       
   }