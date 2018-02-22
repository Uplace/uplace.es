import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    ApartmentService,
    ApartmentPopupService,
    ApartmentComponent,
    ApartmentDetailComponent,
    ApartmentDialogComponent,
    ApartmentPopupComponent,
    ApartmentDeletePopupComponent,
    ApartmentDeleteDialogComponent,
    apartmentRoute,
    apartmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...apartmentRoute,
    ...apartmentPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ApartmentComponent,
        ApartmentDetailComponent,
        ApartmentDialogComponent,
        ApartmentDeleteDialogComponent,
        ApartmentPopupComponent,
        ApartmentDeletePopupComponent,
    ],
    entryComponents: [
        ApartmentComponent,
        ApartmentDialogComponent,
        ApartmentPopupComponent,
        ApartmentDeleteDialogComponent,
        ApartmentDeletePopupComponent,
    ],
    providers: [
        ApartmentService,
        ApartmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceApartmentModule {}
