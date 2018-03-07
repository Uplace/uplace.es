import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {errorRoute, navbarRoute, UpMainComponent} from './layouts';
import { DEBUG_INFO_ENABLED } from './app.constants';
import {adminNavbarRoute} from "./layouts/admin-navbar/admin-navbar.route";
import {UpAdminMainComponent} from "./layouts/admin-main/admin-main.component";
import {HOME_ROUTE} from "./home";
import {UplaceAdminModule} from "./admin/admin.module";
import {propertyPopupRoute, propertyRoute} from "./entities/property";
import {auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, metricsRoute, userMgmtRoute} from "./admin";
import {UserRouteAccessService} from "./shared";
import {accountState} from "./account";
import {adminSidebarRoute} from "./layouts/admin-sidebar/admin-sidebar.route";
import {dashboardRoute} from "./admin/dashboard/dashboard.route";

const LAYOUT_ROUTES = [
    navbarRoute,
    ...errorRoute
];

const DASHBOARD_ROUTES = [
    adminNavbarRoute,
    ...errorRoute
];

const appRoutes: Routes = [

    //Site routes goes here
    {
        path: '',
        component: UpMainComponent,
        children: [
            HOME_ROUTE,
            navbarRoute,
            ...propertyRoute,
            ...propertyPopupRoute,
            ...errorRoute
        ],
    },

    // App routes goes here here
    {
        path: '',
        component: UpAdminMainComponent,
        data: {
            authorities: ['ROLE_ADMIN']
        },
        canActivate: [UserRouteAccessService],
        children: [
            ...accountState,
            adminSidebarRoute,
            adminNavbarRoute,
            auditsRoute,
            configurationRoute,
            docsRoute,
            healthRoute,
            logsRoute,
            ...userMgmtRoute,
            metricsRoute,
            ...dashboardRoute,
            ...errorRoute
        ]
    },
];

@NgModule({
    imports: [
        RouterModule.forRoot(appRoutes, { useHash: true , enableTracing: DEBUG_INFO_ENABLED })
    ],
    exports: [
        RouterModule
    ]
})
export class UplaceAppRoutingModule {}
