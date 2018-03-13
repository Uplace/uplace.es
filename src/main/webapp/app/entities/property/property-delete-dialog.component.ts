import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Property } from './property.model';
import { PropertyPopupService } from './property-popup.service';
import { PropertyService } from './property.service';

@Component({
    selector: 'up-property-delete-dialog',
    templateUrl: './property-delete-dialog.component.html'
})
export class PropertyDeleteDialogComponent {

    property: Property;

    constructor(
        private propertyService: PropertyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        /*this.propertyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'propertyListModification',
                content: 'Deleted an property'
            });
            this.activeModal.dismiss(true);
        });*/
    }
}

@Component({
    selector: 'up-property-delete-popup',
    template: ''
})
export class PropertyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private propertyPopupService: PropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.propertyPopupService
                .open(PropertyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
