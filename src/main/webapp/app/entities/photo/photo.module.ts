import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    PhotoService,
    PhotoPopupService,
    PhotoComponent,
    PhotoDetailComponent,
    PhotoDialogComponent,
    PhotoPopupComponent,
    PhotoDeletePopupComponent,
    PhotoDeleteDialogComponent,
    photoRoute,
    photoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...photoRoute,
    ...photoPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PhotoComponent,
        PhotoDetailComponent,
        PhotoDialogComponent,
        PhotoDeleteDialogComponent,
        PhotoPopupComponent,
        PhotoDeletePopupComponent,
    ],
    entryComponents: [
        PhotoComponent,
        PhotoDialogComponent,
        PhotoPopupComponent,
        PhotoDeleteDialogComponent,
        PhotoDeletePopupComponent,
    ],
    providers: [
        PhotoService,
        PhotoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplacePhotoModule {}
