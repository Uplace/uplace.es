import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bussiness } from './bussiness.model';
import { BussinessService } from './bussiness.service';

@Component({
    selector: 'up-bussiness-detail',
    templateUrl: './bussiness-detail.component.html'
})
export class BussinessDetailComponent implements OnInit, OnDestroy {

    bussiness: Bussiness;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bussinessService: BussinessService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBussinesses();
    }

    load(id) {
        this.bussinessService.find(id).subscribe((bussiness) => {
            this.bussiness = bussiness;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBussinesses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bussinessListModification',
            (response) => this.load(this.bussiness.id)
        );
    }
}
