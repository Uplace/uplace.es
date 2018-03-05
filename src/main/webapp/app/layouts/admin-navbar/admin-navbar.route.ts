import { Route } from '@angular/router';

import {AdminNavbarComponent} from './admin-navbar.component';

export const navbarRoute: Route = {
    path: '',
    component: AdminNavbarComponent,
    outlet: 'navbar'
};
