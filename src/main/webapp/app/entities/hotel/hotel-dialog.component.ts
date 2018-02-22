import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Hotel } from './hotel.model';
import { HotelPopupService } from './hotel-popup.service';
import { HotelService } from './hotel.service';

@Component({
    selector: 'up-hotel-dialog',
    templateUrl: './hotel-dialog.component.html'
})
export class HotelDialogComponent implements OnInit {

    hotel: Hotel;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private hotelService: HotelService,
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
        if (this.hotel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hotelService.update(this.hotel));
        } else {
            this.subscribeToSaveResponse(
                this.hotelService.create(this.hotel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Hotel>>) {
        result.subscribe((res: HttpResponse<Hotel>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Hotel) {
        this.eventManager.broadcast({ name: 'hotelListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-hotel-popup',
    template: ''
})
export class HotelPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hotelPopupService: HotelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hotelPopupService
                    .open(HotelDialogComponent as Component, params['id']);
            } else {
                this.hotelPopupService
                    .open(HotelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
