<script src="https://maps.googleapis.com/maps/api/js?v=3&sensor=false"type="text/javascript"></script>
 	
<script type="text/javascript">
  var geocoder;

  function codeAddressForEventCreation(form) 
  {
		var street = form.find("input[name='address']").val();
		var city = form.find("input[name='city']").val();
		var state = form.find("input[name='state']").val();
		var country = form.find("input[name='country']").val();

		geocoder = new google.maps.Geocoder();
    	var address = street + ", "+city + ", "+ state+", " + country;
    
    
    	geocoder.geocode( { 'address': address}, 
    		function(results, status) 
    		{
      			if (status == google.maps.GeocoderStatus.OK) 
      			{
      				var lat = results[0].geometry.location.lat();
      				var lng = results[0].geometry.location.lng();
      				var params = {newLat: lat, newLong:lng, city:city, state:state, country:country};
      				
      				var dataMap = {};
      				
      				$.ajax({
      				  url: 'Locations',
      				  async: false,
      				  data: params,
      				  dataType: 'json',
      				  type: 'POST',
      				  success: function (json) 
      				  {
      					  $.each(json, function(key, val) {
      						  	dataMap[key] = val;
      						  });
      				  }
      				});

				// we now have the information for our location
				var formParam = form.serialize();
				var locationParam = $.param(dataMap);
				var finalParam = formParam + "&" + locationParam;
				
				$.post(form.attr("action"), finalParam, function(data){$("#mainContent").html(data);});	
				
      		} 
      		else 
      		{
        		alert("We were unable to determine the address of your event, please check your spelling.");
      		}
    });
    
  }
  
  function codeAddressForUserCreation(form) 
  {
		var city = form.find("input[name='city']").val();
		var state = form.find("input[name='state']").val();
		var country = form.find("input[name='country']").val();

		geocoder = new google.maps.Geocoder();
    	var address = city + ", "+ state+", " + country;
    
    
    	geocoder.geocode( { 'address': address}, 
    		function(results, status) 
    		{
      			if (status == google.maps.GeocoderStatus.OK) 
      			{
      				var lat = results[0].geometry.location.lat();
      				var lng = results[0].geometry.location.lng();
      				var params = {newLat: lat, newLong:lng, city:city, state:state, country:country};
      				var dataMap = {};
      				
      				$.ajax({
      				  url: 'Locations',
      				  async: false,
      				  data: params,
      				  dataType: 'json',
      				  type: 'POST',
      				  success: function (json) 
      				  {
      					  $.each(json, function(key, val) {
      						  	dataMap[key] = val;
      						  });
      				  }
      				});

				// we now have the information for our location
				var formParam = form.serialize();
				$.post(form.attr("action"), formParam, function(data){$("#mainContent").html(data);});					
      		} 
      		else 
      		{
        		alert("We were unable to determine your address, please check your spelling.");
      		}
    });
    
  }
</script>
