import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    BussinessService,
    BussinessPopupService,
    BussinessComponent,
    BussinessDetailComponent,
    BussinessDialogComponent,
    BussinessPopupComponent,
    BussinessDeletePopupComponent,
    BussinessDeleteDialogComponent,
    bussinessRoute,
    bussinessPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bussinessRoute,
    ...bussinessPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BussinessComponent,
        BussinessDetailComponent,
        BussinessDialogComponent,
        BussinessDeleteDialogComponent,
        BussinessPopupComponent,
        BussinessDeletePopupComponent,
    ],
    entryComponents: [
        BussinessComponent,
        BussinessDialogComponent,
        BussinessPopupComponent,
        BussinessDeleteDialogComponent,
        BussinessDeletePopupComponent,
    ],
    providers: [
        BussinessService,
        BussinessPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceBussinessModule {}
