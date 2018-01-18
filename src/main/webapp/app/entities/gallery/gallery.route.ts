import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GalleryComponent } from './gallery.component';
import { GalleryDetailComponent } from './gallery-detail.component';
import { GalleryPopupComponent } from './gallery-dialog.component';
import { GalleryDeletePopupComponent } from './gallery-delete-dialog.component';

export const galleryRoute: Routes = [
    {
        path: 'gallery',
        component: GalleryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.gallery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gallery/:id',
        component: GalleryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.gallery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const galleryPopupRoute: Routes = [
    {
        path: 'gallery-new',
        component: GalleryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.gallery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gallery/:id/edit',
        component: GalleryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.gallery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gallery/:id/delete',
        component: GalleryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.gallery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
