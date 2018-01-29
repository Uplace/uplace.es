import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IndustrialPlant } from './industrial-plant.model';
import { IndustrialPlantService } from './industrial-plant.service';

@Component({
    selector: 'up-industrial-plant-detail',
    templateUrl: './industrial-plant-detail.component.html'
})
export class IndustrialPlantDetailComponent implements OnInit, OnDestroy {

    industrialPlant: IndustrialPlant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private industrialPlantService: IndustrialPlantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustrialPlants();
    }

    load(id) {
        this.industrialPlantService.find(id).subscribe((industrialPlant) => {
            this.industrialPlant = industrialPlant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIndustrialPlants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industrialPlantListModification',
            (response) => this.load(this.industrialPlant.id)
        );
    }
}
