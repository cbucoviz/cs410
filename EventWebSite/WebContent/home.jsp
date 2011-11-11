<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>    
	<title>Event Search</title>    
	<script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAA15VoHdx-aRwSchSg3DCmmBT8PMUw5z7_OLJoE1lh2VQyfb-WOxQRzxP2sRQf5GrkdoVIzL1QD02q3Q"> </script>    
	<script type="text/javascript">       
		var ge;
		
		
		google.load("earth", "1");        
		
		function init() {          
			google.earth.createInstance('map3d', initCB, failureCB);       
		}        
		
		function initCB(instance) {
			ge = instance;  
			ge.getWindow().setVisibility(true);  
			createPlaceMark();
		}        
		
		function codeAddress()
		{
			 var address ="Vancouver, B.C.";     
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
						ge.getFeatures().appendChild(placemark);
				 }
				 
			 });
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
	<div id="map3d">
		<script>
			init()
		</script>
	</div> 
</body> 
</html>