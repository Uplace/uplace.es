import {Routes} from "@angular/router";
import {UserRouteAccessService} from "../../../shared";
import {PropertiesComponent} from "./properties.component";
import {PropertiesNewComponent} from "./properties-new/properties-new.component";

export const dashboardPropertyRoute: Routes = [
    {
        path: 'properties',
        component: PropertiesComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'uplaceApp.property.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'properties/new',
        component: PropertiesNewComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Dashboard - New property'
        },
        canActivate: [UserRouteAccessService]
    }
];
