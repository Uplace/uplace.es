import {Route} from "@angular/router";
import {AdminNavbarComponent} from "./admin-navbar.component";

export const adminNavbarRoute: Route = {
    path: '',
    component: AdminNavbarComponent,
    outlet: 'admin-navbar'
};
