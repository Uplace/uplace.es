import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    ParkingService,
    ParkingPopupService,
    ParkingComponent,
    ParkingDetailComponent,
    ParkingDialogComponent,
    ParkingPopupComponent,
    ParkingDeletePopupComponent,
    ParkingDeleteDialogComponent,
    parkingRoute,
    parkingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...parkingRoute,
    ...parkingPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ParkingComponent,
        ParkingDetailComponent,
        ParkingDialogComponent,
        ParkingDeleteDialogComponent,
        ParkingPopupComponent,
        ParkingDeletePopupComponent,
    ],
    entryComponents: [
        ParkingComponent,
        ParkingDialogComponent,
        ParkingPopupComponent,
        ParkingDeleteDialogComponent,
        ParkingDeletePopupComponent,
    ],
    providers: [
        ParkingService,
        ParkingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceParkingModule {}
