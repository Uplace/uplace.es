import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustrialPlant } from './industrial-plant.model';
import { IndustrialPlantPopupService } from './industrial-plant-popup.service';
import { IndustrialPlantService } from './industrial-plant.service';

@Component({
    selector: 'up-industrial-plant-delete-dialog',
    templateUrl: './industrial-plant-delete-dialog.component.html'
})
export class IndustrialPlantDeleteDialogComponent {

    industrialPlant: IndustrialPlant;

    constructor(
        private industrialPlantService: IndustrialPlantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.industrialPlantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'industrialPlantListModification',
                content: 'Deleted an industrialPlant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-industrial-plant-delete-popup',
    template: ''
})
export class IndustrialPlantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industrialPlantPopupService: IndustrialPlantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.industrialPlantPopupService
                .open(IndustrialPlantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
