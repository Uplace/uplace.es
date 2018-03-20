import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RealEstate } from './real-estate.model';
import { RealEstatePopupService } from './real-estate-popup.service';
import { RealEstateService } from './real-estate.service';

@Component({
    selector: 'up-real-estate-delete-dialog',
    templateUrl: './real-estate-delete-dialog.component.html'
})
export class RealEstateDeleteDialogComponent {

    realEstate: RealEstate;

    constructor(
        private realEstateService: RealEstateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.realEstateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'realEstateListModification',
                content: 'Deleted an realEstate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-real-estate-delete-popup',
    template: ''
})
export class RealEstateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private realEstatePopupService: RealEstatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.realEstatePopupService
                .open(RealEstateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
