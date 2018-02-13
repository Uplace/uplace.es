import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {HomeService} from '../home.service';
import {AgmMap} from '@agm/core';

@Component({
  selector: 'app-home-map',
  templateUrl: './home-map.component.html',
  styleUrls: ['./home-map.component.css']
})
export class HomeMapComponent implements OnInit, OnDestroy {

  latitude = 51.678418;
  longitude = 7.809007;
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

  constructor(private homeService: HomeService) {}

  ngOnInit() {

    // Subscription for the resize filter
    this.homeService.change.subscribe(() => {
      setTimeout(() => {
        this.agmMap.triggerResize().then(() => {
          (this.agmMap as any)._mapsWrapper.setCenter({lat: this.agmMap.latitude, lng: this.agmMap.longitude});
        });
      }, 500);
    });
    // Subscription for setting new address

    // TODO: FIX Google places
  }

  ngOnDestroy(): void {
    console.log('Destroyed');
  }

  onShowFilter() {
    this.homeService.toggle();
  }

}
