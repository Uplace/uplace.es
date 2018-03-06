import {Route} from "@angular/router";
import {AdminSidebarComponent} from "./admin-sidebar.component";

export const adminSidebarRoute: Route = {
    path: '',
    component: AdminSidebarComponent,
    outlet: 'admin-sidebar'
};
