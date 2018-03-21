import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RealEstateComponent } from './real-estate.component';
import { RealEstateDetailComponent } from './real-estate-detail.component';
import { RealEstatePopupComponent } from './real-estate-dialog.component';
import { RealEstateDeletePopupComponent } from './real-estate-delete-dialog.component';

export const realEstateRoute: Routes = [
    {
        path: 'real-estate',
        component: RealEstateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.realEstate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'real-estate/:id',
        component: RealEstateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.realEstate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const realEstatePopupRoute: Routes = [
    {
        path: 'real-estate-new',
        component: RealEstatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.realEstate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'real-estate/:id/edit',
        component: RealEstatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.realEstate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'real-estate/:id/delete',
        component: RealEstateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.realEstate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
