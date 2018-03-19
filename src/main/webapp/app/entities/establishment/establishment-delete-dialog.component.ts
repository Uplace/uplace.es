import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Establishment } from './establishment.model';
import { EstablishmentPopupService } from './establishment-popup.service';
import { EstablishmentService } from './establishment.service';

@Component({
    selector: 'up-establishment-delete-dialog',
    templateUrl: './establishment-delete-dialog.component.html'
})
export class EstablishmentDeleteDialogComponent {

    establishment: Establishment;

    constructor(
        private establishmentService: EstablishmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.establishmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'establishmentListModification',
                content: 'Deleted an establishment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-establishment-delete-popup',
    template: ''
})
export class EstablishmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private establishmentPopupService: EstablishmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.establishmentPopupService
                .open(EstablishmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
