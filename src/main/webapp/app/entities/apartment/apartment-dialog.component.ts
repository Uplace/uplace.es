import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Apartment } from './apartment.model';
import { ApartmentPopupService } from './apartment-popup.service';
import { ApartmentService } from './apartment.service';

@Component({
    selector: 'up-apartment-dialog',
    templateUrl: './apartment-dialog.component.html'
})
export class ApartmentDialogComponent implements OnInit {

    apartment: Apartment;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private apartmentService: ApartmentService,
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
        if (this.apartment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.apartmentService.update(this.apartment));
        } else {
            this.subscribeToSaveResponse(
                this.apartmentService.create(this.apartment));
        }
    }

    private subscribeToSaveResponse(result: Observable<Apartment>) {
        result.subscribe((res: Apartment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Apartment) {
        this.eventManager.broadcast({ name: 'apartmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-apartment-popup',
    template: ''
})
export class ApartmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apartmentPopupService: ApartmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.apartmentPopupService
                    .open(ApartmentDialogComponent as Component, params['id']);
            } else {
                this.apartmentPopupService
                    .open(ApartmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
