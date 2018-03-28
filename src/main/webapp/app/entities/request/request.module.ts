import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    RequestService,
    RequestPopupService,
    RequestComponent,
    RequestDetailComponent,
    RequestDialogComponent,
    RequestPopupComponent,
    RequestDeletePopupComponent,
    RequestDeleteDialogComponent,
    requestRoute,
    requestPopupRoute,
    RequestResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...requestRoute,
    ...requestPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RequestComponent,
        RequestDetailComponent,
        RequestDialogComponent,
        RequestDeleteDialogComponent,
        RequestPopupComponent,
        RequestDeletePopupComponent,
    ],
    entryComponents: [
        RequestComponent,
        RequestDialogComponent,
        RequestPopupComponent,
        RequestDeleteDialogComponent,
        RequestDeletePopupComponent,
    ],
    providers: [
        RequestService,
        RequestPopupService,
        RequestResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceRequestModule {}
