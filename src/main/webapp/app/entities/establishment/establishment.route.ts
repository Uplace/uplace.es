import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EstablishmentComponent } from './establishment.component';
import { EstablishmentDetailComponent } from './establishment-detail.component';
import { EstablishmentPopupComponent } from './establishment-dialog.component';
import { EstablishmentDeletePopupComponent } from './establishment-delete-dialog.component';

export const establishmentRoute: Routes = [
    {
        path: 'establishment',
        component: EstablishmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.establishment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'establishment/:id',
        component: EstablishmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.establishment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const establishmentPopupRoute: Routes = [
    {
        path: 'establishment-new',
        component: EstablishmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.establishment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'establishment/:id/edit',
        component: EstablishmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.establishment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'establishment/:id/delete',
        component: EstablishmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.establishment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
