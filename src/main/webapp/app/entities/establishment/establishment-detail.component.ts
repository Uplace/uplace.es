import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Establishment } from './establishment.model';
import { EstablishmentService } from './establishment.service';

@Component({
    selector: 'up-establishment-detail',
    templateUrl: './establishment-detail.component.html'
})
export class EstablishmentDetailComponent implements OnInit, OnDestroy {

    establishment: Establishment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private establishmentService: EstablishmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEstablishments();
    }

    load(id) {
        this.establishmentService.find(id).subscribe((establishment) => {
            this.establishment = establishment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEstablishments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'establishmentListModification',
            (response) => this.load(this.establishment.id)
        );
    }
}
