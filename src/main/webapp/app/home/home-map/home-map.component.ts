import {Component, OnInit, ViewChild} from '@angular/core';
import {MarkerService} from "../../entities/marker/marker.service";
import {JhiAlertService} from "ng-jhipster";
import {HttpClient} from "@angular/common/http";

declare const google: any;
declare const InfoBox: any;
declare const MarkerClusterer: any;
declare const RichMarker: any;

@Component({
  selector: 'up-home-map',
  templateUrl: './home-map.component.html',
    providers: [MarkerService]
})
export class HomeMapComponent implements OnInit {

    latitude = 47.6098;
    longitude = -122.2247;
    mapType: "roadmap" | "hybrid" | "satellite" | "terrain" = 'roadmap';
    mapZoom: number = 14;
    mapFullScreen: boolean = false;

    markers: any[] = [];
    map: any;
    bound: any;
    zoom: number = 13;

    mapStyles = [
        {
            'featureType': 'administrative',
            'elementType': 'labels.text.fill',
            'stylers': [{'color': '#c6c6c6'}]
        },
        {'featureType': 'landscape', 'elementType': 'all', 'stylers': [{'color': '#f2f2f2'}]}, {
            'featureType': 'poi',
            'elementType': 'all',
            'stylers': [{'visibility': 'off'}]
        },
        {'featureType': 'road', 'elementType': 'all', 'stylers': [{'saturation': -100}, {'lightness': 45}]}, {
            'featureType': 'road.highway',
            'elementType': 'all',
            'stylers': [{'visibility': 'simplified'}]
        },
        {'featureType': 'road.highway', 'elementType': 'geometry.fill', 'stylers': [{'color': '#ffffff'}]}, {
            'featureType': 'road.arterial',
            'elementType': 'labels.icon',
            'stylers': [{'visibility': 'off'}]
        },
        {'featureType': 'transit', 'elementType': 'all', 'stylers': [{'visibility': 'off'}]}, {
            'featureType': 'water',
            'elementType': 'all',
            'stylers': [{'color': '#dde6e8'}, {'visibility': 'on'}]}
    ];

  constructor(
      private markersService: MarkerService,
      private alertService: JhiAlertService,
      private http: HttpClient
  ) { }

  ngOnInit() {

      // Inits the map
      this.initMap();

      // Inits the infobox
      var infobox = new InfoBox({
          content: 'empty',
          disableAutoPan: false,
          maxWidth: 0,
          pixelOffset: new google.maps.Size(-250, -330),
          zIndex: null,
          closeBoxURL: '',
          infoBoxClearance: new google.maps.Size(1, 1),
          isHidden: false,
          isOpen: false,
          pane: 'floatPane',
          enableEventPropagation: false
      });

      // Adds a listener for closing
      infobox.addListener('domready', () => {
          $('.infobox-close').on('click', () => {
              infobox.close(this.map, this);
              infobox.isOpen = false;
          });
      });

      // Gets the marker from a service
      this.http.get('http://localhost:8080/api/markers').subscribe((data: any[]) => {

          // For each marker sets the content and configures the infobox
          data.forEach((marker) => {

              const markerCenter = new google.maps.LatLng(marker['latitude'], marker['longitude']);

              const markerContent =
                  '<div class="marker">' +
                  '<div class="marker-inner">' +
                  '<span class="marker-image" style="background-image: url(' + marker['thumbnail'] + ');"></span>' +
                  '</div>' +
                  '<div class="marker-verified"><i class="fa fa-check"></i></div>' +
                  '<div class="marker-price">1000</div>' +
                  '</div>';

              // Creates the RichMarker and sets the content
              const richMarker = new RichMarker({
                  id: marker['reference'],
                  data: 'Im data',
                  flat: true,
                  position: markerCenter,
                  shadow: 0,
                  content: markerContent
              });

              this.markers.push(richMarker);

              google.maps.event.addListener(richMarker, 'click', function () {
                  var c = '<div class="infobox"><div class="infobox-close"><i class="fa fa-close"></i></div>' +
                      '<h3 class="infobox-title"><a href="listing.html">This is a infobox</a></h3>' +
                      '<h4 class="infobox-address">Calle de la piruleta</h4>' +
                      '<div class="infobox-content">' +
                      '<div class="infobox-image"><ul><li><a href="#"><i class="fa fa-facebook"></i></a></li><li><a href="#"><i class="fa fa-twitter"></i></a></li><li><a href="#"><i class="fa fa-google"></i></a></li></ul></div>' +
                      '<div class="infobox-body"><div class="infobox-body-inner"><div class="infobox-price">$119.90</div><div class="infobox-category tag">Restaurant</div><p><strong>Class aptent taciti sociosqu ad litora torquent per conubia nostra.</strong></p><p>Etiam vehicula nisi sem, a volutpat diam lacinia eu. Vivamus lorem est, eleifend et urna sed.</p></div>' +
                      '<div class="infobox-more"><a href="#">Read More <i class="fa fa-chevron-right"></i></a></div>' +
                      '</div>' +
                      '<div>';

                  if (!infobox.isOpen) {
                      infobox.setContent(c);
                      infobox.open(this.map, this);
                      infobox.isOpen = true;
                      infobox.markerId = marker.id;
                  } else {
                      if (infobox.markerId === marker.id) {
                          infobox.close(this.map, this);
                          infobox.isOpen = false;
                      } else {
                          infobox.close(this.map, this);
                          infobox.isOpen = false;

                          infobox.setContent(c);
                          infobox.open(this.map, this);
                          infobox.isOpen = true;
                          infobox.markerId = marker.id;
                      }
                  }
              });

          });
          new MarkerClusterer(this.map, this.markers, {
              styles: [
                  {
                      url: 'assets/img/cluster.png',
                      height: 36,
                      width: 36
                  }
              ]
          });

      });
  }

