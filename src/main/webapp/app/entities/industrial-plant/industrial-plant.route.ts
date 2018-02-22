import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { IndustrialPlantComponent } from './industrial-plant.component';
import { IndustrialPlantDetailComponent } from './industrial-plant-detail.component';
import { IndustrialPlantPopupComponent } from './industrial-plant-dialog.component';
import { IndustrialPlantDeletePopupComponent } from './industrial-plant-delete-dialog.component';

export const industrialPlantRoute: Routes = [
    {
        path: 'industrial-plant',
        component: IndustrialPlantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.industrialPlant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industrial-plant/:id',
        component: IndustrialPlantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.industrialPlant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industrialPlantPopupRoute: Routes = [
    {
        path: 'industrial-plant-new',
        component: IndustrialPlantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.industrialPlant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industrial-plant/:id/edit',
        component: IndustrialPlantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.industrialPlant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industrial-plant/:id/delete',
        component: IndustrialPlantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.industrialPlant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
