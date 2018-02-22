import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustrialPlant } from './industrial-plant.model';
import { IndustrialPlantPopupService } from './industrial-plant-popup.service';
import { IndustrialPlantService } from './industrial-plant.service';

@Component({
    selector: 'up-industrial-plant-dialog',
    templateUrl: './industrial-plant-dialog.component.html'
})
export class IndustrialPlantDialogComponent implements OnInit {

    industrialPlant: IndustrialPlant;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private industrialPlantService: IndustrialPlantService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.industrialPlant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.industrialPlantService.update(this.industrialPlant));
        } else {
            this.subscribeToSaveResponse(
                this.industrialPlantService.create(this.industrialPlant));
        }
    }

    private subscribeToSaveResponse(result: Observable<IndustrialPlant>) {
        result.subscribe((res: IndustrialPlant) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: IndustrialPlant) {
        this.eventManager.broadcast({ name: 'industrialPlantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-industrial-plant-popup',
    template: ''
})
export class IndustrialPlantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industrialPlantPopupService: IndustrialPlantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.industrialPlantPopupService
                    .open(IndustrialPlantDialogComponent as Component, params['id']);
            } else {
                this.industrialPlantPopupService
                    .open(IndustrialPlantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
