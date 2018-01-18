import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Parking } from './parking.model';
import { ParkingService } from './parking.service';

@Component({
    selector: 'up-parking-detail',
    templateUrl: './parking-detail.component.html'
})
export class ParkingDetailComponent implements OnInit, OnDestroy {

    parking: Parking;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private parkingService: ParkingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParkings();
    }

    load(id) {
        this.parkingService.find(id).subscribe((parking) => {
            this.parking = parking;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParkings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parkingListModification',
            (response) => this.load(this.parking.id)
        );
    }
}
