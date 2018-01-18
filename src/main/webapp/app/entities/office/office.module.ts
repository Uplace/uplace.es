import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    OfficeService,
    OfficePopupService,
    OfficeComponent,
    OfficeDetailComponent,
    OfficeDialogComponent,
    OfficePopupComponent,
    OfficeDeletePopupComponent,
    OfficeDeleteDialogComponent,
    officeRoute,
    officePopupRoute,
} from './';

const ENTITY_STATES = [
    ...officeRoute,
    ...officePopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OfficeComponent,
        OfficeDetailComponent,
        OfficeDialogComponent,
        OfficeDeleteDialogComponent,
        OfficePopupComponent,
        OfficeDeletePopupComponent,
    ],
    entryComponents: [
        OfficeComponent,
        OfficeDialogComponent,
        OfficePopupComponent,
        OfficeDeleteDialogComponent,
        OfficeDeletePopupComponent,
    ],
    providers: [
        OfficeService,
        OfficePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceOfficeModule {}
