import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RealEstate } from './real-estate.model';
import { RealEstatePopupService } from './real-estate-popup.service';
import { RealEstateService } from '../../admin/dashboard/real-estate/real-estate.service';

@Component({
    selector: 'up-real-estate-dialog',
    templateUrl: './real-estate-dialog.component.html'
})
export class RealEstateDialogComponent implements OnInit {

    realEstate: RealEstate;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private realEstateService: RealEstateService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.realEstate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.realEstateService.update(this.realEstate));
        } else {
            this.subscribeToSaveResponse(
                this.realEstateService.create(this.realEstate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RealEstate>>) {
        result.subscribe((res: HttpResponse<RealEstate>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RealEstate) {
        this.eventManager.broadcast({ name: 'realEstateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-real-estate-popup',
    template: ''
})
export class RealEstatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private realEstatePopupService: RealEstatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.realEstatePopupService
                    .open(RealEstateDialogComponent as Component, params['id']);
            } else {
                this.realEstatePopupService
                    .open(RealEstateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
