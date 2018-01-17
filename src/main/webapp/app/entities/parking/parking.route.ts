import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ParkingComponent } from './parking.component';
import { ParkingDetailComponent } from './parking-detail.component';
import { ParkingPopupComponent } from './parking-dialog.component';
import { ParkingDeletePopupComponent } from './parking-delete-dialog.component';

export const parkingRoute: Routes = [
    {
        path: 'parking',
        component: ParkingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.parking.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parking/:id',
        component: ParkingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.parking.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parkingPopupRoute: Routes = [
    {
        path: 'parking-new',
        component: ParkingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.parking.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parking/:id/edit',
        component: ParkingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.parking.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parking/:id/delete',
        component: ParkingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.parking.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
