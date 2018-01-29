import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Establishment } from './establishment.model';
import { EstablishmentService } from './establishment.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-establishment',
    templateUrl: './establishment.component.html'
})
export class EstablishmentComponent implements OnInit, OnDestroy {
establishments: Establishment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private establishmentService: EstablishmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.establishmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.establishments = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEstablishments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Establishment) {
        return item.id;
    }
    registerChangeInEstablishments() {
        this.eventSubscriber = this.eventManager.subscribe('establishmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
