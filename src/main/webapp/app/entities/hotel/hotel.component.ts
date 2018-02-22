import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Hotel } from './hotel.model';
import { HotelService } from './hotel.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-hotel',
    templateUrl: './hotel.component.html'
})
export class HotelComponent implements OnInit, OnDestroy {
hotels: Hotel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hotelService: HotelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.hotelService.query().subscribe(
            (res: ResponseWrapper) => {
                this.hotels = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHotels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Hotel) {
        return item.id;
    }
    registerChangeInHotels() {
        this.eventSubscriber = this.eventManager.subscribe('hotelListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
