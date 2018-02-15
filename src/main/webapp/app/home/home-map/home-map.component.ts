import {Component, OnInit, ViewChild} from '@angular/core';
import {AgmMap} from "@agm/core";
import {MarkerService} from "../../entities/marker/marker.service";
import {MarkerModel} from "../../entities/marker/marker.model";

@Component({
  selector: 'up-home-map',
  templateUrl: './home-map.component.html',
    providers: [MarkerService]
})
export class HomeMapComponent implements OnInit {

    latitude = 51.678418;
    longitude = 7.809007;
    markers: MarkerModel[] = [];

    @ViewChild(AgmMap) agmMap: AgmMap;

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
      private markersService: MarkerService
  ) { }

  ngOnInit() {
      this.getUserLocation();

      this.markersService.query().subscribe((result) => {
          this.markers = result.json;
      });
  }

    private getUserLocation() {
        /// locate the user
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                this.latitude = position.coords.latitude;
                this.longitude = position.coords.longitude;
            });
        }
    }

}
