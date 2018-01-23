import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { NotificationComponent } from './notification.component';
import { NotificationDetailComponent } from './notification-detail.component';
import { NotificationPopupComponent } from './notification-dialog.component';
import { NotificationDeletePopupComponent } from './notification-delete-dialog.component';

@Injectable()
export class NotificationResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const notificationRoute: Routes = [
    {
        path: 'notification',
        component: NotificationComponent,
        resolve: {
            'pagingParams': NotificationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'notification/:id',
        component: NotificationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notificationPopupRoute: Routes = [
    {
        path: 'notification-new',
        component: NotificationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification/:id/edit',
        component: NotificationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification/:id/delete',
        component: NotificationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.notification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
