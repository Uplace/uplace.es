import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Property } from './property.model';
import { PropertyService } from './property.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-property',
    templateUrl: './property.component.html'
})
export class PropertyComponent implements OnInit, OnDestroy {
properties: Property[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private propertyService: PropertyService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.propertyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.properties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProperties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Property) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInProperties() {
        this.eventSubscriber = this.eventManager.subscribe('propertyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}