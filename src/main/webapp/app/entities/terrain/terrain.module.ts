import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UplaceSharedModule } from '../../shared';
import {
    TerrainService,
    TerrainPopupService,
    TerrainComponent,
    TerrainDetailComponent,
    TerrainDialogComponent,
    TerrainPopupComponent,
    TerrainDeletePopupComponent,
    TerrainDeleteDialogComponent,
    terrainRoute,
    terrainPopupRoute,
} from './';

const ENTITY_STATES = [
    ...terrainRoute,
    ...terrainPopupRoute,
];

@NgModule({
    imports: [
        UplaceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TerrainComponent,
        TerrainDetailComponent,
        TerrainDialogComponent,
        TerrainDeleteDialogComponent,
        TerrainPopupComponent,
        TerrainDeletePopupComponent,
    ],
    entryComponents: [
        TerrainComponent,
        TerrainDialogComponent,
        TerrainPopupComponent,
        TerrainDeleteDialogComponent,
        TerrainDeletePopupComponent,
    ],
    providers: [
        TerrainService,
        TerrainPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UplaceTerrainModule {}
