import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Terrain } from './terrain.model';
import { TerrainPopupService } from './terrain-popup.service';
import { TerrainService } from './terrain.service';

@Component({
    selector: 'up-terrain-delete-dialog',
    templateUrl: './terrain-delete-dialog.component.html'
})
export class TerrainDeleteDialogComponent {

    terrain: Terrain;

    constructor(
        private terrainService: TerrainService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.terrainService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'terrainListModification',
                content: 'Deleted an terrain'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-terrain-delete-popup',
    template: ''
})
export class TerrainDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private terrainPopupService: TerrainPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.terrainPopupService
                .open(TerrainDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
