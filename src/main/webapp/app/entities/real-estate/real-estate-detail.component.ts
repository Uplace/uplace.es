import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RealEstate } from './real-estate.model';
import { RealEstateService } from '../../admin/dashboard/real-estate/real-estate.service';

@Component({
    selector: 'up-real-estate-detail',
    templateUrl: './real-estate-detail.component.html'
})
export class RealEstateDetailComponent implements OnInit, OnDestroy {

    realEstate: RealEstate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private realEstateService: RealEstateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRealEstates();
    }

    load(id) {
        this.realEstateService.find(id)
            .subscribe((realEstateResponse: HttpResponse<RealEstate>) => {
                this.realEstate = realEstateResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRealEstates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'realEstateListModification',
            (response) => this.load(this.realEstate.id)
        );
    }
}
