import {Component, OnInit, ViewChild} from '@angular/core';
import {Property, PropertyService, TransactionType} from "../../../../entities/property";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";
import {AgmMap} from "@agm/core";
import {Location} from "../../../../entities/location/location.model";
import {google} from "@agm/core/services/google-maps-types";

@Component({
    selector: 'up-properties-new',
    templateUrl: './properties-new.component.html',
    styles: []
})
export class PropertiesNewComponent implements OnInit {

    isSaving: boolean;
    property: Property;
    propertyTypes = ['Apartment','Building','Business','Establishment', 'Hotel', 'IndustrialPlant', 'Office', 'Parking', 'Terrain'];
    transactionTypes = TransactionType;
    latitude = 41.390205;
    longitude = 2.154007;

    @ViewChild(AgmMap) agmMap: AgmMap;


    constructor(
        private propertyService: PropertyService,
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService
    ) {
        this.property = new Property();
        this.property.propertyType = this.propertyTypes[0];
        this.property.transaction = TransactionType.RENT_BUY;
    }

    ngOnInit() {
        this.getUserLocation();
        console.log(this.property);
    }

    onSubmit() {
        this.save();
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

    markerMoved(e) {
        this.latitude = e.coords.lat;
        this.longitude = e.coords.lng;
    }

    save() {
        document.body.scrollTop = 0;
        this.isSaving = true;
        if (this.property.id !== undefined) {
            this.subscribeToSaveResponse(
                this.propertyService.update(this.property));
        } else {
            this.subscribeToSaveResponse(
                this.propertyService.create(this.property));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Property>>) {
        result.subscribe((res: HttpResponse<Property>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Property) {
        this.eventManager.broadcast({ name: 'propertyListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

}
