import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {PropertiesNewComponent} from "./properties/properties-new/properties-new.component";
import {PropertiesComponent} from "./properties/properties.component";
import {UserRouteAccessService} from "../../shared";
import {Injectable} from "@angular/core";
import {JhiPaginationUtil} from "ng-jhipster";

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

export const dashboardRoute: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
        data: {
            pageTitle: 'dashboard.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/properties',
        component: PropertiesComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'uplaceApp.property.home.title'
        },
        resolve: {
            'pagingParams': PropertyResolvePagingParams
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/properties/new',
        component: PropertiesNewComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Dashboard - New property'
        },
        canActivate: [UserRouteAccessService]
    }
];
