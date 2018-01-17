import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Office } from './office.model';
import { OfficeService } from './office.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-office',
    templateUrl: './office.component.html'
})
export class OfficeComponent implements OnInit, OnDestroy {
offices: Office[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private officeService: OfficeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.officeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.offices = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOffices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Office) {
        return item.id;
    }
    registerChangeInOffices() {
        this.eventSubscriber = this.eventManager.subscribe('officeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
