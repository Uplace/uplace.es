import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PropertyComponent } from './property.component';
import { PropertyDetailComponent } from './property-detail.component';
import { PropertyPopupComponent } from './property-dialog.component';
import { PropertyDeletePopupComponent } from './property-delete-dialog.component';

export const propertyRoute: Routes = [
    {
        path: 'properties',
        component: PropertyComponent,
        data: {
            authorities: [],
            pageTitle: 'uplaceApp.property.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'property/:reference',
        component: PropertyDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'uplaceApp.property.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const propertyPopupRoute: Routes = [
    {
        path: 'property-new',
        component: PropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.property.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'property/:id/edit',
        component: PropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.property.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'property/:id/delete',
        component: PropertyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.property.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
