import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bussiness } from './bussiness.model';
import { BussinessService } from './bussiness.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-bussiness',
    templateUrl: './bussiness.component.html'
})
export class BussinessComponent implements OnInit, OnDestroy {
bussinesses: Bussiness[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bussinessService: BussinessService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bussinessService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bussinesses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBussinesses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Bussiness) {
        return item.id;
    }
    registerChangeInBussinesses() {
        this.eventSubscriber = this.eventManager.subscribe('bussinessListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
