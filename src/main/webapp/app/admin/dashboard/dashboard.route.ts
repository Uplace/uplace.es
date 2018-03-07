import {Routes} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {PropertiesNewComponent} from "./properties/properties-new/properties-new.component";
import {PropertiesComponent} from "./properties/properties.component";
import {UserRouteAccessService} from "../../shared";

export const dashboardRoute: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
        data: {
            pageTitle: 'dashboard.title'
        }
    },
    {
        path: 'dashboard/properties',
        component: PropertiesComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'uplaceApp.property.home.title'
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
