import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    GalleryService,
    GalleryPopupService,
    GalleryComponent,
    GalleryDetailComponent,
    GalleryDialogComponent,
    GalleryPopupComponent,
    GalleryDeletePopupComponent,
    GalleryDeleteDialogComponent,
    galleryRoute,
    galleryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...galleryRoute,
    ...galleryPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GalleryComponent,
        GalleryDetailComponent,
        GalleryDialogComponent,
        GalleryDeleteDialogComponent,
        GalleryPopupComponent,
        GalleryDeletePopupComponent,
    ],
    entryComponents: [
        GalleryComponent,
        GalleryDialogComponent,
        GalleryPopupComponent,
        GalleryDeleteDialogComponent,
        GalleryDeletePopupComponent,
    ],
    providers: [
        GalleryService,
        GalleryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceGalleryModule {}
