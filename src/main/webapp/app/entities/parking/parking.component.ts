import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Parking } from './parking.model';
import { ParkingService } from './parking.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-parking',
    templateUrl: './parking.component.html'
})
export class ParkingComponent implements OnInit, OnDestroy {
parkings: Parking[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private parkingService: ParkingService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.parkingService.query().subscribe(
            (res: ResponseWrapper) => {
                this.parkings = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInParkings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Parking) {
        return item.id;
    }
    registerChangeInParkings() {
        this.eventSubscriber = this.eventManager.subscribe('parkingListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
