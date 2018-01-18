import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Terrain } from './terrain.model';
import { TerrainPopupService } from './terrain-popup.service';
import { TerrainService } from './terrain.service';

@Component({
    selector: 'up-terrain-dialog',
    templateUrl: './terrain-dialog.component.html'
})
export class TerrainDialogComponent implements OnInit {

    terrain: Terrain;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private terrainService: TerrainService,
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
        if (this.terrain.id !== undefined) {
            this.subscribeToSaveResponse(
                this.terrainService.update(this.terrain));
        } else {
            this.subscribeToSaveResponse(
                this.terrainService.create(this.terrain));
        }
    }

    private subscribeToSaveResponse(result: Observable<Terrain>) {
        result.subscribe((res: Terrain) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Terrain) {
        this.eventManager.broadcast({ name: 'terrainListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'up-terrain-popup',
    template: ''
})
export class TerrainPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private terrainPopupService: TerrainPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.terrainPopupService
                    .open(TerrainDialogComponent as Component, params['id']);
            } else {
                this.terrainPopupService
                    .open(TerrainDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
