<!DOCTYPE html>
<html> 
<head> 
   <meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
   <title>Google Maps getBoundsZoomLevel Demo</title> 
   <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false" 
           type="text/javascript"></script> 
</head> 
<body onunload="GUnload()"> 

   <div id="map" style="width: 800px; height: 600px"></div> 

   <script type="text/javascript"> 

   if (GBrowserIsCompatible()) {
      var map = new GMap2(document.getElementById("map"));
      var markerBounds = new GLatLngBounds();

      for (var i = 0; i < 10; i++) {
         var randomPoint = new GLatLng( 39.00 + (Math.random() - 0.5) * 20, 
                                       -77.00 + (Math.random() - 0.5) * 20);

         map.addOverlay(new GMarker(randomPoint));
         markerBounds.extend(randomPoint);
      }

      map.setCenter(markerBounds.getCenter(), 
                    map.getBoundsZoomLevel(markerBounds));
   }
   </script> 
</body> 
</html>
