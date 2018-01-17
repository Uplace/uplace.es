import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bussiness } from './bussiness.model';
import { BussinessPopupService } from './bussiness-popup.service';
import { BussinessService } from './bussiness.service';

@Component({
    selector: 'up-bussiness-delete-dialog',
    templateUrl: './bussiness-delete-dialog.component.html'
})
export class BussinessDeleteDialogComponent {

    bussiness: Bussiness;

    constructor(
        private bussinessService: BussinessService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bussinessService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bussinessListModification',
                content: 'Deleted an bussiness'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-bussiness-delete-popup',
    template: ''
})
export class BussinessDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bussinessPopupService: BussinessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bussinessPopupService
                .open(BussinessDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
