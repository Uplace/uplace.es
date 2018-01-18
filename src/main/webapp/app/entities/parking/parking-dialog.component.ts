import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parking } from './parking.model';
import { ParkingPopupService } from './parking-popup.service';
import { ParkingService } from './parking.service';

@Component({
    selector: 'up-parking-dialog',
    templateUrl: './parking-dialog.component.html'
})
export class ParkingDialogComponent implements OnInit {

    parking: Parking;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private parkingService: ParkingService,
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
        if (this.parking.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parkingService.update(this.parking));
        } else {
            this.subscribeToSaveResponse(
                this.parkingService.create(this.parking));
        }
    }

    private subscribeToSaveResponse(result: Observable<Parking>) {
        result.subscribe((res: Parking) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Parking) {
        this.eventManager.broadcast({ name: 'parkingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-parking-popup',
    template: ''
})
export class ParkingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parkingPopupService: ParkingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parkingPopupService
                    .open(ParkingDialogComponent as Component, params['id']);
            } else {
                this.parkingPopupService
                    .open(ParkingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
