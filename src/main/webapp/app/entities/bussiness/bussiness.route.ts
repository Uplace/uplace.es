import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BussinessComponent } from './bussiness.component';
import { BussinessDetailComponent } from './bussiness-detail.component';
import { BussinessPopupComponent } from './bussiness-dialog.component';
import { BussinessDeletePopupComponent } from './bussiness-delete-dialog.component';

export const bussinessRoute: Routes = [
    {
        path: 'bussiness',
        component: BussinessComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.bussiness.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bussiness/:id',
        component: BussinessDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.bussiness.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bussinessPopupRoute: Routes = [
    {
        path: 'bussiness-new',
        component: BussinessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.bussiness.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bussiness/:id/edit',
        component: BussinessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.bussiness.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bussiness/:id/delete',
        component: BussinessDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.bussiness.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
