import { Component, OnInit } from '@angular/core';
import {PropertyService} from "../../../entities/property";
import {Notification} from "../../../entities/notification";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Property} from "../../../entities/property/property.model";
import {Subscription} from "rxjs/Subscription";
import {JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks} from "ng-jhipster";
import {ActivatedRoute, Router} from "@angular/router";
import {ITEMS_PER_PAGE, Principal} from "../../../shared";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PropertyModalComponent} from "./property-modal/property-modal.component";

@Component({
  selector: 'up-properties',
  templateUrl: './properties.component.html',
  styles: []
})
export class PropertiesComponent implements OnInit {

    properties: Property[];
    selectedproperties: Property[];
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
        private activatedRoute: ActivatedRoute,
        private modalService: NgbModal,
        public activeModal: NgbActiveModal
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

    showActions(event) {
        var contains = event.path[1].classList.contains("show");

        if (contains) {
            event.path[1].classList.remove("show");
        } else {
            event.path[1].classList.add("show");
        }
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.properties = data;
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
        this.router.navigate(['/dashboard/properties'], {queryParams:
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

    selectedProperty(event) {
        console.log(event)
    }

    openDeleteModal(property: Property) {
        const modalRef = this.modalService.open(PropertyModalComponent);
        modalRef.componentInstance.name = property.title;
        modalRef.result.then((result) => {
            if (result == 'delete') {
                this.propertyService.delete(property.reference).subscribe((response) => {
                    this.eventManager.broadcast({
                        name: 'propertyListModification',
                        content: 'Deleted an property'
                    });
                }, error => {
                    this.onError(error);
                });
            }
        }, reason => {
            console.log(reason);
        })
    }

}
