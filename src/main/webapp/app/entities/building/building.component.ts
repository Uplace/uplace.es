import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Building } from './building.model';
import { BuildingService } from './building.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-building',
    templateUrl: './building.component.html'
})
export class BuildingComponent implements OnInit, OnDestroy {
buildings: Building[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private buildingService: BuildingService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.buildingService.query().subscribe(
            (res: ResponseWrapper) => {
                this.buildings = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBuildings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Building) {
        return item.id;
    }
    registerChangeInBuildings() {
        this.eventSubscriber = this.eventManager.subscribe('buildingListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
