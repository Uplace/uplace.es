import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { HotelComponent } from './hotel.component';
import { HotelDetailComponent } from './hotel-detail.component';
import { HotelPopupComponent } from './hotel-dialog.component';
import { HotelDeletePopupComponent } from './hotel-delete-dialog.component';

export const hotelRoute: Routes = [
    {
        path: 'hotel',
        component: HotelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.hotel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hotel/:id',
        component: HotelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.hotel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hotelPopupRoute: Routes = [
    {
        path: 'hotel-new',
        component: HotelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.hotel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hotel/:id/edit',
        component: HotelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.hotel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hotel/:id/delete',
        component: HotelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.hotel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
