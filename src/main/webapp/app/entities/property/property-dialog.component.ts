import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Property } from './property.model';
import { PropertyPopupService } from './property-popup.service';
import { PropertyService } from './property.service';
import { Location, LocationService } from '../location';
import { Agent, AgentService } from '../agent';

@Component({
    selector: 'up-property-dialog',
    templateUrl: './property-dialog.component.html'
})
export class PropertyDialogComponent implements OnInit {

    property: Property;
    isSaving: boolean;

    locations: Location[];

    agents: Agent[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private propertyService: PropertyService,
        private locationService: LocationService,
        private agentService: AgentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService
            .query({filter: 'property(reference)-is-null'})
            .subscribe((res: HttpResponse<Location[]>) => {
                if (!this.property.location || !this.property.location.id) {
                    this.locations = res.body;
                } else {
                    this.locationService
                        .find(this.property.location.id)
                        .subscribe((subRes: HttpResponse<Location>) => {
                            this.locations = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.agentService.query()
            .subscribe((res: HttpResponse<Agent[]>) => { this.agents = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
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
        this.eventManager.broadcast({ name: 'propertyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }

    trackAgentById(index: number, item: Agent) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'up-property-popup',
    template: ''
})
export class PropertyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private propertyPopupService: PropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.propertyPopupService
                    .open(PropertyDialogComponent as Component, params['id']);
            } else {
                this.propertyPopupService
                    .open(PropertyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
