import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Apartment } from './apartment.model';
import { ApartmentService } from './apartment.service';

@Component({
    selector: 'up-apartment-detail',
    templateUrl: './apartment-detail.component.html'
})
export class ApartmentDetailComponent implements OnInit, OnDestroy {

    apartment: Apartment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private apartmentService: ApartmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInApartments();
    }

    load(id) {
        this.apartmentService.find(id)
            .subscribe((apartmentResponse: HttpResponse<Apartment>) => {
                this.apartment = apartmentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInApartments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'apartmentListModification',
            (response) => this.load(this.apartment.id)
        );
    }
}
