import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    RealEstateService,
    RealEstatePopupService,
    RealEstateComponent,
    RealEstateDetailComponent,
    RealEstateDialogComponent,
    RealEstatePopupComponent,
    RealEstateDeletePopupComponent,
    RealEstateDeleteDialogComponent,
    realEstateRoute,
    realEstatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...realEstateRoute,
    ...realEstatePopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RealEstateComponent,
        RealEstateDetailComponent,
        RealEstateDialogComponent,
        RealEstateDeleteDialogComponent,
        RealEstatePopupComponent,
        RealEstateDeletePopupComponent,
    ],
    entryComponents: [
        RealEstateComponent,
        RealEstateDialogComponent,
        RealEstatePopupComponent,
        RealEstateDeleteDialogComponent,
        RealEstateDeletePopupComponent,
    ],
    providers: [
        RealEstateService,
        RealEstatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceRealEstateModule {}
