import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Office } from './office.model';
import { OfficePopupService } from './office-popup.service';
import { OfficeService } from './office.service';

@Component({
    selector: 'up-office-dialog',
    templateUrl: './office-dialog.component.html'
})
export class OfficeDialogComponent implements OnInit {

    office: Office;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private officeService: OfficeService,
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
        if (this.office.id !== undefined) {
            this.subscribeToSaveResponse(
                this.officeService.update(this.office));
        } else {
            this.subscribeToSaveResponse(
                this.officeService.create(this.office));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Office>>) {
        result.subscribe((res: HttpResponse<Office>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Office) {
        this.eventManager.broadcast({ name: 'officeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-office-popup',
    template: ''
})
export class OfficePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private officePopupService: OfficePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.officePopupService
                    .open(OfficeDialogComponent as Component, params['id']);
            } else {
                this.officePopupService
                    .open(OfficeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
