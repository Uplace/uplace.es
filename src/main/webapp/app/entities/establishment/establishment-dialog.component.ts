import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Establishment } from './establishment.model';
import { EstablishmentPopupService } from './establishment-popup.service';
import { EstablishmentService } from './establishment.service';

@Component({
    selector: 'up-establishment-dialog',
    templateUrl: './establishment-dialog.component.html'
})
export class EstablishmentDialogComponent implements OnInit {

    establishment: Establishment;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private establishmentService: EstablishmentService,
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
        if (this.establishment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.establishmentService.update(this.establishment));
        } else {
            this.subscribeToSaveResponse(
                this.establishmentService.create(this.establishment));
        }
    }

    private subscribeToSaveResponse(result: Observable<Establishment>) {
        result.subscribe((res: Establishment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Establishment) {
        this.eventManager.broadcast({ name: 'establishmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-establishment-popup',
    template: ''
})
export class EstablishmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private establishmentPopupService: EstablishmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.establishmentPopupService
                    .open(EstablishmentDialogComponent as Component, params['id']);
            } else {
                this.establishmentPopupService
                    .open(EstablishmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
