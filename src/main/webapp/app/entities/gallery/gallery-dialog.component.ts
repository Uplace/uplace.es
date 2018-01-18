import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Gallery } from './gallery.model';
import { GalleryPopupService } from './gallery-popup.service';
import { GalleryService } from './gallery.service';
import { Property, PropertyService } from '../property';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-gallery-dialog',
    templateUrl: './gallery-dialog.component.html'
})
export class GalleryDialogComponent implements OnInit {

    gallery: Gallery;
    isSaving: boolean;

    properties: Property[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private galleryService: GalleryService,
        private propertyService: PropertyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.propertyService.query()
            .subscribe((res: ResponseWrapper) => { this.properties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.gallery.id !== undefined) {
            this.subscribeToSaveResponse(
                this.galleryService.update(this.gallery));
        } else {
            this.subscribeToSaveResponse(
                this.galleryService.create(this.gallery));
        }
    }

    private subscribeToSaveResponse(result: Observable<Gallery>) {
        result.subscribe((res: Gallery) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Gallery) {
        this.eventManager.broadcast({ name: 'galleryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPropertyById(index: number, item: Property) {
        return item.id;
    }
}

@Component({
    selector: 'up-gallery-popup',
    template: ''
})
export class GalleryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private galleryPopupService: GalleryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.galleryPopupService
                    .open(GalleryDialogComponent as Component, params['id']);
            } else {
                this.galleryPopupService
                    .open(GalleryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
