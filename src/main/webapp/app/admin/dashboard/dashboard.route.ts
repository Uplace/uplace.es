import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {PropertiesNewComponent} from "./properties/properties-new/properties-new.component";
import {PropertiesComponent} from "./properties/properties.component";
import {UserRouteAccessService} from "../../shared";
import {Injectable} from "@angular/core";
import {JhiPaginationUtil} from "ng-jhipster";
import {CompanyComponent} from "./company/company.component";
import {RealEstateComponent} from "./real-estate/real-estate.component";
import {NotificationComponent} from "./notification/notification.component";
import {RequestComponent} from "./request/request.component";

@Injectable()
export class ResolvePagingParams implements Resolve<any> {

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
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.property.home.title'
        },
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/properties/new',
        component: PropertiesNewComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dashboard - New property'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/properties/:reference',
        component: PropertiesNewComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dashboard - Edit property'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/company',
        component: CompanyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/real-estates',
        component: RealEstateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.realEstate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/notifications',
        component: NotificationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.realEstate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard/requests',
        component: RequestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'uplaceApp.request.home.title'
        },
        resolve: {
            'pagingParams': ResolvePagingParams
        },
        canActivate: [UserRouteAccessService]
    }
];
