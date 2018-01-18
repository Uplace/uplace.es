import { Route } from '@angular/router';

import { UpHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
    path: 'up-health',
    component: UpHealthCheckComponent,
    data: {
        pageTitle: 'health.title'
    }
};
