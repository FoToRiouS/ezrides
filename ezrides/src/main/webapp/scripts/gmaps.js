var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var map;

function initializeGMaps() {
  directionsDisplay = new google.maps.DirectionsRenderer();
  var initialLocation = new google.maps.LatLng(-16.323587, -48.9353974);
  var mapOptions = {
    zoom:4,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    center: initialLocation,
    streetViewControl: false
  };
  map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
  directionsDisplay.setMap(map);
}

function calcRoute(start, end) {
  var request = {
    origin: start,
    destination:end,
    travelMode: google.maps.TravelMode.DRIVING
  };
  directionsService.route(request, function(result, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setDirections(result);
    } 
  });
}

