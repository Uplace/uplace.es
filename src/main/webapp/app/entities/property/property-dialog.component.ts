import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Property } from './property.model';
import { PropertyPopupService } from './property-popup.service';
import { PropertyService } from './property.service';
import { Gallery, GalleryService } from '../gallery';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'up-property-dialog',
    templateUrl: './property-dialog.component.html'
})
export class PropertyDialogComponent implements OnInit {

    property: Property;
    isSaving: boolean;

    galleries: Gallery[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private propertyService: PropertyService,
        private galleryService: GalleryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.galleryService
            .query({filter: 'property(title)-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.property.galleryId) {
                    this.galleries = res.json;
                } else {
                    this.galleryService
                        .find(this.property.galleryId)
                        .subscribe((subRes: Gallery) => {
                            this.galleries = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.property.id !== undefined) {
            this.subscribeToSaveResponse(
                this.propertyService.update(this.property));
        } else {
            this.subscribeToSaveResponse(
                this.propertyService.create(this.property));
        }
    }

    private subscribeToSaveResponse(result: Observable<Property>) {
        result.subscribe((res: Property) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Property) {
        this.eventManager.broadcast({ name: 'propertyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGalleryById(index: number, item: Gallery) {
        return item.id;
    }
}

@Component({
    selector: 'up-property-popup',
    template: ''
})
export class PropertyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private propertyPopupService: PropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.propertyPopupService
                    .open(PropertyDialogComponent as Component, params['id']);
            } else {
                this.propertyPopupService
                    .open(PropertyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
