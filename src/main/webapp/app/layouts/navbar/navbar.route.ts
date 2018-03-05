import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';
import {AdminNavbarComponent} from '../admin-navbar/admin-navbar.component';

export const navbarRoute: Route = {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar'
};

export const dashboardRoute: Route = {
    path: '',
    component: AdminNavbarComponent,
    outlet: 'navbar'
};
