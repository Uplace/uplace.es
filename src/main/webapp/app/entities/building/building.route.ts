import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BuildingComponent } from './building.component';
import { BuildingDetailComponent } from './building-detail.component';
import { BuildingPopupComponent } from './building-dialog.component';
import { BuildingDeletePopupComponent } from './building-delete-dialog.component';

export const buildingRoute: Routes = [
    {
        path: 'building',
        component: BuildingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.building.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'building/:id',
        component: BuildingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.building.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const buildingPopupRoute: Routes = [
    {
        path: 'building-new',
        component: BuildingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.building.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building/:id/edit',
        component: BuildingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.building.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building/:id/delete',
        component: BuildingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.building.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
