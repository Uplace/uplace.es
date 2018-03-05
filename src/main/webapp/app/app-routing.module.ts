import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from './app.constants';
import {adminNavbarRoute} from "./layouts/admin-navbar/admin-navbar.route";

const LAYOUT_ROUTES = [
    navbarRoute,
    ...errorRoute
];

const DASHBOARD_ROUTES = [
    adminNavbarRoute,
    ...errorRoute
];


// CHANGE TO DASHBOARD_ROUTES TO LOAD NEW LAYOUT

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true , enableTracing: DEBUG_INFO_ENABLED })
    ],
    exports: [
        RouterModule
    ]
})
export class UplaceAppRoutingModule {}
