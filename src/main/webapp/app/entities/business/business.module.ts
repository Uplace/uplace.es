import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    BusinessService,
    BusinessPopupService,
    BusinessComponent,
    BusinessDetailComponent,
    BusinessDialogComponent,
    BusinessPopupComponent,
    BusinessDeletePopupComponent,
    BusinessDeleteDialogComponent,
    businessRoute,
    businessPopupRoute,
} from './';

const ENTITY_STATES = [
    ...businessRoute,
    ...businessPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BusinessComponent,
        BusinessDetailComponent,
        BusinessDialogComponent,
        BusinessDeleteDialogComponent,
        BusinessPopupComponent,
        BusinessDeletePopupComponent,
    ],
    entryComponents: [
        BusinessComponent,
        BusinessDialogComponent,
        BusinessPopupComponent,
        BusinessDeleteDialogComponent,
        BusinessDeletePopupComponent,
    ],
    providers: [
        BusinessService,
        BusinessPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceBusinessModule {}
