import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ApartmentComponent } from './apartment.component';
import { ApartmentDetailComponent } from './apartment-detail.component';
import { ApartmentPopupComponent } from './apartment-dialog.component';
import { ApartmentDeletePopupComponent } from './apartment-delete-dialog.component';

export const apartmentRoute: Routes = [
    {
        path: 'apartment',
        component: ApartmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'apartment/:id',
        component: ApartmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apartmentPopupRoute: Routes = [
    {
        path: 'apartment-new',
        component: ApartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'apartment/:id/edit',
        component: ApartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'apartment/:id/delete',
        component: ApartmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
