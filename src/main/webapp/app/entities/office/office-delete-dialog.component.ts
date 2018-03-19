import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Office } from './office.model';
import { OfficePopupService } from './office-popup.service';
import { OfficeService } from './office.service';

@Component({
    selector: 'up-office-delete-dialog',
    templateUrl: './office-delete-dialog.component.html'
})
export class OfficeDeleteDialogComponent {

    office: Office;

    constructor(
        private officeService: OfficeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.officeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'officeListModification',
                content: 'Deleted an office'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-office-delete-popup',
    template: ''
})
export class OfficeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private officePopupService: OfficePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.officePopupService
                .open(OfficeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
