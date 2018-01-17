import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bussiness } from './bussiness.model';
import { BussinessPopupService } from './bussiness-popup.service';
import { BussinessService } from './bussiness.service';

@Component({
    selector: 'up-bussiness-dialog',
    templateUrl: './bussiness-dialog.component.html'
})
export class BussinessDialogComponent implements OnInit {

    bussiness: Bussiness;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private bussinessService: BussinessService,
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
        if (this.bussiness.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bussinessService.update(this.bussiness));
        } else {
            this.subscribeToSaveResponse(
                this.bussinessService.create(this.bussiness));
        }
    }

    private subscribeToSaveResponse(result: Observable<Bussiness>) {
        result.subscribe((res: Bussiness) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Bussiness) {
        this.eventManager.broadcast({ name: 'bussinessListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-bussiness-popup',
    template: ''
})
export class BussinessPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bussinessPopupService: BussinessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bussinessPopupService
                    .open(BussinessDialogComponent as Component, params['id']);
            } else {
                this.bussinessPopupService
                    .open(BussinessDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
