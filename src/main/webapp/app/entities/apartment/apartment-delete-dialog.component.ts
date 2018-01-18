import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Apartment } from './apartment.model';
import { ApartmentPopupService } from './apartment-popup.service';
import { ApartmentService } from './apartment.service';

@Component({
    selector: 'up-apartment-delete-dialog',
    templateUrl: './apartment-delete-dialog.component.html'
})
export class ApartmentDeleteDialogComponent {

    apartment: Apartment;

    constructor(
        private apartmentService: ApartmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apartmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'apartmentListModification',
                content: 'Deleted an apartment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-apartment-delete-popup',
    template: ''
})
export class ApartmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apartmentPopupService: ApartmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.apartmentPopupService
                .open(ApartmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
