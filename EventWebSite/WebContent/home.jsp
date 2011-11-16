<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head> 
<title>Event Search</title> 
</head>
<body>      
	
	<script type="text/javascript" src="https://www.google.com/jsapi?key=ABQIAAAA15VoHdx-aRwSchSg3DCmmBTOoy231r4QEbSXFm-h85D_yMO-KRSCWK3Vov4vXJK9McDY97wQwWepmg"> </script>  
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	
	<script type="text/javascript">       
		var ge;
		google.load("earth", "1");        
		
		//Attempts to create the instance of google earth
		function init() 
		{  
			google.earth.createInstance('map3d', initCB, failureCB);       
		}         
		
		//Callback for successful creation of google earth instance
		function initCB(instance) {
			ge = instance;
			//Make the instance of google earth visible
			ge.getWindow().setVisibility(true); 
			//Get the locations that need to be placed on GE.
			$.getJSON('/EventWebSite/GElocations', setAddresses);
		}        
		
		//Callback for successful location look up
		function setAddresses(data)
		{
			for(var i=0; i < data.locations.length; i++)
			{
				var loc = data.locations[i];
				var name = loc.name;
				//get latitudde of location
				var lat = loc.lat;
				//get longitude of location
				var lng = loc.lng;
				//add location to GE
				addPlace(name,lat,lng);
			}
			
		}
		
		//Adds a location to GE and creates the associate balloon and event
		function addPlace(name,lat,lng)
		{
			var placemark = ge.createPlacemark(''); 
			placemark.setName(name);  
			// Define a custom icon. 
			var icon = ge.createIcon(''); 
			icon.setHref('http://maps.google.com/mapfiles/kml/paddle/red-circle.png'); 
			//create a new style 
			var style = ge.createStyle(''); 
			//apply the icon to the style 
			style.getIconStyle().setIcon(icon); 
			style.getIconStyle().setScale(5.0);
			//apply the style to the placemark  
			placemark.setStyleSelector(style); 
	
			// Set the placemark's location.   
			var point = ge.createPoint(''); 
			point.setLatitude(lat); 
			point.setLongitude(lng); 
			placemark.setGeometry(point);   
			
			//Event listener for when the placemark is clicked
			google.earth.addEventListener(placemark, 'click', function(event) {  
				// prevent the default balloon from popping up  
				event.preventDefault();    
				var balloon = ge.createHtmlStringBalloon('');  
				balloon.setFeature(event.getTarget());  
				balloon.setMaxWidth(300); 
				balloon.setBackgroundColor('#696969');
		        balloon.setForegroundColor('#0000ff'); 
				var temp = event.getTarget(); 
				var cName = temp.getName();
				var test = '<font color="#009900"><a href="http://localhost:8080/EventWebSite/citypage?city='+cName+'">Events in '+ cName + '</a></font>';
				balloon.setContentString(test);
				ge.setBalloon(balloon);});
			
			//add placemark to GE.
			ge.getFeatures().appendChild(placemark);
		
		}
		
		


		
		function failureCB(errorCode) 
		{       
			
		}        
		 
		
	</script>  

 
	<div id="map3d">
		<script>
			init();
		</script>
	</div> 
</body> 
</html>
