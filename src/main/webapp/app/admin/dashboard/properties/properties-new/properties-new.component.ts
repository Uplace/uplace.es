import {Component, OnInit} from '@angular/core';
import {Property, PropertyService} from "../../../../entities/property";
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
    property: Property = new Property();

    constructor(
        private propertyService: PropertyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
    }

    onSubmit(event) {
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
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Property>>) {
        result.subscribe((res: HttpResponse<Property>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Property) {
        console.log("OK!");
        console.log(result);
        this.eventManager.broadcast({ name: 'propertyListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

}
