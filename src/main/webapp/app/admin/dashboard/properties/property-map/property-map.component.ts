import {Component, ElementRef, EventEmitter, Input, NgZone, OnInit, Output, ViewChild} from '@angular/core';
import {FormControl} from "@angular/forms";
import {AgmMap, MapsAPILoader} from "@agm/core";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";
import {} from '@types/googlemaps';
import {Location} from '../../../../entities/location';

@Component({
    selector: 'up-property-map',
    templateUrl: './property-map.component.html',
    styles: []
})

export class PropertyMapComponent implements OnInit {

    latitude = 41.390205;
    longitude = 2.154007;
    zoom = 14;

    @ViewChild(AgmMap) agmMap: AgmMap;

    @ViewChild("search") inputSearch: ElementRef;

    @Input() location: Location;

    @Output() locationChange: EventEmitter<Location> = new EventEmitter<Location>();

    constructor(
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService,
        private mapsAPILoader: MapsAPILoader,
        private ngZone: NgZone
    ) {
    }

    ngOnInit() {
        console.log(this.location);
        this.getUserLocation();
        this.mapsAPILoader.load().then(() => {
            let autocomplete = new google.maps.places.Autocomplete(this.inputSearch.nativeElement, {
                types: ["address"]
            });
            autocomplete.addListener("place_changed", () => {
                this.ngZone.run(() => {
                    //get the place result
                    let place: google.maps.places.PlaceResult = autocomplete.getPlace();

                    //verify result
                    if (place.geometry === undefined || place.geometry === null) {
                        return;
                    }

                    //set latitude, longitude and zoom
                    this.latitude = place.geometry.location.lat();
                    this.longitude = place.geometry.location.lng();
                    this.zoom = 12;
                });
            });
        });
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
            this.alertService.error('Cannot find user location :(');
        }
    }

    markerMoved(e) {
        this.latitude = e.coords.lat;
        this.longitude = e.coords.lng;
        this.geocodeLatLng();
    }

    geocodeLatLng() {
        let geocoder = new google.maps.Geocoder;
        let latlng = {lat: this.latitude, lng: this.longitude};
        geocoder.geocode({'location': latlng}, (results, status) => {
            if (status.toString() === 'OK') {
                if (results[0]) {
                    console.log(results[0].formatted_address);
                } else {
                    console.log('No results found');
                }
            } else {
                console.error('Geocoder failed due to: ' + status);
            }
        });
    }

}
