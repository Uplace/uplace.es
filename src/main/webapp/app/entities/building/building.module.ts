import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    BuildingService,
    BuildingPopupService,
    BuildingComponent,
    BuildingDetailComponent,
    BuildingDialogComponent,
    BuildingPopupComponent,
    BuildingDeletePopupComponent,
    BuildingDeleteDialogComponent,
    buildingRoute,
    buildingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...buildingRoute,
    ...buildingPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BuildingComponent,
        BuildingDetailComponent,
        BuildingDialogComponent,
        BuildingDeleteDialogComponent,
        BuildingPopupComponent,
        BuildingDeletePopupComponent,
    ],
    entryComponents: [
        BuildingComponent,
        BuildingDialogComponent,
        BuildingPopupComponent,
        BuildingDeleteDialogComponent,
        BuildingDeletePopupComponent,
    ],
    providers: [
        BuildingService,
        BuildingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceBuildingModule {}