    initMap() {
        this.map = new google.maps.Map(document.getElementById('map-object'), {
            zoom: this.zoom,
            center: new google.maps.LatLng(47.5883, -122.303),
            scrollwheel: false,
            mapTypeControl: false,
            streetViewControl: false,
            zoomControl: false,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            styles: [{
                'featureType': 'water',
                'elementType': 'geometry',
                'stylers': [{'color': '#e9e9e9'}, {'lightness': 17}]
            }, {
                'featureType': 'landscape',
                'elementType': 'geometry',
                'stylers': [{'color': '#f5f5f5'}, {'lightness': 20}]
            }, {
                'featureType': 'road.highway',
                'elementType': 'geometry.fill',
                'stylers': [{'color': '#ffffff'}, {'lightness': 17}]
            }, {
                'featureType': 'road.highway',
                'elementType': 'geometry.stroke',
                'stylers': [{'color': '#ffffff'}, {'lightness': 29}, {'weight': 0.2}]
            }, {
                'featureType': 'road.arterial',
                'elementType': 'geometry',
                'stylers': [{'color': '#ffffff'}, {'lightness': 18}]
            }, {
                'featureType': 'road.local',
                'elementType': 'geometry',
                'stylers': [{'color': '#ffffff'}, {'lightness': 16}]
            }, {
                'featureType': 'poi',
                'elementType': 'geometry',
                'stylers': [{'color': '#f5f5f5'}, {'lightness': 21}]
            }, {
                'featureType': 'poi.park',
                'elementType': 'geometry',
                'stylers': [{'color': '#dedede'}, {'lightness': 21}]
            }, {
                'elementType': 'labels.text.stroke',
                'stylers': [{'visibility': 'on'}, {'color': '#ffffff'}, {'lightness': 16}]
            }, {
                'elementType': 'labels.text.fill',
                'stylers': [{'saturation': 36}, {'color': '#333333'}, {'lightness': 40}]
            }, {'elementType': 'labels.icon', 'stylers': [{'visibility': 'off'}]}, {
                'featureType': 'transit',
                'elementType': 'geometry',
                'stylers': [{'color': '#f2f2f2'}, {'lightness': 19}]
            }, {
                'featureType': 'administrative',
                'elementType': 'geometry.fill',
                'stylers': [{'color': '#fefefe'}, {'lightness': 20}]
            }, {
                'featureType': 'administrative',
                'elementType': 'geometry.stroke',
                'stylers': [{'color': '#fefefe'}, {'lightness': 17}, {'weight': 1.2}]
            }]
        });
    }

    /*getUserLocation() {
        // locate the user
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                this.latitude = position.coords.latitude;
                this.longitude = position.coords.longitude;
                this.agmMap.triggerResize().then(() =>  (this.agmMap as any)._mapsWrapper.setCenter({lat: this.latitude, lng: this.longitude}));
                console.log('Location of user found!');
            });
        } else {
            this.alertService.error('Cannot find your location :(');
        }
    }*/
    actionZoomIn() {
        this.map.setZoom(this.map.getZoom() + 1);
    }

    actionZoomOut() {
        this.map.setZoom(this.map.getZoom() - 1);
    }

    actionType(type: string) {
        if (type === 'HYBRID') {
            this.map.setMapTypeId(google.maps.MapTypeId.HYBRID);
        } else if (type === 'SATELLITE') {
            this.map.setMapTypeId(google.maps.MapTypeId.SATELLITE);
        } else if (type === 'TERRAIN') {
            this.map.setMapTypeId(google.maps.MapTypeId.TERRAIN);
        } else if (type === 'ROADMAP') {
            this.map.setMapTypeId(google.maps.MapTypeId.ROADMAP);
        }
    }

}
