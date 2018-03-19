import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OfficeComponent } from './office.component';
import { OfficeDetailComponent } from './office-detail.component';
import { OfficePopupComponent } from './office-dialog.component';
import { OfficeDeletePopupComponent } from './office-delete-dialog.component';

export const officeRoute: Routes = [
    {
        path: 'office',
        component: OfficeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.office.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'office/:id',
        component: OfficeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.office.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const officePopupRoute: Routes = [
    {
        path: 'office-new',
        component: OfficePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.office.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'office/:id/edit',
        component: OfficePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.office.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'office/:id/delete',
        component: OfficeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.office.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
