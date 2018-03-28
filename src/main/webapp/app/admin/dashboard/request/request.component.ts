import {Component, OnDestroy, OnInit} from '@angular/core';
import {RequestService} from "../../../shared/request/request.service";
import {Request} from "../../../shared/request/request.model";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {JhiAlertService, JhiEventManager, JhiParseLinks} from "ng-jhipster";
import {Subscription} from "rxjs/Subscription";
import {ActivatedRoute, Router} from "@angular/router";
import {ITEMS_PER_PAGE} from "../../../shared";

@Component({
    selector: 'up-request',
    templateUrl: './request.component.html',
    styles: []
})
export class RequestComponent implements OnInit, OnDestroy {

    eventSubscriber: Subscription;
    page: any;
    itemsPerPage: any;
    links: any;
    totalItems: any;
    queryCount: any;
    routeData: any;
    previousPage: any;
    reverse: any;
    predicate: any;

    requests: Request[] = [];

    constructor(
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private requestService: RequestService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.requestService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: ['date,desc']}).subscribe(
            (res: HttpResponse<Request[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/dashboard/request'], {queryParams:
                {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        });
        this.loadAll();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.requests = data;
        console.log(this.requests)
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

}
