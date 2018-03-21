import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RealEstate } from './real-estate.model';
import { RealEstateService } from '../../admin/dashboard/real-estate/real-estate.service';
import { Principal } from '../../shared';

@Component({
    selector: 'up-real-estate',
    templateUrl: './real-estate.component.html'
})
export class RealEstateComponent implements OnInit, OnDestroy {
realEstates: RealEstate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private realEstateService: RealEstateService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.realEstateService.query().subscribe(
            (res: HttpResponse<RealEstate[]>) => {
                this.realEstates = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRealEstates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RealEstate) {
        return item.id;
    }
    registerChangeInRealEstates() {
        this.eventSubscriber = this.eventManager.subscribe('realEstateListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
