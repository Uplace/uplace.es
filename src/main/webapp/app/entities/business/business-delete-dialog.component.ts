import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Business } from './business.model';
import { BusinessPopupService } from './business-popup.service';
import { BusinessService } from './business.service';

@Component({
    selector: 'up-business-delete-dialog',
    templateUrl: './business-delete-dialog.component.html'
})
export class BusinessDeleteDialogComponent {

    business: Business;

    constructor(
        private businessService: BusinessService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.businessService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'businessListModification',
                content: 'Deleted an business'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-business-delete-popup',
    template: ''
})
export class BusinessDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private businessPopupService: BusinessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.businessPopupService
                .open(BusinessDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
