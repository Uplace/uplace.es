import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Office } from './office.model';
import { OfficeService } from './office.service';

@Component({
    selector: 'up-office-detail',
    templateUrl: './office-detail.component.html'
})
export class OfficeDetailComponent implements OnInit, OnDestroy {

    office: Office;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private officeService: OfficeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOffices();
    }

    load(id) {
        this.officeService.find(id)
            .subscribe((officeResponse: HttpResponse<Office>) => {
                this.office = officeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOffices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'officeListModification',
            (response) => this.load(this.office.id)
        );
    }
}
