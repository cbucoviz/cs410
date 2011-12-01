<!DOCTYPE html>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>Geocoder</title>

<script src="https://maps.googleapis.com/maps/api/js?v=3&sensor=false"type="text/javascript"></script>
 	
<script type="text/javascript">
  var geocoder;

  function codeAddress(street,city,state,country) {
	geocoder = new google.maps.Geocoder();
    var address = street + ", "+city + ", "+ state+", " + country;
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
      		var lat =  results[0].geometry.location.lat();
      		var lng =  results[0].geometry.location.lng();
      		$.post('/EventWebSite/Locations',{newLat: lat,newLong:lng,city:city,state:state,country:country}, function(data) {
      			
      			return this.data;
      		},'json');
      		
        
      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
  }
</script>
</head>

</html>
