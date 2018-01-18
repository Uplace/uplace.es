import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Gallery } from './gallery.model';
import { GalleryPopupService } from './gallery-popup.service';
import { GalleryService } from './gallery.service';

@Component({
    selector: 'up-gallery-delete-dialog',
    templateUrl: './gallery-delete-dialog.component.html'
})
export class GalleryDeleteDialogComponent {

    gallery: Gallery;

    constructor(
        private galleryService: GalleryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.galleryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'galleryListModification',
                content: 'Deleted an gallery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-gallery-delete-popup',
    template: ''
})
export class GalleryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private galleryPopupService: GalleryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.galleryPopupService
                .open(GalleryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
