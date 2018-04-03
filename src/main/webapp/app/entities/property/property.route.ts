import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PropertyComponent } from './property.component';
import { PropertyDetailComponent } from './property-detail.component';
import { PropertyPopupComponent } from './property-dialog.component';
import { PropertyDeletePopupComponent } from './property-delete-dialog.component';
import {JhiPaginationUtil} from "ng-jhipster";
import {Injectable} from "@angular/core";

@Injectable()
export class PropertyResolvePagingParams implements Resolve<any> {

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

export const propertyRoute: Routes = [
    {
        path: 'properties',
        component: PropertyComponent,
        resolve: {
            'pagingParams': PropertyResolvePagingParams
        },
        data: {
            authorities: [],
            pageTitle: 'uplaceApp.property.home.title'
        }
    }, {
        path: 'property/:reference',
        component: PropertyDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'uplaceApp.property.home.title'
        }
    }
];

/*export const propertyPopupRoute: Routes = [
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
];*/
