import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    IndustrialPlantService,
    IndustrialPlantPopupService,
    IndustrialPlantComponent,
    IndustrialPlantDetailComponent,
    IndustrialPlantDialogComponent,
    IndustrialPlantPopupComponent,
    IndustrialPlantDeletePopupComponent,
    IndustrialPlantDeleteDialogComponent,
    industrialPlantRoute,
    industrialPlantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...industrialPlantRoute,
    ...industrialPlantPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustrialPlantComponent,
        IndustrialPlantDetailComponent,
        IndustrialPlantDialogComponent,
        IndustrialPlantDeleteDialogComponent,
        IndustrialPlantPopupComponent,
        IndustrialPlantDeletePopupComponent,
    ],
    entryComponents: [
        IndustrialPlantComponent,
        IndustrialPlantDialogComponent,
        IndustrialPlantPopupComponent,
        IndustrialPlantDeleteDialogComponent,
        IndustrialPlantDeletePopupComponent,
    ],
    providers: [
        IndustrialPlantService,
        IndustrialPlantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceIndustrialPlantModule {}
