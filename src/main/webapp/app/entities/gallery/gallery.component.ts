import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Gallery } from './gallery.model';
import { GalleryService } from './gallery.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-gallery',
    templateUrl: './gallery.component.html'
})
export class GalleryComponent implements OnInit, OnDestroy {
galleries: Gallery[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private galleryService: GalleryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.galleryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.galleries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGalleries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Gallery) {
        return item.id;
    }
    registerChangeInGalleries() {
        this.eventSubscriber = this.eventManager.subscribe('galleryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
