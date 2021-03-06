import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Photo } from './photo.model';
import { PhotoPopupService } from './photo-popup.service';
import { PhotoService } from './photo.service';

@Component({
    selector: 'up-photo-delete-dialog',
    templateUrl: './photo-delete-dialog.component.html'
})
export class PhotoDeleteDialogComponent {

    photo: Photo;

    constructor(
        private photoService: PhotoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.photoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'photoListModification',
                content: 'Deleted an photo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'up-photo-delete-popup',
    template: ''
})
export class PhotoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private photoPopupService: PhotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.photoPopupService
                .open(PhotoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
