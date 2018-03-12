import {Component, OnInit} from '@angular/core';
import {Property, PropertyService, TransactionType} from "../../../../entities/property";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
    selector: 'up-properties-new',
    templateUrl: './properties-new.component.html',
    styles: []
})
export class PropertiesNewComponent implements OnInit {

    isSaving: boolean;
    property: Property;
    propertyTypes = ['Apartment', 'Building', 'Business', 'Establishment', 'Hotel', 'IndustrialPlant', 'Office', 'Parking', 'Terrain'];
    transactionTypes = TransactionType;


    constructor(
        private propertyService: PropertyService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private alertService: JhiAlertService,
        private router: Router
    ) {
        this.property = new Property();
        this.property.propertyType = this.propertyTypes[0];
        this.property.transaction = TransactionType.RENT_BUY;
    }

    ngOnInit() {
       this.route.params.subscribe((params: Params) => {
            if (params['reference']) {
               this.propertyService.find(params['reference']).subscribe((result) => {
                   this.property = result.body;
               }, error => {
                   this.alertService.error(error.message, null, null);
                   this.router.navigate(['/dashboard/properties']);
               });
            }
       });
    }

    onSubmit() {
        this.save();
    }

    save() {
        this.isSaving = true;
        if (this.property.id !== undefined) {
            this.subscribeToSaveResponse(
                this.propertyService.update(this.property));
        } else {
            this.subscribeToSaveResponse(
                this.propertyService.create(this.property));
        }
        document.body.scrollTop = 0;
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
