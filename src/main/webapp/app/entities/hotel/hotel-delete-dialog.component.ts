import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Hotel } from './hotel.model';
import { HotelPopupService } from './hotel-popup.service';
import { HotelService } from './hotel.service';

@Component({
    selector: 'up-hotel-delete-dialog',
    templateUrl: './hotel-delete-dialog.component.html'
})
export class HotelDeleteDialogComponent {

    hotel: Hotel;

    constructor(
        private hotelService: HotelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hotelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hotelListModification',
                content: 'Deleted an hotel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-hotel-delete-popup',
    template: ''
})
export class HotelDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hotelPopupService: HotelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hotelPopupService
                .open(HotelDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
