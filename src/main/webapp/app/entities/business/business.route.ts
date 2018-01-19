import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BusinessComponent } from './business.component';
import { BusinessDetailComponent } from './business-detail.component';
import { BusinessPopupComponent } from './business-dialog.component';
import { BusinessDeletePopupComponent } from './business-delete-dialog.component';

export const businessRoute: Routes = [
    {
        path: 'business',
        component: BusinessComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.business.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'business/:id',
        component: BusinessDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.business.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const businessPopupRoute: Routes = [
    {
        path: 'business-new',
        component: BusinessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.business.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'business/:id/edit',
        component: BusinessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.business.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'business/:id/delete',
        component: BusinessDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.business.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
