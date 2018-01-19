import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Business } from './business.model';
import { BusinessService } from './business.service';

@Component({
    selector: 'up-business-detail',
    templateUrl: './business-detail.component.html'
})
export class BusinessDetailComponent implements OnInit, OnDestroy {

    business: Business;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private businessService: BusinessService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBusinesses();
    }

    load(id) {
        this.businessService.find(id).subscribe((business) => {
            this.business = business;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBusinesses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'businessListModification',
            (response) => this.load(this.business.id)
        );
    }
}
