import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Business } from './business.model';
import { BusinessService } from './business.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-business',
    templateUrl: './business.component.html'
})
export class BusinessComponent implements OnInit, OnDestroy {
businesses: Business[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private businessService: BusinessService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.businessService.query().subscribe(
            (res: ResponseWrapper) => {
                this.businesses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBusinesses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Business) {
        return item.id;
    }
    registerChangeInBusinesses() {
        this.eventSubscriber = this.eventManager.subscribe('businessListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
