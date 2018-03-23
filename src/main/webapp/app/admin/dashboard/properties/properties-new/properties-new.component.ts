import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Property, PropertyService, TransactionType} from "../../../../entities/property";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Location} from '../../../../entities/location/location.model';
import {Apartment} from "../../../../shared/model/apartment.model";
import {Select} from "../../../../shared/model/select.enum";
import {NgForm} from "@angular/forms";

@Component({
    selector: 'up-properties-new',
    templateUrl: './properties-new.component.html',
    styles: [`input.ng-invalid.ng-touched, textarea.ng-invalid.ng-touched {
        border: 1px solid red;
    }`],
    encapsulation: ViewEncapsulation.None
})
export class PropertiesNewComponent implements OnInit {

    isSaving: boolean;
    property: Property = new Property();
    propertyTypes = ['Apartment', 'Building', 'Business', 'Establishment', 'Hotel', 'IndustrialPlant', 'Office', 'Parking', 'Terrain'];
    Select: Select;
    @ViewChild('newPropertyForm') form: NgForm;

    constructor(private propertyService: PropertyService,
                private eventManager: JhiEventManager,
                private route: ActivatedRoute,
                private alertService: JhiAlertService,
                private router: Router) {
        this.property.propertyType = this.propertyTypes[0];
        this.property.transaction = TransactionType.RENT_BUY;
    }

    ngOnInit() {
        console.log(this.form);
        this.route.params.subscribe((params: Params) => {
            if (params['reference']) {
                this.propertyService.find(params['reference']).subscribe((result) => {
                    if (result.body.location == null) {
                        result.body.location = new Location();
                    }
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
        console.log(this.property);
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
        window.scrollTo({left: 0, top: 0, behavior: 'smooth'});
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Property>>) {
        result.subscribe((res: HttpResponse<Property>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Property) {
        this.eventManager.broadcast({name: 'propertyListModification', content: 'OK'});
        this.isSaving = true;
    }

    private onSaveError() {
        this.isSaving = false;
    }

}
