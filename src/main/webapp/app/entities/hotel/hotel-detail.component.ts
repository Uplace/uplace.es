import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Hotel } from './hotel.model';
import { HotelService } from './hotel.service';

@Component({
    selector: 'up-hotel-detail',
    templateUrl: './hotel-detail.component.html'
})
export class HotelDetailComponent implements OnInit, OnDestroy {

    hotel: Hotel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hotelService: HotelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHotels();
    }

    load(id) {
        this.hotelService.find(id).subscribe((hotel) => {
            this.hotel = hotel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHotels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hotelListModification',
            (response) => this.load(this.hotel.id)
        );
    }
}
