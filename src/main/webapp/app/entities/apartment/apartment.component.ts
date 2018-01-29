import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Apartment } from './apartment.model';
import { ApartmentService } from './apartment.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-apartment',
    templateUrl: './apartment.component.html'
})
export class ApartmentComponent implements OnInit, OnDestroy {
apartments: Apartment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private apartmentService: ApartmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.apartmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.apartments = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInApartments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Apartment) {
        return item.id;
    }
    registerChangeInApartments() {
        this.eventSubscriber = this.eventManager.subscribe('apartmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
