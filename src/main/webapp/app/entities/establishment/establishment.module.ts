import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    EstablishmentService,
    EstablishmentPopupService,
    EstablishmentComponent,
    EstablishmentDetailComponent,
    EstablishmentDialogComponent,
    EstablishmentPopupComponent,
    EstablishmentDeletePopupComponent,
    EstablishmentDeleteDialogComponent,
    establishmentRoute,
    establishmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...establishmentRoute,
    ...establishmentPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EstablishmentComponent,
        EstablishmentDetailComponent,
        EstablishmentDialogComponent,
        EstablishmentDeleteDialogComponent,
        EstablishmentPopupComponent,
        EstablishmentDeletePopupComponent,
    ],
    entryComponents: [
        EstablishmentComponent,
        EstablishmentDialogComponent,
        EstablishmentPopupComponent,
        EstablishmentDeleteDialogComponent,
        EstablishmentDeletePopupComponent,
    ],
    providers: [
        EstablishmentService,
        EstablishmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceEstablishmentModule {}
