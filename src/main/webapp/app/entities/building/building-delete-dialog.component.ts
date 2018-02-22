import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Building } from './building.model';
import { BuildingPopupService } from './building-popup.service';
import { BuildingService } from './building.service';

@Component({
    selector: 'up-building-delete-dialog',
    templateUrl: './building-delete-dialog.component.html'
})
export class BuildingDeleteDialogComponent {

    building: Building;

    constructor(
        private buildingService: BuildingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.buildingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'buildingListModification',
                content: 'Deleted an building'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-building-delete-popup',
    template: ''
})
export class BuildingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private buildingPopupService: BuildingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.buildingPopupService
                .open(BuildingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
