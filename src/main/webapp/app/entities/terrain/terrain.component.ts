import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Terrain } from './terrain.model';
import { TerrainService } from './terrain.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-terrain',
    templateUrl: './terrain.component.html'
})
export class TerrainComponent implements OnInit, OnDestroy {
terrains: Terrain[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private terrainService: TerrainService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.terrainService.query().subscribe(
            (res: ResponseWrapper) => {
                this.terrains = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTerrains();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Terrain) {
        return item.id;
    }
    registerChangeInTerrains() {
        this.eventSubscriber = this.eventManager.subscribe('terrainListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
