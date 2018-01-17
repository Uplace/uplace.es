import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TerrainComponent } from './terrain.component';
import { TerrainDetailComponent } from './terrain-detail.component';
import { TerrainPopupComponent } from './terrain-dialog.component';
import { TerrainDeletePopupComponent } from './terrain-delete-dialog.component';

export const terrainRoute: Routes = [
    {
        path: 'terrain',
        component: TerrainComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.terrain.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'terrain/:id',
        component: TerrainDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.terrain.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const terrainPopupRoute: Routes = [
    {
        path: 'terrain-new',
        component: TerrainPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.terrain.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'terrain/:id/edit',
        component: TerrainPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.terrain.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'terrain/:id/delete',
        component: TerrainDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.terrain.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
