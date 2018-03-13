import {Component, OnInit, ViewChild} from '@angular/core';
import {AgmMap} from "@agm/core";
import {MarkerService} from "../../entities/marker/marker.service";
import {Marker} from "../../entities/marker/marker.model";
import {JhiAlertService} from "ng-jhipster";
import {FilterService} from '../../entities/filter/filter.service';
import {Filter} from '../../entities/filter/filter.model';
import {HttpResponse} from "@angular/common/http";

@Component({
    selector: 'up-home-map',
    templateUrl: './home-map.component.html',
    providers: []
})
export class HomeMapComponent implements OnInit {

    latitude = 41.390205;
    longitude = 2.154007;
    markers: Marker[] = [];
    filters: Filter = {};
    mapType: 'roadmap' | 'hybrid' | 'satellite' | 'terrain' = 'roadmap';
    mapZoom: number = 14;
    mapFullScreen: boolean = false;

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
        private markersService: MarkerService,
        private alertService: JhiAlertService,
        private filterService: FilterService
    ) { }

    ngOnInit() {
        this.getUserLocation();

        this.markersService.query().subscribe(
            (res: HttpResponse<Marker[]>) => {
                this.markers = res.body;
            }
        );
        this.filterService.query().subscribe(
            (res: HttpResponse<Filter>) => {
                this.filters = res.body;
            }
        );

        /**
         * Update for Angular 4.3
         * */
        /*this.markersService.query().subscribe((result) => {
            this.markers = result.json;
        });

        this.filterService.query().subscribe((filter) => {
            this.filters = filter.json;
        });*/
    }

    getUserLocation() {
        // locate the user
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position) => {
                this.latitude = position.coords.latitude;
                this.longitude = position.coords.longitude;
                this.agmMap.triggerResize().then(() =>  (this.agmMap as any)._mapsWrapper.setCenter({lat: this.latitude, lng: this.longitude}));
                console.log('Location of user found!');
            });
        } else {
            this.alertService.error('Cannot find your location :(');
        }
    }

}
