import {Component, ElementRef, EventEmitter, Input, NgZone, OnInit, Output, ViewChild} from '@angular/core';
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

    defaultLatitude = 41.390205;
    defaultLongitude = 2.154007;
    defaultZoom = 14;

    @ViewChild(AgmMap) agmMap: AgmMap;

    @ViewChild("search") inputSearch: ElementRef;

    @Input() location: Location;

    @Output() locationChange: EventEmitter<Location> = new EventEmitter<Location>();

    constructor(
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService,
        private mapsAPILoader: MapsAPILoader,
        private ngZone: NgZone
    ) { }

    ngOnInit() {
        this.mapsAPILoader.load().then(() => {
            this.initPlaces();
            this.getUserLocation();
        });
    }

    initPlaces() {

        let autocomplete = new google.maps.places.Autocomplete(this.inputSearch.nativeElement, {
            types: ["address"]
        });

        autocomplete.addListener("place_changed", () => {
            this.ngZone.run(() => {
                // get the place result
                let place: google.maps.places.PlaceResult = autocomplete.getPlace();

                // verify result
                if (place.geometry === undefined || place.geometry === null) {
                    return;
                }

                // set latitude, longitude and zoom
                this.location.latitude = place.geometry.location.lat();
                this.location.longitude = place.geometry.location.lng();
            });
        });
    }

    getUserLocation() {
        // locate the user
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position) => {
                this.location.latitude = position.coords.latitude;
                this.location.longitude = position.coords.longitude;
                this.setMapCenter(this.location.latitude, this.location.longitude);
                console.log('Location of user found!');
                this.geocodeLatLng();
            });
        } else {
            this.alertService.error('Cannot find user location :(');
        }
    }

    setMapCenter(latitude: number, longitude: number) {
        this.agmMap.triggerResize().then(() =>  (this.agmMap as any)._mapsWrapper.setCenter({lat: latitude, lng: longitude}));
    }

    markerMoved(e) {
        this.location.latitude = e.coords.lat;
        this.location.longitude = e.coords.lng;
        this.geocodeLatLng();
    }

    geocodeLatLng() {
        let geocoder = new google.maps.Geocoder;
        let latlng = {lat: this.location.latitude, lng: this.location.longitude};
        geocoder.geocode({'location': latlng}, (results, status) => {
            if (status.toString() === 'OK') {
                if (results[0]) {
                    this.location.fullAddress = results[0].formatted_address;
                    this.location.city = results[0].address_components[2].long_name;
                    this.location.postalCode = results[0].address_components[6].long_name;
                } else {
                    console.log('No results found');
                }
            } else {
                console.error('Geocoder failed due to: ' + status);
            }
        });
    }

}
