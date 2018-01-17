import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parking } from './parking.model';
import { ParkingPopupService } from './parking-popup.service';
import { ParkingService } from './parking.service';

@Component({
    selector: 'up-parking-delete-dialog',
    templateUrl: './parking-delete-dialog.component.html'
})
export class ParkingDeleteDialogComponent {

    parking: Parking;

    constructor(
        private parkingService: ParkingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parkingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parkingListModification',
                content: 'Deleted an parking'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-parking-delete-popup',
    template: ''
})
export class ParkingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parkingPopupService: ParkingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parkingPopupService
                .open(ParkingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
