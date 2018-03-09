import {Component, OnInit} from '@angular/core';
import {Property, PropertyService, TransactionType} from "../../../../entities/property";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {JhiEventManager} from "ng-jhipster";

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


    constructor(
        private propertyService: PropertyService,
        private eventManager: JhiEventManager
    ) {
        this.property = new Property();
        this.property.propertyType = this.propertyTypes[0];
        this.property.transaction = TransactionType.RENT_BUY;
    }

    ngOnInit() {}

    onSubmit() {
        console.log(this.property);
        this.save();
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
