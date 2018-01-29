import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IndustrialPlant } from './industrial-plant.model';
import { IndustrialPlantService } from './industrial-plant.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-industrial-plant',
    templateUrl: './industrial-plant.component.html'
})
export class IndustrialPlantComponent implements OnInit, OnDestroy {
industrialPlants: IndustrialPlant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private industrialPlantService: IndustrialPlantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.industrialPlantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.industrialPlants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIndustrialPlants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IndustrialPlant) {
        return item.id;
    }
    registerChangeInIndustrialPlants() {
        this.eventSubscriber = this.eventManager.subscribe('industrialPlantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
