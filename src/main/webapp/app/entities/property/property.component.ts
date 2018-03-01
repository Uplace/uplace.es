import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import {JhiEventManager, JhiAlertService, JhiDataUtils, JhiParseLinks} from 'ng-jhipster';

import { Property } from './property.model';
import { PropertyService } from './property.service';
import {ITEMS_PER_PAGE, Principal} from '../../shared';
import {Notification} from '../notification';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'up-property',
    templateUrl: './property.component.html'
})
export class PropertyComponent implements OnInit, OnDestroy {
    properties: Property[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: any;
    page: any;
    predicate: any;
    reverse: any;
    links: any;
    totalItems: any;
    queryCount: any;
    previousPage: any;
    routeData: any;

    constructor(
        private propertyService: PropertyService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private parseLinks: JhiParseLinks,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        /*this.propertyService.query().subscribe(
            (res: HttpResponse<Property[]>) => {
                if (this.properties.length === 0) {
                    this.jhiAlertService.info('error.noData');
                }
                console.log(this.properties);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );*/
        this.propertyService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: HttpResponse<Notification[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProperties();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.properties = data;
        console.log(this.properties);
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

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/properties'], {queryParams:
                {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        });
        this.loadAll();
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
}
