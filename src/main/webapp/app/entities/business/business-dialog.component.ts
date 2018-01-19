import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Business } from './business.model';
import { BusinessPopupService } from './business-popup.service';
import { BusinessService } from './business.service';

@Component({
    selector: 'up-business-dialog',
    templateUrl: './business-dialog.component.html'
})
export class BusinessDialogComponent implements OnInit {

    business: Business;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private businessService: BusinessService,
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
        if (this.business.id !== undefined) {
            this.subscribeToSaveResponse(
                this.businessService.update(this.business));
        } else {
            this.subscribeToSaveResponse(
                this.businessService.create(this.business));
        }
    }

    private subscribeToSaveResponse(result: Observable<Business>) {
        result.subscribe((res: Business) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Business) {
        this.eventManager.broadcast({ name: 'businessListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-business-popup',
    template: ''
})
export class BusinessPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private businessPopupService: BusinessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.businessPopupService
                    .open(BusinessDialogComponent as Component, params['id']);
            } else {
                this.businessPopupService
                    .open(BusinessDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
