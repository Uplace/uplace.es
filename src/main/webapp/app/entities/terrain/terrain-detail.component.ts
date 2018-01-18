import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Terrain } from './terrain.model';
import { TerrainService } from './terrain.service';

@Component({
    selector: 'up-terrain-detail',
    templateUrl: './terrain-detail.component.html'
})
export class TerrainDetailComponent implements OnInit, OnDestroy {

    terrain: Terrain;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private terrainService: TerrainService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTerrains();
    }

    load(id) {
        this.terrainService.find(id).subscribe((terrain) => {
            this.terrain = terrain;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTerrains() {
        this.eventSubscriber = this.eventManager.subscribe(
            'terrainListModification',
            (response) => this.load(this.terrain.id)
        );
    }
}
