<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>    
	<title>Event Search</title>    
	<script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAA15VoHdx-aRwSchSg3DCmmBT8PMUw5z7_OLJoE1lh2VQyfb-WOxQRzxP2sRQf5GrkdoVIzL1QD02q3Q"> </script>  
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script type='text/javascript' src='http://code.jquery.com/jquery-1.6.4.js'></script>
	<script type="text/javascript">       
		var ge;
		
		google.load("earth", "1");        
		
		function init() {          
			
			google.earth.createInstance('test', initCB, failureCB);       
			
		}        
		
		function initCB(instance) {
			ge = instance;  
			ge.getWindow().setVisibility(true); 
			$.getJSON('/EventWebSite/GElocations', setAddresses);
			//createPlaceMark();
			//setAddresses();
		}        
		
		function setAddresses(data)
		{
			for(var i=0; i < data.locations.length; i++)
			{
				var loc = data.locations[i];
				var name = loc.name;
				var lat = loc.lat;
				var lng = loc.lng;
				addPlace(name,lat,lng);
			}
			
		}
		
		/*function setAddresses()
		{
			//var address ='Vancouver'; 
			 var locations = new Array();
			 locations[0] = "New York";
			 locations[1] = "Toronto";
			 var i = 0;
			 for(i=0; i< locations.length ; i++)
			{ 
	                  currentProviderAddress = locations[i]; 
	                  place(currentProviderAddress,i); 
			}
		}*/
		
		function addPlace(name,lat,lng)
		{
			var placemark = ge.createPlacemark(name); 
			placemark.setName("Events in " + name);  
			// Define a custom icon. 
			var icon = ge.createIcon(''); 
			icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png'); 
			var style = ge.createStyle(''); 
			//create a new style 
			style.getIconStyle().setIcon(icon); 
			style.getIconStyle().setScale(5.0);
			//apply the icon to the style 
			placemark.setStyleSelector(style); 
			//apply the style to the placemark  
			// Set the placemark's location.   
			var point = ge.createPoint(''); 
			//var latitude = results[0].geometry.location.lat();     
			//var longitude = results[0].geometry.location.lng(); 
			point.setLatitude(lat); 
			point.setLongitude(lng); 
			placemark.setGeometry(point);   
			
			google.earth.addEventListener(placemark, 'click', function(event) {  
				// prevent the default balloon from popping up  
				event.preventDefault();    
				var balloon = ge.createHtmlStringBalloon('');  
				balloon.setFeature(event.getTarget());  
				balloon.setMaxWidth(300); 
				var temp = event.getTarget(); 
				var test = '<a href="http://localhost:8080/EventWebSite/citypage.jsp">'+ temp.getName() + '</a>';
				balloon.setContentString(test);
				//balloon.setContentString('<a href="http://www.w3schools.com/"> placemark </a> ');
				//balloon.setContentString(' <object width="200" height="150"><param name="movie" ' + 'value="http://www.youtube.com/v/6mrG_bsqC6k&hl=en&fs=1"/>'     + '<param name="allowFullScreen" value="true"/>'     + '<embed src="http://www.youtube.com/v/6mrG_bsqC6k&hl=en&fs=1" '     + 'type="application/x-shockwave-flash" allowfullscreen="true" '     + 'width="200" height="150"></embed></object>');    
				ge.setBalloon(balloon);});
			
			
			ge.getFeatures().appendChild(placemark);
		
		}
		
		function place(address,i)
		{
			try
			{
			 var geo = new google.maps.Geocoder();
			 geo.geocode({'address': address},function(results,status)
			 {
				 if (status == google.maps.GeocoderStatus.OK && results.length > 0) 
				 {
					 
				 	 
					 var placemark = ge.createPlacemark(' '); 
						placemark.setName("Events in " + results[0].formatted_address);  
						// Define a custom icon. 
						var icon = ge.createIcon(''); 
						icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png'); 
						var style = ge.createStyle(''); 
						//create a new style 
						style.getIconStyle().setIcon(icon); 
						style.getIconStyle().setScale(3.0);
						//apply the icon to the style 
						placemark.setStyleSelector(style); 
						//apply the style to the placemark  
						// Set the placemark's location.   
						var point = ge.createPoint(''); 
						var latitude = results[0].geometry.location.lat();     
						var longitude = results[0].geometry.location.lng(); 
						point.setLatitude(latitude); 
						point.setLongitude(longitude); 
						placemark.setGeometry(point);   
						
						google.earth.addEventListener(placemark, 'click', function(event) {  
							// prevent the default balloon from popping up  
							event.preventDefault();    
							var balloon = ge.createHtmlStringBalloon('');  
							balloon.setFeature(event.getTarget());  
							balloon.setMaxWidth(300);    // Google logo.  
							
							balloon.setContentString(' <object width="200" height="150"><param name="movie" ' + 'value="http://www.youtube.com/v/6mrG_bsqC6k&hl=en&fs=1"/>'     + '<param name="allowFullScreen" value="true"/>'     + '<embed src="http://www.youtube.com/v/6mrG_bsqC6k&hl=en&fs=1" '     + 'type="application/x-shockwave-flash" allowfullscreen="true" '     + 'width="200" height="150"></embed></object>');    
							ge.setBalloon(balloon);});
						
						
						ge.getFeatures().appendChild(placemark);
				 }
				 else
				 {
					 alert("Geocode was not successful for the following reason: " + status);
				 }
			 });
			}
			catch(err)
			{
				alert("Geocode was not successful for the following reason: " + err);
			}
			
			 /*
			 geocoder.geocode( { 'address': address}, function(results, status) { 
				 if (status == google.maps.GeocoderStatus.OK) {
					 var placemark = ge.createPlacemark(' '); 
						placemark.setName("Events in" + address);  
						// Define a custom icon. 
						var icon = ge.createIcon(''); 
						icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png'); 
						var style = ge.createStyle(''); 
						//create a new style 
						style.getIconStyle().setIcon(icon); 
						style.getIconStyle().setScale(3.0);
						//apply the icon to the style 
						placemark.setStyleSelector(style); 
						//apply the style to the placemark  
						// Set the placemark's location.   
						var point = ge.createPoint(''); 
						point.setLatitude(49.286007); 
						point.setLongitude(-123.116878); 
						placemark.setGeometry(point);  // Add the placemark to Earth. 
						ge.getFeatures().appendChild(placemark);*/
		}
				 
			
		
		function createPlaceMark()
		{
			// Create the placemark. 
			var placemark = ge.createPlacemark(' '); 
			placemark.setName("Events in Vancouver!!");  
			// Define a custom icon. 
			var icon = ge.createIcon(''); 
			icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png'); 
			var style = ge.createStyle(''); 
			//create a new style 
			style.getIconStyle().setIcon(icon); 
			style.getIconStyle().setScale(3.0);
			//apply the icon to the style 
			placemark.setStyleSelector(style); 
			//apply the style to the placemark  
			// Set the placemark's location.   
			var point = ge.createPoint(''); 
			point.setLatitude(49.286007); 
			point.setLongitude(-123.116878); 
			placemark.setGeometry(point);  // Add the placemark to Earth. 
			ge.getFeatures().appendChild(placemark);
			
		}
		
		function failureCB(errorCode) {       
			
		}        
		google.setOnLoadCallback(init);    
		
	</script>  
</head> 
<body>    
	<div id="test" style="height: 400px; width: 600px;"></div> 
</body> 
</html>

